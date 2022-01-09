package top.mccat.dao;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import top.mccat.domain.StrengthItemStack;

/**
 * @ClassName: strengthDao
 * @Description: dao层只实现基础方法，进行基础的强化工作，传入成功则传出强化物品，失败则根据装备等级而定
 * @Author: Raven
 * @Date: 2022/1/6
 * @Version: 1.0
 */
public interface StrengthDao {
    /**
     * 普通强化
     * @param isSuccess 是否成功
     * @param strengthItemStack 玩家强化物品对象
     * @return 强化物品
     */
    ItemStack normalStrength(boolean isSuccess, StrengthItemStack strengthItemStack);

    /**
     * 安全强化
     * @param isSuccess 是否成功
     * @param strengthItemStack 玩家强化物品对象
     * @return 强化物品
     */
    ItemStack safeStrength(boolean isSuccess, StrengthItemStack strengthItemStack);

    /**
     * 必定成功强化
     * @param strengthItemStack 玩家强化物品对象
     * @return 强化物品
     */
    ItemStack successStrength(StrengthItemStack strengthItemStack);

    /**
     * 直接满级强化
     * @param strengthItemStack 玩家强化物品对象
     * @return 强化物品
     */
    ItemStack adminStrength(StrengthItemStack strengthItemStack);

    /**
     * 获取普通强化石
     * @param count 数量
     * @return 强化石对象
     */
    ItemStack giveNormalStone(int count);

    /**
     * 获取保护强化石
     * @param count 数量
     * @return 保护强化石对象
     */
    ItemStack giveSafeStone(int count);

    /**
     * 获取必定成功强化石
     * @param count 数量
     * @return 必定成功强化石对象
     */
    ItemStack giveSuccessStone(int count);

    /**
     * 用于给玩家展示菜单面板的方法
     * @param player player.
     */
    void infoMenu(Player player);
}
