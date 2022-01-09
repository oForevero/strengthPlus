package top.mccat.utils;

import org.bukkit.ChatColor;

/**
 * @ClassName: ColorUtils
 * @Description: 转义颜色工具
 * @Author: Raven
 * @Date: 2022/1/6
 * @Version: 1.0
 */
public class ColorUtils {
    public static String getColorStr(String str){
        return ChatColor.translateAlternateColorCodes('&',str);
    }
}
