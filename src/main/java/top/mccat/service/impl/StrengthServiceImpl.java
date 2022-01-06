package top.mccat.service.impl;

import org.bukkit.entity.Player;
import top.mccat.service.StrengthService;

/**
 * @ClassName: StrengthServiceImpl
 * @Description: service层用于逻辑思维，进行逻辑的判断
 * @Author: Raven
 * @Date: 2022/1/6
 * @Version: 1.0
 */
public class StrengthServiceImpl implements StrengthService {
    private Player player;
    @Override
    public void infoMenu() {

    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
