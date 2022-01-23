package top.mccat;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import top.mccat.handler.CommandHandler;
import top.mccat.listener.OnDamageListener;
import top.mccat.service.impl.StrengthServiceImpl;
import top.mccat.utils.ColorUtils;
import top.mccat.utils.ConfigFactory;
import top.mccat.utils.MenuUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName: StrengthPlus
 * @Description: 主启动类，负责加载各个模块
 * @Author: Raven
 * @Date: 2022/1/6
 * @Version: 1.0
 */
public class StrengthPlus extends JavaPlugin {
    private static final String DEFAULT_COMMAND = "sp";
    private OnDamageListener damageListener;
    private ConsoleCommandSender sender;
    private ConfigFactory factory;
    private CommandHandler handler;

    @Override
    public void onLoad() {
        this.saveDefaultConfig();
        sender = getServer().getConsoleSender();
    }

    @Override
    public void onEnable() {
        MenuUtils.authorMenu(this);
        factory = new ConfigFactory(this);
        //初始化并绑定handler
        handler = new CommandHandler(this,factory);
        Objects.requireNonNull(Bukkit.getPluginCommand(DEFAULT_COMMAND)).setExecutor(handler);
        handler.setFactory(factory);
        //设置tab联想
        Objects.requireNonNull(Bukkit.getPluginCommand(DEFAULT_COMMAND)).setTabCompleter(this);
        //初始化并绑定监听器
        damageListener = new OnDamageListener();
        damageListener.setPlugin(this);
        damageListener.setDamageExtra(factory.getStrengthExtra().getDamageExtra());
        getServer().getPluginManager().registerEvents(damageListener,this);
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        consoleMsg("&c&l正在重新读取&a[&bconfig.yml&a]&c&l文件...");
        factory.initFile();
        factory.initExtra();
        handler.reloadHandlerMethod(factory.getStrengthExtra());
        damageListener.setDamageExtra(factory.getStrengthExtra().getDamageExtra());
    }

    /**
     * 子命令联想
     */
    private final String[] subUserCommands = {"normal", "safe", "success"};
    private final String[] subCommands = {"normal", "safe", "success", "admin", "reload", "normalstone", "safestone", "successstone"};
    @Override
    public @Nullable
    List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length > 1) {
            return new ArrayList<>();
        }
        if(sender.hasPermission(CommandHandler.ADMIN_PERMISSION)){
            if (args.length == 0) {
                return Arrays.asList(subCommands);
            }
            return Arrays.stream(subCommands).filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
        }
        if (args.length == 0) {
            return Arrays.asList(subUserCommands);
        }
        return Arrays.stream(subUserCommands).filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
    }

    /**
     * 发送控制台信息
     * @param msg 消息字符串
     */
    public void consoleMsg(String msg){
        sender.sendMessage(ColorUtils.getColorStr("&a[strengthPlus] "+msg));
    }

    /**
     * 进行强化广播消息
     * @param msg 消息字符串
     */
    public void strengthBroadcastMsg(String msg){
        this.getServer().broadcastMessage(ColorUtils.getColorStr("&b[&e&l强化公告&b] "+msg));
    }

}
