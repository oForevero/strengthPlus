package top.mccat.domain;

/**
 * @ClassName: DamageExtra
 * @Description: TODO
 * @Author: Raven
 * @Date: 2022/1/6
 * @Version: 1.0
 */
public class DamageExtra {
    private double swordDamage;
    private double crossbowDamage;
    private double bowDamage;
    private double minDamage;
    private double armorDefence;
    public DamageExtra(){}

    public double getSwordDamage() {
        return swordDamage;
    }

    public void setSwordDamage(double swordDamage) {
        this.swordDamage = swordDamage;
    }

    public double getCrossbowDamage() {
        return crossbowDamage;
    }

    public void setCrossbowDamage(double crossbowDamage) {
        this.crossbowDamage = crossbowDamage;
    }

    public double getBowDamage() {
        return bowDamage;
    }

    public void setBowDamage(double bowDamage) {
        this.bowDamage = bowDamage;
    }

    public double getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(double minDamage) {
        this.minDamage = minDamage;
    }

    public double getArmorDefence() {
        return armorDefence;
    }

    public void setArmorDefence(double armorDefence) {
        this.armorDefence = armorDefence;
    }

    @Override
    public String toString() {
        return "DamageExtra{" +
                "swordDamage=" + swordDamage +
                ", crossbowDamage=" + crossbowDamage +
                ", bowDamage=" + bowDamage +
                ", minDamage=" + minDamage +
                ", armorDefence=" + armorDefence +
                '}';
    }
}
