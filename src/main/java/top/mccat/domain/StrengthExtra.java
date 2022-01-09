package top.mccat.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: StrengthExtra
 * @Description: 总强化bean类
 * @Author: Raven
 * @Date: 2022/1/6
 * @Version: 1.0
 */
public class StrengthExtra implements Serializable {
    private DamageExtra damageExtra;
    private List<StrengthStone> strengthStones;
    private StrengthItemExtra strengthItemExtra;

    public StrengthExtra() {
    }

    public DamageExtra getDamageExtra() {
        return damageExtra;
    }

    public void setDamageExtra(DamageExtra damageExtra) {
        this.damageExtra = damageExtra;
    }

    public List<StrengthStone> getStrengthStones() {
        return strengthStones;
    }

    public void setStrengthStones(List<StrengthStone> strengthStones) {
        this.strengthStones = strengthStones;
    }

    public StrengthItemExtra getStrengthItemExtra() {
        return strengthItemExtra;
    }

    public void setStrengthItemExtra(StrengthItemExtra strengthItemExtra) {
        this.strengthItemExtra = strengthItemExtra;
    }

    @Override
    public String toString() {
        return "StrengthExtra{" +
                "damageExtra=" + damageExtra +
                ", strengthStones=" + strengthStones +
                ", strengthItemExtra=" + strengthItemExtra +
                '}';
    }
}
