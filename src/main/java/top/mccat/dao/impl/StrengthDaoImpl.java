package top.mccat.dao.impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.mccat.dao.StrengthDao;
import top.mccat.domain.StrengthItemStack;
import top.mccat.domain.StrengthStone;
import top.mccat.service.impl.StrengthServiceImpl;
import top.mccat.utils.MenuUtils;
import top.mccat.utils.PlayerMsgUtils;

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
    private List<StrengthStone> strengthStones;
    public StrengthDaoImpl(){
        adminStrengthLore.add(StrengthServiceImpl.ADMIN_PREFIX);
        adminStrengthLore.add("&c----------");
        adminStrengthLore.add("§c✡✡✡✡✡✡✡✡✡✡");
    }

    @Override
    public ItemStack normalStrength(boolean isSuccess, StrengthItemStack strengthItemStack) {
        ItemStack stack = strengthItemStack.getItemStack();
        int stackLevel = levelHandler(strengthItemStack.getAuthor(),isSuccess, strengthItemStack.getStrengthLevel());
        ItemMeta meta = setNormalLore(strengthItemStack, stackLevel);
        stack.setItemMeta(meta);
        return stack;
    }

    @Override
    public ItemStack safeStrength(boolean isSuccess, StrengthItemStack strengthItemStack) {
        return null;
    }

    @Override
    public ItemStack successStrength(StrengthItemStack strengthItemStack) {
        return null;
    }

    @Override
    public ItemStack adminStrength(StrengthItemStack strengthItemStack) {
        return null;
    }

    @Override
    public ItemStack giveNormalStone(int count) {
        return getStoneStack(0,count);
    }

    @Override
    public ItemStack giveSafeStone(int count) {
        return getStoneStack(1,count);
    }

    @Override
    public ItemStack giveSuccessStone(int count) {
        return getStoneStack(2,count);
    }

    @Override
    public void infoMenu(Player player) {
        MenuUtils.menuInfo(player);
    }

    /**
     * 通过对应列表下表和物品数量进行配置
     * @param index stones列表下标
     * @param count 给与物品堆数量
     * @return 物品堆对象
     */
    private ItemStack getStoneStack(int index, int count){
        StrengthStone stone = strengthStones.get(index);
        Material type = Material.valueOf(stone.getMaterial());
        ItemStack stack = new ItemStack(type);
        ItemMeta itemMeta = stack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setLore(stone.getLore());
        itemMeta.setDisplayName(stone.getStoneName());
        stack.setItemMeta(itemMeta);
        stack.setAmount(count);
        return stack;
    }

    /**
     * 处理level，这里默认将超过10的等级再最开始强化的时候就进行处理，这里不做判断。
     * @param player 玩家对象
     * @param success 是否成功
     * @param level 当前物品等级
     * @return extra 进行强化后的物品等级,最小等级必须为0
     */
    private int levelHandler(Player player,boolean success, int level){
        int extra = 0;
        if (success) {
            extra = level + 1;
            PlayerMsgUtils.sendMsg(player,"&6恭喜你，强化成功！ &b当前武器等级为 [&a"+(level+1)+"&b]");
        } else {
            extra = level - 1;
            PlayerMsgUtils.sendMsg(player,"&c很遗憾，你的强化失败了！");
        }
        return Math.max(extra, 0);
    }

    /**
     * 设置普通lore
     * @param strengthItemStack StrengthItemStack 对象
     * @param level 等级
     * @return itemMeta
     */
    private ItemMeta setNormalLore(StrengthItemStack strengthItemStack,int level){
        ItemMeta itemMeta = strengthItemStack.getItemStack().getItemMeta();
        List<String> itemMetaLore = itemMeta.getLore();
        if(itemMetaLore==null){
            itemMetaLore = new ArrayList<>();
        }else {
            //由于默认不存在为-1
            int strengthIndex;
            if((strengthIndex = strengthItemStack.getStrengthLoreIndex())!=-1){
                itemMetaLore.set(strengthIndex,StrengthItemStack.STRENGTH_PREFIX);
                itemMetaLore.set(strengthIndex+1,StrengthItemStack.SPLIT_LINE);
                itemMetaLore.set(strengthIndex+2,getLevelStr(level));
                itemMeta.setLore(itemMetaLore);
                return itemMeta;
            }
        }
        setLoreList(itemMetaLore, level);
        itemMeta.setLore(itemMetaLore);
        return itemMeta;
    }

    /**
     * 配置 lore 列表
     * @param itemMetaLore lore列表
     * @param level 等级
     */
    private void setLoreList(List<String> itemMetaLore, int level){
        itemMetaLore.add(StrengthItemStack.STRENGTH_PREFIX);
        itemMetaLore.add(StrengthItemStack.SPLIT_LINE);
        itemMetaLore.add(getLevelStr(level));
    }

    /**
     * 获取等级字符
     * @param level 等级
     * @return 等级str
     */
    private String getLevelStr(int level){
        StringBuilder builder = new StringBuilder();
        if (level < 6){
            builder.append("§a");
        }else if (level < 9){
            builder.append("§b");
        }else if(level == 9){
            builder.append("§c");
        }else {
            builder.append("§e");
        }
        for(int i = 0; i < level; i++){
            builder.append("✡");
        }
        return builder.toString();
    }

    public void setStrengthStones(List<StrengthStone> strengthStones) {
        this.strengthStones = strengthStones;
    }
}
