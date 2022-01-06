package top.mccat.handler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.mccat.StrengthPlus;
import top.mccat.utils.ConfigFactory;

/**
 * @ClassName: CommandHandler
 * @Description: TODO
 * @Author: Raven
 * @Date: 2022/1/6
 * @Version: 1.0
 */
public class CommandHandler implements CommandExecutor {
    private StrengthPlus plugin;
    private ConfigFactory factory;
    public CommandHandler(){}

    public CommandHandler(StrengthPlus plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
        }
        return false;
    }

    public void setPlugin(StrengthPlus plugin) {
        this.plugin = plugin;
    }

    public void setFactory(ConfigFactory factory) {
        this.factory = factory;
    }

    public ConfigFactory getFactory() {
        return factory;
    }
}
