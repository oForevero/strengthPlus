package top.mccat.service;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @ClassName: StrengthService
 * @Description: service层用于逻辑思维，进行逻辑的判断
 * @Author: Raven
 * @Date: 2022/1/6
 * @Version: 1.0
 */
public interface StrengthService {

    /**
     * 强化物品方法
     * @param player player.
     * @param isSafe 是否为保护强化石
     * @param isSuccess 是否为必定成功强化石
     * @param isAdmin 是否为管理员指令强化
     * @return item 物品
     */
    ItemStack strengthItem(Player player,boolean isSafe, boolean isSuccess, boolean isAdmin);

    /**
     * 用于给玩家展示菜单面板的方法
     * @param player player.
     */
    void infoMenu(Player player);
}
