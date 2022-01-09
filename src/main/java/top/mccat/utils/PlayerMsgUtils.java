package top.mccat.utils;

import org.bukkit.entity.Player;

/**
 * @ClassName: PlayerMsgUtils
 * @Description: 玩家信息类，所有的玩家信息都使用了该 util
 * @Author: Raven
 * @Date: 2022/1/8
 * @Version: 1.0
 */
public class PlayerMsgUtils {
    public static void sendMsg(Player player, String msg){
        player.sendMessage(ColorUtils.getColorStr("&a[strengthPlus] "+msg));
    }
}
