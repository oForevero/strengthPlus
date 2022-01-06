package top.mccat.dao;

/**
 * @ClassName: strengthDao
 * @Description: TODO
 * @Author: Raven
 * @Date: 2022/1/6
 * @Version: 1.0
 */
public interface StrengthDao {
    void normalStrength();

    void safeStrength();

    void successStrength();

    void adminStrength();

    /**
     * 用于给玩家展示菜单面板的方法
     */
    void infoMenu();
}
