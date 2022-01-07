package top.mccat.service.impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import top.mccat.dao.impl.StrengthDaoImpl;
import top.mccat.domain.StrengthExtra;
import top.mccat.domain.StrengthStone;
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
            int level = getStackLevel(mainHandStack);
            boolean strengthStatue = strengthResult(level);
            if(isSafe){
                dao.safeStrength(strengthStatue,level);
            }else if(isSuccess){
                dao.successStrength(level);
            }else if(isAdmin){
                dao.adminStrength();
            }else {
                dao.normalStrength(strengthStatue,level);
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

    /**
     * 扣取强化石方法.
     * @param isSafe 是否为保护强化
     * @param isSuccess 是否为必定成功强化
     * @return cost 是否支付过了
     */
    private boolean costStone(Player player,boolean isSafe, boolean isSuccess){
        PlayerInventory inventory = player.getInventory();
        ItemStack[] extraContents = inventory.getExtraContents();
        StrengthStone stone;
        if(isSafe){
            //对应保护石的列表地址
            stone = strengthExtra.getStrengthStones().get(1);
        }else if(isSuccess){
            //对应成功石的列表地址
            stone = strengthExtra.getStrengthStones().get(2);
        }else {
            //对应普通石的列表地址
            stone = strengthExtra.getStrengthStones().get(0);
        }
        boolean cost = false;
        for(ItemStack stack : extraContents){
            if(stone.getMaterial().equals(stack.getType().toString())){
                stack.setAmount(stack.getAmount()-1);
                return true;
            }
        }
        return false;
    }

    public void setStrengthExtra(StrengthExtra strengthExtra) {
        this.strengthExtra = strengthExtra;
    }
}
