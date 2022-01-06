package top.mccat.utils;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import top.mccat.StrengthPlus;
import top.mccat.domain.DamageExtra;
import top.mccat.domain.StrengthExtra;
import top.mccat.domain.StrengthItemExtra;
import top.mccat.domain.StrengthStone;

import java.io.IOException;
import java.util.*;

/**
 * @ClassName: ConfigFactory
 * @Description: TODO
 * @Author: Raven
 * @Date: 2022/1/6
 * @Version: 1.0
 */
public class ConfigFactory {
    private StrengthPlus plugin;
    private StrengthExtra strengthExtra;
    private FileConfiguration configuration = new YamlConfiguration();

    public ConfigFactory(){}

    public ConfigFactory(StrengthPlus plugin){
        this.plugin = plugin;
        strengthExtra = new StrengthExtra();
        initFile();
        initExtra();
    }

    /**
     * 初始化配置文件
     */
    public void initFile(){
        try {
            configuration.load(Objects.requireNonNull(ConfigFactory.class.getClassLoader().getResource("config.yml")).getFile());
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            plugin.consoleMsg("&a[strengthPlus]&c配置文件不存在，正在生成配置文件....");
            plugin.saveDefaultConfig();
            initFile();
        }
        plugin.consoleMsg("&a[strengthPlus]&b配置文件已加载...");
    }

    public void initExtra(){
        initItemExtra();
        initStrengthStone();
        initDamageExtra();
        plugin.consoleMsg("&a[strengthPlus]&c配置文件读取成功...");
    }

    /**
     * 初始化强化物品和物品强化几率
     */
    private void initItemExtra(){
        StrengthItemExtra extra = new StrengthItemExtra();
        List<?> materials = configuration.getList("itemName");
        extra.setMaterials((List<Material>) materials);
        extra.setStrengthChance(configuration.getIntegerList("strength_chance"));
        strengthExtra.setStrengthItemExtra(extra);
    }

    /**
     * 初始化强化石item
     */
    private void initStrengthStone(){
        List<StrengthStone> strengthStones = new ArrayList<>();
        ConfigurationSection strengthStoneConf = configuration.getConfigurationSection("strength_stone");
        List<ConfigurationSection> configurationSections = new ArrayList<>();
        //分别对应normal，safe和success 强化石的配置
        ConfigurationSection stoneNormal = Objects.requireNonNull(strengthStoneConf).getConfigurationSection("stone_normal");
        ConfigurationSection stoneSafe = Objects.requireNonNull(strengthStoneConf).getConfigurationSection("stone_safe");
        ConfigurationSection stoneSuccess = Objects.requireNonNull(strengthStoneConf).getConfigurationSection("stone_success");
        configurationSections.add(stoneNormal);
        configurationSections.add(stoneSafe);
        configurationSections.add(stoneSuccess);
        for (ConfigurationSection section : configurationSections) {
            StrengthStone stone = new StrengthStone();
            stone.setStoneName(Objects.requireNonNull(section).getString("name"));
            stone.setMaterial(section.getString("item"));
            stone.setLore(section.getStringList("lore"));
            stone.setSafe(section.getBoolean("safe", false));
            stone.setSuccess(section.getBoolean("success", false));
            strengthStones.add(stone);
        }
        //注入对象
        strengthExtra.setStrengthStones(strengthStones);
    }

    /**
     * 加载伤害模块
     */
    private void initDamageExtra(){
        DamageExtra damageExtra = new DamageExtra();
        damageExtra.setSwordDamage(configuration.getDouble("sword"));
        damageExtra.setBowDamage(configuration.getDouble("bow"));
        damageExtra.setCrossbowDamage(configuration.getDouble("crossbow"));
        damageExtra.setArmorDefence(configuration.getDouble("defence"));
        damageExtra.setMinDamage(configuration.getDouble("min_damage"));
        strengthExtra.setDamageExtra(damageExtra);
    }

    public StrengthExtra getStrengthExtra() {
        return strengthExtra;
    }

    public void setPlugin(StrengthPlus plugin) {
        this.plugin = plugin;
    }
}
