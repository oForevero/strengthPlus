package top.mccat.dao.impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.mccat.StrengthPlus;
import top.mccat.dao.StrengthDao;
import top.mccat.domain.StrengthItemStack;
import top.mccat.domain.StrengthStone;
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
    private final List<String> adminStrengthLore = new ArrayList<>();
    private StrengthPlus plugin;
    private List<StrengthStone> strengthStones;
    public StrengthDaoImpl(){
        adminStrengthLore.add(StrengthItemStack.STRENGTH_PREFIX);
        adminStrengthLore.add(StrengthItemStack.SPLIT_LINE);
        adminStrengthLore.add("§c✡✡✡✡✡✡✡✡✡✡");
    }

    @Override
    public ItemStack normalStrength(boolean isSuccess, StrengthItemStack strengthItemStack) {
        return strengthItem(strengthItemStack,isSuccess,false);
    }

    @Override
    public ItemStack safeStrength(boolean isSuccess, StrengthItemStack strengthItemStack) {
        return strengthItem(strengthItemStack,isSuccess,true);
    }

    @Override
    public ItemStack successStrength(StrengthItemStack strengthItemStack) {
        return strengthItem(strengthItemStack,true,false);
    }

    @Override
    public ItemStack adminStrength(StrengthItemStack strengthItemStack) {
        return setAdminLore(strengthItemStack.getItemStack());
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
     * 如果保护我们走正常强化流程，反之返回空气
     * @param strengthItemStack StrengthItemStack对象
     * @param isSuccess 是否成功
     * @param isSafe 是否为安全强化
     * @return 强化物品或空气
     */
    private ItemStack strengthItem(StrengthItemStack strengthItemStack,boolean isSuccess, boolean isSafe){
        ItemStack stack = strengthItemStack.getItemStack();
        int stackLevel = levelHandler(strengthItemStack, isSuccess, isSafe, strengthItemStack.getStrengthLevel());
        ItemMeta meta = setNormalLore(strengthItemStack, stackLevel);
        stack.setItemMeta(meta);
        if(!isSuccess){
            //默认大于6是由于失败会降级，所以当失败且等级为7的物品都要摧毁
            if(stackLevel>6){
                if(!isSafe){
                    return new ItemStack(Material.AIR);
                }
            }
        }
        return stack;
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
     * @param strengthItemStack StrengthItemStack对象
     * @param success 是否成功
     * @param level 当前物品等级
     * @return extra 进行强化后的物品等级,最小等级必须为0
     */
    private int levelHandler(StrengthItemStack strengthItemStack, boolean success, boolean isSafe, int level){
        Player player = strengthItemStack.getAuthor();
        ItemMeta itemMeta = strengthItemStack.getItemStack().getItemMeta();
        String displayName = "武器";
        if(itemMeta.hasDisplayName()){
            displayName = itemMeta.getDisplayName();
        }else if(itemMeta.hasLocalizedName()){
            displayName = itemMeta.getLocalizedName();
        }
        int extra = 0;
        if (success) {
            extra = level + 1;
            successStrengthMsg(player,extra,displayName);
        } else {
            extra = level - 1;
            failStrengthMsg(player,extra,isSafe,displayName);
        }
        return Math.max(extra, 0);
    }

    private void successStrengthMsg(Player player,int level,String displayName){
        PlayerMsgUtils.sendMsg(player,"&6恭喜你，强化成功！ &b当前武器等级为 [&a"+(level)+"&b]");
        if (level == 10){
            plugin.strengthBroadcastMsg("&e&l只见强化炉中一抹金光闪烁,&b&l恭喜玩家&c[&a"+player.getName()+"&c]&b&l将他的&c[&c"+displayName+"&c]&b&l强化到了 &6"+level+" &b&l级！");
        }else if (level>5){
            plugin.strengthBroadcastMsg("&b&l恭喜玩家&c[&a"+player.getName()+"&c]&b&l将他的&c[&c"+displayName+"&c]&b&l强化到了 &6"+level+" &b&l级！");
        }
    }

    /**
     * 成功时打印的消息
     * @param player 玩家对象
     * @param level 物品等级
     * @param isSafe 是否为保护强化
     * @param displayName 展示名
     */
    private void failStrengthMsg(Player player,int level, boolean isSafe,String displayName){
        if(level > 6){
            if(!isSafe){
                plugin.strengthBroadcastMsg("&c&l玩家&a[&a"+player.getName()+"&a]&c&l将他的&a[&c"+displayName+"&a]&c&l强化到 &6"+(level+1)+" &c&l级时强化炉发生了爆炸！导致武器被炸毁了！");
                PlayerMsgUtils.sendMsg(player,"&c很遗憾，你的强化失败了！当前武器已被摧毁！");
                return;
            }else {
                plugin.strengthBroadcastMsg("&c&l玩家&a[&a"+player.getName()+"&a]&c&l将他的&a[&c"+displayName+"&a]&c&l强化到 &6"+(level+1)+" &c&l级时强化炉发生了爆炸！但是由于装备保护卷的保护导致武器并没有炸毁！");
            }
        }
        PlayerMsgUtils.sendMsg(player,"&c很遗憾，你的强化失败了！ &b当前武器等级为 [&a"+(level)+"&b]");
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
     * 设置管理员lore
     * @param stack stack对象
     * @return 物品堆对象
     */
    private ItemStack setAdminLore(ItemStack stack){
        ItemMeta itemMeta = stack.getItemMeta();
        List<String> itemMetaLore = itemMeta.getLore();
        if(itemMetaLore==null){
            itemMetaLore = adminStrengthLore;
        }else {
            itemMetaLore.addAll(adminStrengthLore);
        }
        itemMeta.setLore(itemMetaLore);
        stack.setItemMeta(itemMeta);
        return stack;
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

    public void setPlugin(StrengthPlus plugin) {
        this.plugin = plugin;
    }

    public void setStrengthStones(List<StrengthStone> strengthStones) {
        this.strengthStones = strengthStones;
    }
}
