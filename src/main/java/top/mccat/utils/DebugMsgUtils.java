package top.mccat.utils;

import org.bukkit.entity.Player;
import top.mccat.handler.CommandHandler;

/**
 * @ClassName: DebugMsgUtils
 * @Description: TODO
 * @Author: Raven
 * @Date: 2022/1/8
 * @Version: 1.0
 */
public class DebugMsgUtils {
    public static void sendDebugMsg(Player player,String msg){
        if(player.isOp() || player.hasPermission(CommandHandler.ADMIN_PERMISSION)){
            player.sendMessage(ColorUtils.getColorStr("&c[strengthPlusDebugger]： &b&l"+msg));
        }
    }
}
