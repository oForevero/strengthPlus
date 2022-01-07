package top.mccat.domain;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: StrengthItem
 * @Description: 强化石参数
 * @Author: Raven
 * @Date: 2022/1/6
 * @Version: 1.0
 */
public class StrengthStone {
    private String stoneName;
    /**
     * 列表下标0对应普通强化石，1对应保护强化石头，2对应必定成功强化石
     */
    private List<String> lore;
    private boolean isSafe;
    private boolean isLuck;
    private boolean isSuccess;
    private boolean isAdmin;
    private String material;
    public StrengthStone() {}

    public String getStoneName() {
        return stoneName;
    }

    public void setStoneName(String stoneName) {
        this.stoneName = stoneName;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public boolean isSafe() {
        return isSafe;
    }

    public void setSafe(boolean safe) {
        isSafe = safe;
    }

    public boolean isLuck() {
        return isLuck;
    }

    public void setLuck(boolean luck) {
        isLuck = luck;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    @Override
    public String toString() {
        return "StrengthStone{" +
                "stoneName='" + stoneName + '\'' +
                ", lore=" + lore +
                ", isSafe=" + isSafe +
                ", isLuck=" + isLuck +
                ", isSuccess=" + isSuccess +
                ", isAdmin=" + isAdmin +
                ", material=" + material +
                '}';
    }
}
