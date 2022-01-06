package top.mccat;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import top.mccat.handler.CommandHandler;
import top.mccat.utils.ColorUtils;
import top.mccat.utils.ConfigFactory;

import java.util.Objects;

/**
 * @ClassName: StrengthPlus
 * @Description: TODO
 * @Author: Raven
 * @Date: 2022/1/6
 * @Version: 1.0
 */
public class StrengthPlus extends JavaPlugin {
    private ConsoleCommandSender sender;
    private ConfigFactory factory;
    private CommandHandler handler;
    @Override
    public void onLoad() {
        factory = new ConfigFactory(this);
    }

    @Override
    public void onEnable() {
        Objects.requireNonNull(Bukkit.getPluginCommand("sp")).setExecutor(handler = new CommandHandler(this));
        handler.setFactory(factory);
    }

    @Override
    public void onDisable() {

    }

    public void consoleMsg(String msg){
        sender.sendMessage(ColorUtils.getColorStr(msg));
    }
}
