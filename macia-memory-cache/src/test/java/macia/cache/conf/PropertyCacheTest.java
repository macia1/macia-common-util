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
        String filePath = "C:\\Users\\zengguosheng\\Desktop\\chache.txt";
        PropertyCache propertyCache = new PropertyCache(filePath);
        do {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000);
                String key = propertyCache.getProperties("key1");
//                if (key != null && !"".equals(key)){
//                    System.out.println("key"+i+" : " + key);
//                }
            }
        }while (true);
    }


}
