package top.mccat.listener;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.mccat.StrengthPlus;
import top.mccat.domain.DamageExtra;
import top.mccat.domain.StrengthItemStack;
import top.mccat.utils.DebugMsgUtils;
import java.util.List;
import java.util.Objects;

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
    private Player damager;
    @EventHandler(priority = EventPriority.HIGH)
    public void modifySwordDamage(EntityDamageByEntityEvent event){
        Entity damagerEntity;
        Entity defencerEntity;
        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK){
            if((damagerEntity = event.getDamager()) instanceof Player) {
                damager = (Player) damagerEntity;
                Player defencer = null;
                ItemStack mainHandStack = damager.getInventory().getItemInMainHand();
                if((defencerEntity = event.getEntity()) instanceof Player){
                    defencer = (Player) defencerEntity;
                }
                setDamage(event,0,damager,defencer,mainHandStack);
            }
        }else if(event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE){
            if(damager != null){
                Player defencer = null;
                ItemStack mainHandStack = damager.getInventory().getItemInMainHand();
                if((defencerEntity = event.getEntity()) instanceof Player){
                    defencer = (Player) defencerEntity;
                }
                Material type = mainHandStack.getType();
                if(type.equals(Material.BOW)){
                    setDamage(event,1,damager,defencer,mainHandStack);
                }else if(type.equals(Material.CROSSBOW)){
                    setDamage(event,2,damager,defencer,mainHandStack);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void modifyBowDamage(EntityShootBowEvent event){
        Entity damagerEntity;
        if((damagerEntity = event.getEntity()) instanceof Player && getStackLevel(Objects.requireNonNull(event.getBow()))>0) {
            this.damager = (Player) damagerEntity;
        }
    }

    /**
     * 通过物品来设置伤害值
     * @param event 事件
     * @param item 如果是0，则为剑，1为弓，2为弩
     */
    private void setDamage(EntityDamageByEntityEvent event, int item, Player damager, Player defencer,ItemStack mainHandStack){
        int damageLevel;
        double damage = event.getDamage();
        double defenceDamage = 0;
        if((damageLevel = getStackLevel(mainHandStack))>0){
            switch (item){
                case 0:
                    damage += damageLevel*damageExtra.getSwordDamage();
                    break;
                case 1:
                    damage += damageLevel*damageExtra.getBowDamage();
                    break;
                case 2:
                    damage += damageLevel*damageExtra.getCrossbowDamage();
                    break;
                default:
                    break;
            }
        }
        if(defencer!=null){
            ItemStack[] armorContents = defencer.getInventory().getArmorContents();
            if(armorContents.length>0){
                int defenceLevel = 0;
                for(ItemStack stack : armorContents){
                    defenceLevel += getStackLevel(stack);
                }
                if(defenceLevel > 0){
                    defenceDamage = damageExtra.getArmorDefence()*defenceLevel;
                }
                if((damage -= defenceDamage)>0){
                    event.setDamage(damage);
                }else {
                    //防止刮痧伤害
                    event.setDamage(damageExtra.getMinDamage());
                }
            }
        }
        event.setDamage(damage);
    }

    /**
     * 获取物品等级
     * @param stack 物品堆
     * @return 等级参数
     */
    private int getStackLevel(ItemStack stack){
        ItemMeta itemMeta = stack.getItemMeta();
        if (itemMeta != null) {
            List<String> lore = itemMeta.getLore();
            if (lore!=null){
                for(int i = 0;i < lore.size(); i++){
                    String str = lore.get(i);
                    if (str.contains(StrengthItemStack.STRENGTH_PREFIX)){
                        //由于有§c的前置所以要-2
                        return lore.get(i+2).length()-2;
                    }
                }
            }
        }
        return 0;
    }

    public void setDamageExtra(DamageExtra damageExtra) {
        this.damageExtra = damageExtra;
    }

    public void setPlugin(StrengthPlus plugin) {
        this.plugin = plugin;
    }
}
