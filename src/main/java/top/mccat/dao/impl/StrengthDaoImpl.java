package top.mccat.dao.impl;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import top.mccat.dao.StrengthDao;
import top.mccat.utils.ColorUtils;
import top.mccat.utils.MenuUtils;

/**
 *
 * @ClassName: StrengthDaoImpl
 * @Description: dao层只实现基础方法，进行基础的强化工作，传入成功则传出强化物品或失败物品
 * @Author: Raven
 * @Date: 2022/1/6
 * @Version: 1.0
 */
public class StrengthDaoImpl implements StrengthDao{
    @Override
    public ItemStack normalStrength(boolean isSuccess, int level) {
        return null;
    }

    @Override
    public ItemStack safeStrength(boolean isSuccess, int level) {
        return null;
    }

    @Override
    public ItemStack successStrength(int level) {
        return null;
    }

    @Override
    public ItemStack adminStrength() {
        return null;
    }

    @Override
    public ItemStack giveNormalStone() {
        return null;
    }

    @Override
    public ItemStack giveSafeStone() {
        return null;
    }

    @Override
    public ItemStack giveSuccessStone() {
        return null;
    }

    @Override
    public void infoMenu(Player player) {
        MenuUtils.menuInfo(player);
    }
}
