package top.mccat.domain;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @ClassName: StrengthItemStack
 * @Description: TODO
 * @Author: Raven
 * @Date: 2022/1/9
 * @Version: 1.0
 */
public class StrengthItemStack {
    private ItemStack itemStack;
    /**
     * 默认下标为-1，代表不存在该lore
     */
    private int strengthLoreIndex = -1;
    private int strengthLevel = 0;
    private Player author;
    public static final String STRENGTH_PREFIX = "§b[强化等级]:§6§l";
    public static final String SPLIT_LINE = "§c--------------";

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public int getStrengthLoreIndex() {
        return strengthLoreIndex;
    }

    public void setStrengthLoreIndex(int strengthLoreIndex) {
        this.strengthLoreIndex = strengthLoreIndex;
    }

    public int getStrengthLevel() {
        return strengthLevel;
    }

    public void setStrengthLevel(int strengthLevel) {
        this.strengthLevel = strengthLevel;
    }

    public Player getAuthor() {
        return author;
    }

    public void setAuthor(Player author) {
        this.author = author;
    }
}
