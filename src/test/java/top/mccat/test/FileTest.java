package top.mccat.test;

import org.junit.Test;
import top.mccat.utils.ConfigFactory;

/**
 * @ClassName: FileTest
 * @Description: TODO
 * @Author: Raven
 * @Date: 2022/1/6
 * @Version: 1.0
 */
public class FileTest {
    @Test
    public void testInit(){
        ConfigFactory factory = new ConfigFactory(null);
        factory.initFile();
    }
}
