package top.mccat.listener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import top.mccat.StrengthPlus;
import top.mccat.domain.DamageExtra;

/**
 * @ClassName: OnDamageListener
 * @Description: 伤害监听器
 * @Author: Raven
 * @Date: 2022/1/9
 * @Version: 1.0
 */
public class OnDamageListener implements Listener {
    private DamageExtra damageExtra;
    private StrengthPlus plugin;

    public DamageExtra getDamageExtra() {
        return damageExtra;
    }

    public void setDamageExtra(DamageExtra damageExtra) {
        this.damageExtra = damageExtra;
    }

    public StrengthPlus getPlugin() {
        return plugin;
    }

    public void setPlugin(StrengthPlus plugin) {
        this.plugin = plugin;
    }
}
