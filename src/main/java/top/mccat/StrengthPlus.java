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
 * @Description: TODO
 * @Author: Raven
 * @Date: 2022/1/6
 * @Version: 1.0
 */
public class StrengthPlus extends JavaPlugin {
    private static final String DEFAULT_COMMAND = "sp";
    private ConsoleCommandSender sender;
    private ConfigFactory factory;
    private CommandHandler handler;

    @Override
    public void onLoad() {
        this.saveDefaultConfig();
        sender = getServer().getConsoleSender();
        MenuUtils.authorMenu(this);
    }

    @Override
    public void onEnable() {
        factory = new ConfigFactory(this);
        handler = new CommandHandler(this,factory);
        Objects.requireNonNull(Bukkit.getPluginCommand(DEFAULT_COMMAND)).setExecutor(handler);
        handler.setFactory(factory);
        Objects.requireNonNull(Bukkit.getPluginCommand(DEFAULT_COMMAND)).setTabCompleter(this);
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        consoleMsg("&c&l正在重新读取&a[&bconfig.yml&a]&c&l文件...");
        factory.initFile();
        factory.initExtra();
        handler.reloadHandlerMethod(factory.getStrengthExtra());
    }

    /**
     * 子命令联想
     */
    private final String[] subCommands = {"normal", "safe", "success", "admin", "reload", "normalstone", "safestone", "successstone"};
    @Override
    public @Nullable
    List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length > 1) {
            return new ArrayList<>();
        }
        if (args.length == 0) {
            return Arrays.asList(subCommands);
        }
        return Arrays.stream(subCommands).filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
    }

    public void consoleMsg(String msg){
        sender.sendMessage(ColorUtils.getColorStr("&a[strengthPlus] "+msg));
    }
}
