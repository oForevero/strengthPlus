package top.mccat.utils;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @ClassName: MenuUtils
 * @Description: 用于发送菜单信息
 * @Author: Raven
 * @Date: 2022/1/6
 * @Version: 1.0
 */
public class MenuUtils {

    /**
     * 发送指令 menu
     * @param player player.
     */
    public static void menuInfo(Player player){
        sendMsg(player,"&4&l===---------&6&l[strengthPlus]&4&l----------===");
        sendMsg(player,"&c/sp 或 qh 打开此帮助菜单");
        sendMsg(player,"&a/sp 或 qh normal 进行一次强化");
        sendMsg(player,"&b/sp 或 qh safe 保护强化 (强化失败不降级)");
        sendMsg(player,"&b/sp 或 qh success 必定成功强化 ");
        if(player.hasPermission("strengthPlus.admin")){
            sendMsg(player,"&4&l===-------&6&l[strengthPlusAdmin]&4&l--------===");
            sendMsg(player,"&c/sp &b admin &c管理员强化，&a直接满级");
            sendMsg(player,"&c/sp &b reload &c管理员专用，&a重载配置");
            sendMsg(player,"&c/sp &b normalStone &c管理员&a权限可用，给与&b普通强化石");
            sendMsg(player,"&c/sp &b safeStone &c管理员&a权限可用，给与&c保护强化石");
            sendMsg(player,"&c/sp &b successStone &c管理员&a权限可用，给与&e必定成功强化石");
        }
        sendMsg(player,"&4&l===&6&l--------------------------------&4&l===");
    }

    /**
     * 作者信息menu
     * @param plugin javaPlugin.
     */
    public static void authorMenu(JavaPlugin plugin){
        plugin.getServer().getConsoleSender().sendMessage("§4§l===-------§6§l[StrengthPlus]§4§l--------===");
        plugin.getServer().getConsoleSender().sendMessage("§b      制作者： Raven       ");
        plugin.getServer().getConsoleSender().sendMessage("§b      QQ ： 740585947     ");
        plugin.getServer().getConsoleSender().sendMessage("§b如有bug可以加我反馈也可以在bbs论坛下留言，蟹蟹！");
        plugin.getServer().getConsoleSender().sendMessage("§4§l===-------§6§l[StrengthPlus]§4§l--------===");
    }

    public static void sendMsg(Player player, String str){
        player.sendMessage(ColorUtils.getColorStr(str));
    }
}
