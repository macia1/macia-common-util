package macia.cache.conf.property;

import lombok.SneakyThrows;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zenggs
 * @Date 2022/9/22
 */
public class PropertyCache {
    /**
     * 订阅路径
     */
    private final String filePath;

    /**
     * 文件缓存内容
     */
    private final ConcurrentHashMap<String,Object> cache = new ConcurrentHashMap<>();
    /**
     * 缓存属性
     */
    private final Properties properties = new Properties();

    public PropertyCache(String filePath) {
        this.filePath = filePath;
        /**
         * 定时监控文件修改时间
         */
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        scheduledExecutorService.scheduleAtFixedRate(new SyncFileProperties(),0, 1, TimeUnit.SECONDS);
    }

    public Properties getProperties() {
        return properties;
    }

    private class SyncFileProperties implements Runnable{
        private long lastModify = 0;
        @SneakyThrows
        @Override
        public void run() {
            File file = new File(filePath);
            long modifiedTimeStamp = file.lastModified();
            if (lastModify != modifiedTimeStamp){
                lastModify = modifiedTimeStamp;
                System.out.printf("重新加载配置文件: %s, 上次修改时间: %d \n", filePath, lastModify);
                properties.clear();
                properties.load(new FileInputStream(file));
            }
        }
    }
}
