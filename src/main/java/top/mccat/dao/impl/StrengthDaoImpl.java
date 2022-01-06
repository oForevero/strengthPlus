package top.mccat.dao.impl;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import top.mccat.dao.StrengthDao;
import top.mccat.utils.ColorUtils;

/**
 *
 * @ClassName: StrengthDaoImpl
 * @Description: dao层只实现基础方法，进行基础的强化工作，传入成功则传出强化物品，失败则根据装备等级而定
 * @Author: Raven
 * @Date: 2022/1/6
 * @Version: 1.0
 */
public class StrengthDaoImpl implements StrengthDao{
    @Override
    public ItemStack normalStrength(boolean isSuccess) {
        return null;
    }

    @Override
    public ItemStack safeStrength(boolean isSuccess) {
        return null;
    }

    @Override
    public ItemStack successStrength() {
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

    }


}
