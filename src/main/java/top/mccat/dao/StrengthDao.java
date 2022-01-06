package top.mccat.dao;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
     * @param level 当前等级
     * @return 强化物品
     */
    ItemStack normalStrength(boolean isSuccess,int level);

    /**
     * 安全强化
     * @param isSuccess 是否成功
     * @param level 当前等级
     * @return 强化物品
     */
    ItemStack safeStrength(boolean isSuccess, int level);

    /**
     * 必定成功强化
     * @param level 当前等级
     * @return 强化物品
     */
    ItemStack successStrength(int level);

    /**
     * 直接满级强化
     * @return 强化物品
     */
    ItemStack adminStrength();

    /**
     * 获取普通强化石
     * @return 获取强化石
     */
    ItemStack giveNormalStone();

    ItemStack giveSafeStone();

    ItemStack giveSuccessStone();

    /**
     * 用于给玩家展示菜单面板的方法
     * @param player player.
     */
    void infoMenu(Player player);
}
