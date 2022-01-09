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

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName: ConfigFactory
 * @Description: 配置文件工厂，用于生成config.yml和 加载配置文件
 * @Author: Raven
 * @Date: 2022/1/6
 * @Version: 1.0
 */
public class ConfigFactory {
    private StrengthPlus plugin;
    private StrengthExtra strengthExtra;
    private final FileConfiguration configuration = new YamlConfiguration();

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
            configuration.load(new File(plugin.getDataFolder(),"config.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            plugin.consoleMsg("&c&l配置文件不存在，正在生成配置文件....");
            plugin.saveDefaultConfig();
            initFile();
        }
        plugin.consoleMsg("&c&l本地配置文件初始化成功，正在读取配置文件...");
    }

    public void initExtra(){
        initItemExtra();
        initStrengthStone();
        initDamageExtra();
        plugin.consoleMsg("&6&l配置文件读取成功！");
    }

    /**
     * 初始化强化物品和物品强化几率
     */
    private void initItemExtra(){
        StrengthItemExtra extra = new StrengthItemExtra();
        List<String> materials = configuration.getStringList("itemName");
        extra.setMaterials(materials);
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
            stone.setStoneName(ColorUtils.getColorStr(Objects.requireNonNull(section).getString("name")));
            stone.setMaterial(section.getString("item"));
            List<String> lore = section.getStringList("lore");
            stone.setLore(getParseLore(lore));
            stone.setSafe(section.getBoolean("safe", false));
            stone.setSuccess(section.getBoolean("success", false));
            strengthStones.add(stone);
        }
        //注入对象
        strengthExtra.setStrengthStones(strengthStones);
    }

    private List<String> getParseLore(List<String> lore){
        List<String> parseLore = new ArrayList<>();
        for(String str : lore){
            parseLore.add(ColorUtils.getColorStr(str));
        }
        return parseLore;
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
