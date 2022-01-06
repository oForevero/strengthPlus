package top.mccat.domain;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: StrengthItemExtra
 * @Description: TODO
 * @Author: Raven
 * @Date: 2022/1/6
 * @Version: 1.0
 */
public class StrengthItemExtra {
    private List<Integer> strengthChance;
    private List<Material> materials;
    private List<StrengthStone> strengthStones;
    public StrengthItemExtra(){}

    public List<Integer> getStrengthChance() {
        return strengthChance;
    }

    public void setStrengthChance(List<Integer> strengthChance) {
        this.strengthChance = strengthChance;
    }

    public List<StrengthStone> getStrengthStones() {
        return strengthStones;
    }

    public void setStrengthStones(List<StrengthStone> strengthStones) {
        this.strengthStones = strengthStones;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    @Override
    public String toString() {
        return "StrengthItemExtra{" +
                "strengthChance=" + strengthChance +
                ", materials=" + materials +
                ", strengthStones=" + strengthStones +
                '}';
    }
}
