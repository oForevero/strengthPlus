package top.mccat.service.impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import top.mccat.StrengthPlus;
import top.mccat.dao.impl.StrengthDaoImpl;
import top.mccat.domain.StrengthExtra;
import top.mccat.domain.StrengthItemStack;
import top.mccat.domain.StrengthStone;
import top.mccat.service.StrengthService;
import top.mccat.utils.PlayerMsgUtils;
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
    private StrengthPlus plugin;
    private final StrengthDaoImpl dao = new StrengthDaoImpl();
    private StrengthExtra strengthExtra;
    private final Random random = new Random();

    public StrengthServiceImpl(StrengthExtra strengthExtra){
        this.strengthExtra = strengthExtra;
        dao.setStrengthStones(strengthExtra.getStrengthStones());
    }

    @Override
    public ItemStack strengthItem(Player p,boolean isSafe, boolean isSuccess, boolean isAdmin) {
        ItemStack mainHandStack;
        if(canBeStrength(mainHandStack = p.getInventory().getItemInMainHand())){
            StrengthItemStack strengthItemStack = getStrengthItem(mainHandStack);
            strengthItemStack.setAuthor(p);
            int level;
            if((level = strengthItemStack.getStrengthLevel())>9){
                PlayerMsgUtils.sendMsg(p,"&c&l你的武器已强化到最高等级！无法再进行武器的强化！");
                return null;
            }
            boolean strengthStatue = strengthResult(level);
            if(costStone(p,isSafe,isSuccess)) {
                if (isSafe) {
                    mainHandStack = dao.safeStrength(strengthStatue, strengthItemStack);
                } else if (isSuccess) {
                    mainHandStack = dao.successStrength(strengthItemStack);
                }else {
                    mainHandStack = dao.normalStrength(strengthStatue, strengthItemStack);
                }
                return mainHandStack;
            }else if(isAdmin){
                mainHandStack = dao.adminStrength(strengthItemStack);
                plugin.strengthBroadcastMsg("&c&l卑鄙的管理员&a[&e&l"+p.getName()+"&a]&c&l使用管理员指令将自己的武器直接强化到了满级，真是厚颜无耻之人！");
                return mainHandStack;
            }else {
                PlayerMsgUtils.sendMsg(p,"&c&l请确定自己有足够的强化石进行此强化！");
            }
        }else {
            PlayerMsgUtils.sendMsg(p,"&c&l请确定自己主手物品是否可强化或为空气！");
        }
        return null;
    }

    @Override
    public ItemStack giveStrengthStone(Player player, int amount, boolean isSafe, boolean isSuccess) {
        ItemStack strengthStoneStack;
        if(isSafe){
            strengthStoneStack = dao.giveSafeStone(amount);
        }else if (isSuccess){
            strengthStoneStack = dao.giveSuccessStone(amount);
        }else {
            strengthStoneStack = dao.giveNormalStone(amount);
        }
        PlayerMsgUtils.sendMsg(player,"&b&l已向您发送&a[&c"+amount+"&a]&b&l个&a["+strengthStoneStack.getItemMeta().getDisplayName()+"&a]");
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
     * 获取物品堆等级
     * @param stack itemStack 对象
     * @return strengthItemStack 对象
     */
    private StrengthItemStack getStrengthItem(ItemStack stack){
        StrengthItemStack strengthItemStack = new StrengthItemStack();
        strengthItemStack.setItemStack(stack);
        ItemMeta itemMeta = stack.getItemMeta();
        if (itemMeta != null) {
            List<String> lore = itemMeta.getLore();
            if (lore!=null){
                for(int i = 0;i < lore.size(); i++){
                    String str = lore.get(i);
                    if (str.contains(StrengthItemStack.STRENGTH_PREFIX)){
                        strengthItemStack.setStrengthLoreIndex(i);
                        //由于有§c的前置所以要-2
                        strengthItemStack.setStrengthLevel(lore.get(i+2).length()-2);
                    }
                }
            }
        }
        return strengthItemStack;
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
        for(ItemStack stack : extraContents){
            if(stack!=null){
                if(stone.getMaterial().equals(stack.getType().toString())){
                    ItemStack item = new ItemStack(Material.valueOf(stone.getMaterial()));
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.setLore(stone.getLore());
                    itemMeta.setDisplayName(stone.getStoneName());
                    item.setItemMeta(itemMeta);
                    if(item.isSimilar(stack)){
                        stack.setAmount(stack.getAmount()-1);
                        return true;
                    }
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

    public void setPlugin(StrengthPlus plugin) {
        this.plugin = plugin;
        dao.setPlugin(plugin);
    }

    public void setStrengthExtra(StrengthExtra strengthExtra) {
        this.strengthExtra = strengthExtra;
    }
}
