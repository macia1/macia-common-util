package macia.cache.conf;

import com.sun.deploy.util.StringUtils;
import macia.cache.conf.property.PropertyCache;
import org.junit.Test;

import java.util.Properties;

/**
 * @author zenggs
 * @Date 2022/9/22
 */
public class PropertyCacheTest {

    @Test
    public void test() throws InterruptedException {
        String filePath = "C:\\Users\\aa\\Desktop\\chache.txt";
        PropertyCache propertyCache = new PropertyCache(filePath);
        Properties properties = propertyCache.getProperties();
        do {
            Thread.sleep(3000);
            for (int i = 0; i < 10; i++) {
                String key = properties.getProperty("key"+i);
                if (key != null && !"".equals(key)){
                    System.out.println("key"+i+" : " + key);
                }
            }
        }while (true);
    }


}
