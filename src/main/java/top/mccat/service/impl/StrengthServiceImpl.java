package top.mccat.service.impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.mccat.dao.impl.StrengthDaoImpl;
import top.mccat.domain.StrengthExtra;
import top.mccat.service.StrengthService;

import java.util.List;
import java.util.Random;

/**
 * @ClassName: StrengthServiceImpl
 * @Description: service层用于逻辑思维，进行逻辑的判断
 * @Author: Raven
 * @Date: 2022/1/6
 * @Version: 1.0
 */
public class StrengthServiceImpl implements StrengthService {
    private final StrengthDaoImpl dao = new StrengthDaoImpl();
    private StrengthExtra strengthExtra;
    private static final String STRENGTH_PREFIX = "§b[强化等级]:§6§l";
    private static final String ADMIN_PREFIX = "§b[§c§l管理员§b强化等级]:§6§l";
    private Random random = new Random();

    @Override
    public ItemStack strengthItem(Player p,boolean isSafe, boolean isSuccess, boolean isAdmin) {
        ItemStack mainHandStack = null;
        if(canBeStrength(mainHandStack = p.getInventory().getItemInMainHand())){
            if(isSafe){

            }else if(isSuccess){

            }else if(isAdmin){

            }else {

            }
        }else{
            return null;
        }
        return mainHandStack;
    }

    @Override
    public void infoMenu(Player player) {
        dao.infoMenu(player);
    }

    /**
     * 判断是否能强化
     * @param stack 物品
     * @return 是否可以强化的布尔值
     */
    private boolean canBeStrength(ItemStack stack){
        if(stack!=null){
            Material type = stack.getType();
            for(Material material : strengthExtra.getStrengthItemExtra().getMaterials()){
                if (material.equals(type)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取物品等级
     * @param stack itemStack对象
     * @return 等级数值
     */
    private int getStackLevel(ItemStack stack){
        ItemMeta itemMeta = stack.getItemMeta();
        if (itemMeta != null) {
            List<String> lore = itemMeta.getLore();
            if (lore!=null){
                for(String str : lore){
                    if (str.contains(STRENGTH_PREFIX)){
                        return str.split(STRENGTH_PREFIX)[1].length();
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 返回是否强化成功
     * @param level 当前物品等级
     * @return 成功或失败
     */
    private boolean strengthResult(int level){
        int index = level;
        if(index==0){
            index = 1;
        }
        List<Integer> strengthChance = strengthExtra.getStrengthItemExtra().getStrengthChance();
        int chance = random.nextInt(101);
        return chance < strengthChance.get(index - 1);
    }

    public void setStrengthExtra(StrengthExtra strengthExtra) {
        this.strengthExtra = strengthExtra;
    }
}
