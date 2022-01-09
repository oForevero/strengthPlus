package top.mccat.handler;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import top.mccat.StrengthPlus;
import top.mccat.domain.StrengthExtra;
import top.mccat.domain.StrengthStone;
import top.mccat.service.impl.StrengthServiceImpl;
import top.mccat.utils.ConfigFactory;
import top.mccat.utils.PlayerMsgUtils;

import java.util.List;

/**
 * @ClassName: CommandHandler
 * @Description: 处理命令的Handler
 * @Author: Raven
 * @Date: 2022/1/6
 * @Version: 1.0
 */
public class CommandHandler implements CommandExecutor {
    private StrengthPlus plugin;
    private ConfigFactory factory;
    private static StrengthServiceImpl strengthService;
    private static final int DEFAULT_STACK = 64;
    public static final String ADMIN_PERMISSION = "strength.admin";

    public CommandHandler(StrengthPlus plugin, ConfigFactory factory){
        this.plugin = plugin;
        this.factory = factory;
        strengthService = new StrengthServiceImpl(factory.getStrengthExtra());
        strengthService.setPlugin(plugin);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String mainCommand, String[] commandArray) {
        if(commandSender instanceof Player){
            Player player = ((Player) commandSender).getPlayer();
            assert player != null;
            if (commandArray.length<1){
                strengthService.infoMenu(player);
            }else {
                String sonCommand = commandArray[0];
                ItemStack stack = null;
                int amount = 0;
                switch (sonCommand){
                    case "normal":
                        stack = strengthService.strengthItem(player, false, false, false);
                        strengthCheck(stack,player);
                        break;
                    case "safe":
                        stack = strengthService.strengthItem(player,true,false,false);
                        strengthCheck(stack,player);
                        break;
                    case "success":
                        stack = strengthService.strengthItem(player,false,true,false);
                        strengthCheck(stack,player);
                        break;
                    case "admin":
                        if(player.hasPermission(ADMIN_PERMISSION)){
                            stack = strengthService.strengthItem(player,false,false,true);
                            strengthCheck(stack,player);
                        }
                        break;
                    case "normalstone":
                        if((amount = giveCommandCheck(commandArray,player))>0) {
                            giveStrengthStone(player,strengthService.giveStrengthStone(player, amount, false, false));
                        }
                        break;
                    case "safestone":
                        if((amount = giveCommandCheck(commandArray,player))>0) {
                            giveStrengthStone(player,strengthService.giveStrengthStone(player, amount, true, false));
                        }
                        break;
                    case "successstone":
                        if((amount = giveCommandCheck(commandArray,player))>0) {
                            giveStrengthStone(player,strengthService.giveStrengthStone(player, amount, false, true));
                        }
                        break;
                    case "reload":
                        if(player.hasPermission(ADMIN_PERMISSION)){
                            PlayerMsgUtils.sendMsg(player,"&c&l正在重新读取&a[&bconfig.yml&a]&c&l文件，请稍后...");
                            plugin.reloadConfig();
                            PlayerMsgUtils.sendMsg(player,"&6&l配置文件加载成功!");
                        }
                        break;
                    default:
                        break;
                }
            }
            return true;
        }else {
            if ("reload".equals(commandArray[0])) {
                plugin.reloadConfig();
                return true;
            }else {
                plugin.consoleMsg("控制台仅允许使用reload指令");
            }
        }
        return false;
    }

    /**
     * 重载handler本地数据
     * @param strengthExtra StrengthExtra 对象
     */
    public void reloadHandlerMethod(StrengthExtra strengthExtra){
        strengthService.setStrengthExtra(strengthExtra);
        strengthService.reloadServiceConfig(strengthExtra);
    }

    /**
     * 给与玩家强化石方法
     * @param player 玩家对象
     * @param stack stack对象
     */
    private void giveStrengthStone(Player player, ItemStack stack){
        PlayerInventory inventory = player.getInventory();
        int amount = stack.getAmount();
        int firstIndex = 0;
        int firstEmpty = 0;
        while (amount>0){
            firstIndex = getIndexItem(stack,inventory.getContents());
            firstEmpty = inventory.firstEmpty();
            if(firstIndex != -1){
                ItemStack firstStack = inventory.getItem(firstIndex);
                assert firstStack != null;
                int itemAmount = firstStack.getAmount();
                firstStack.setAmount(Math.min(amount+itemAmount,DEFAULT_STACK));
                amount -= DEFAULT_STACK - itemAmount;
                inventory.setItem(firstIndex,firstStack);
                continue;
            }
            if (firstEmpty == -1){
                PlayerMsgUtils.sendMsg(player,"&c&l背包已满，有&b[&a"+amount+"&b]&c&l物品欠发。请截图联系管理员进行物品补偿(&e&l请调整背包容量！&c&l)");
                break;
            }
            stack.setAmount(Math.min(amount, DEFAULT_STACK));
            amount -= DEFAULT_STACK;
            inventory.setItem(firstEmpty, stack);
        }
    }

    /**
     * 获取index参数
     * @param stack 强化石stack对象
     * @param inventoryItems stack数组
     * @return 当前是否存在相同物品，存在返回index，反之返回-1
     */
    private int getIndexItem(ItemStack stack, ItemStack[] inventoryItems){
        for(int i = 0;i < inventoryItems.length;i++){
            if(stack.isSimilar(inventoryItems[i])){
                if(inventoryItems[i].getAmount()<DEFAULT_STACK){
                    return i;
                }
            }
        }
        return -1;
    }


    /**
     * 判断是否给与物品，且物品数 > 0
     * @param commandArray 指令数组
     * @param player 玩家对象
     * @return 数值，如果为零则证明错误
     */
    private int giveCommandCheck(String[] commandArray, Player player){
        if(player.hasPermission(ADMIN_PERMISSION)) {
            if (commandArray.length > 1) {
                if (commandArray[1] != null && player.hasPermission(ADMIN_PERMISSION)) {
                    int amount = Integer.parseInt(commandArray[1]);
                    return Math.max(amount, 0);
                }
            }
            PlayerMsgUtils.sendMsg(player,"&b&l请输入给与强化石物品数量");
        }
        return 0;
    }

    /**
     * 检查物品是否可以被强化
     * @param stack itemStack
     * @param player 玩家对象
     */
    private void strengthCheck(ItemStack stack, Player player){
        if(stack!=null){
            player.getInventory().setItemInMainHand(stack);
        }
    }

    public void setPlugin(StrengthPlus plugin) {
        this.plugin = plugin;
    }

    public void setFactory(ConfigFactory factory) {
        this.factory = factory;
    }

}
