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
import top.mccat.utils.DebugMsgUtils;
import top.mccat.utils.PlayerMsgUtils;

import java.util.Arrays;
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
    public static final String STRENGTH_PREFIX = "§b[强化等级]:§6§l";
    public static final String ADMIN_PREFIX = "§b[§c§l管理员§b强化等级]:§6§l";
    private final Random random = new Random();

    public StrengthServiceImpl(){}

    public StrengthServiceImpl(StrengthExtra strengthExtra){
        this.strengthExtra = strengthExtra;
        dao.setStrengthStones(strengthExtra.getStrengthStones());
    }

    @Override
    public ItemStack strengthItem(Player p,boolean isSafe, boolean isSuccess, boolean isAdmin) {
        ItemStack mainHandStack = null;
        if(canBeStrength(mainHandStack = p.getInventory().getItemInMainHand())){
            int level;
            if((level = getStackLevel(mainHandStack))>9){
                PlayerMsgUtils.sendMsg(p,"&c&l你的武器已强化到最高等级！无法再进行武器的强化！");
            }
            boolean strengthStatue = strengthResult(level);
            if(costStone(p,isSafe,isSuccess)) {
                DebugMsgUtils.sendDebugMsg(p,"haven been costStone...");
                if (isSafe) {
                    mainHandStack = dao.safeStrength(strengthStatue, level, mainHandStack);
                } else if (isSuccess) {
                    mainHandStack = dao.successStrength(level, mainHandStack);
                } else if (isAdmin) {
                    mainHandStack = dao.adminStrength(mainHandStack);
                } else {
                    mainHandStack = dao.normalStrength(strengthStatue, level, mainHandStack);
                    DebugMsgUtils.sendDebugMsg(p,"normal strength...");
                }
                return mainHandStack;
            }else{
                PlayerMsgUtils.sendMsg(p,"&c&l请确定自己有足够的强化石进行此强化！");
            }
        }else {
            PlayerMsgUtils.sendMsg(p,"&c&l请确定自己主手物品是否可强化或为空气！");
        }
        return null;
    }

    @Override
    public ItemStack giveStrengthStone(Player player, int amount, boolean isSafe, boolean isSuccess) {
        ItemStack strengthStoneStack = null;
        if(isSafe){
            strengthStoneStack = dao.giveSafeStone(amount);
        }else if (isSuccess){
            strengthStoneStack = dao.giveSuccessStone(amount);
        }else {
            strengthStoneStack = dao.giveNormalStone(amount);
        }
        return strengthStoneStack;
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
            for(String material : strengthExtra.getStrengthItemExtra().getMaterials()){
                if (material.equals(type.toString())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取物品等级
     * @param stack itemStack对象
     * @return 等级数值 为 0 代表无强化等级，>0则代表有强化等级）
     */
    private int getStackLevel(ItemStack stack){
        ItemMeta itemMeta = stack.getItemMeta();
        if (itemMeta != null) {
            List<String> lore = itemMeta.getLore();
            if (lore!=null){
                for(String str : lore){
                    if (str.contains(STRENGTH_PREFIX)){
                        //由于有§c的前置所以要-2
                        return str.split(STRENGTH_PREFIX)[1].length()-2;
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
        ItemStack[] extraContents = inventory.getContents();
        StrengthStone stone;
        List<StrengthStone> strengthStones = strengthExtra.getStrengthStones();
        if(isSafe){
            //对应保护石的列表地址
            stone = strengthStones.get(1);
        }else if(isSuccess){
            //对应成功石的列表地址
            stone = strengthStones.get(2);
        }else {
            //对应普通石的列表地址
            stone = strengthStones.get(0);
        }
        String stoneMaterial = stone.getMaterial();
        for(ItemStack stack : extraContents){
            if(stack!=null){
                if(stoneMaterial.equals(stack.getType().toString())){
                    stack.setAmount(stack.getAmount()-1);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 重载service参数
     * @param strengthExtra strengthExtra 对象
     */
    public void reloadServiceConfig(StrengthExtra strengthExtra){
        dao.setStrengthStones(strengthExtra.getStrengthStones());
    }

    public void setStrengthExtra(StrengthExtra strengthExtra) {
        this.strengthExtra = strengthExtra;
    }
}
