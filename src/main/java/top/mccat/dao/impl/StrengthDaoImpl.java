package top.mccat.dao.impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.mccat.dao.StrengthDao;
import top.mccat.service.impl.StrengthServiceImpl;
import top.mccat.utils.ColorUtils;
import top.mccat.utils.MenuUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ClassName: StrengthDaoImpl
 * @Description: dao层只实现基础方法，进行基础的强化工作，传入成功则传出强化物品或失败物品
 * @Author: Raven
 * @Date: 2022/1/6
 * @Version: 1.0
 */
public class StrengthDaoImpl implements StrengthDao{
    private List<String> adminStrengthLore = new ArrayList<>();
    public StrengthDaoImpl(){
        adminStrengthLore.add(StrengthServiceImpl.ADMIN_PREFIX);
        adminStrengthLore.add("&c----------");
        adminStrengthLore.add("§c✡✡✡✡✡✡✡✡✡✡");
    }

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

    /**
     * 设置普通lore
     * @param itemMeta itemMeta 对象
     * @param level 等级
     */
    private void setNormalLore(ItemMeta itemMeta,int level){
        List<String> itemMetaLore = itemMeta.getLore();
        if(itemMetaLore==null){
            itemMetaLore = new ArrayList<>();
        }
        setLoreList(itemMetaLore,StrengthServiceImpl.STRENGTH_PREFIX,level);
    }

    /**
     * 配置 lore 列表
     * @param itemMetaLore lore列表
     * @param prefix 前缀
     * @param level 等级
     */
    private void setLoreList(List<String> itemMetaLore, String prefix,int level){
        itemMetaLore.add(prefix);
        itemMetaLore.add("&b----------");
        itemMetaLore.add(getLevelStr(level));
    }

    /**
     * 获取等级字符
     * @param level 等级
     * @return 等级str
     */
    private String getLevelStr(int level){
        StringBuilder builder = new StringBuilder("§b");
        for(int i = 0; i < level; i++){
            builder.append("✡");
        }
        return builder.toString();
    }
}
