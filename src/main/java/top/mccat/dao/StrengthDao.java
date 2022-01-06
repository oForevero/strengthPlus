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
    ItemStack normalStrength(boolean isSuccess);

    ItemStack safeStrength(boolean isSuccess);

    ItemStack successStrength();

    ItemStack adminStrength();

    ItemStack giveNormalStone();

    ItemStack giveSafeStone();

    ItemStack giveSuccessStone();

    /**
     * 用于给玩家展示菜单面板的方法
     * @param player player.
     */
    void infoMenu(Player player);
}
