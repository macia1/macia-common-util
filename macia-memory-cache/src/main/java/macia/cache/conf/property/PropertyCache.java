package macia.cache.conf.property;

import macia.cache.conf.trans.DataTrans;
import macia.cache.conf.trans.DefaultDataTrans;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
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

    /**
     * 数据转换方式
     */
    private DataTrans dataTrans;

    public PropertyCache(String filePath) {
        this.filePath = filePath;
        /**
         * 定时监控文件修改时间
         */
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        scheduledExecutorService.scheduleAtFixedRate(new SyncFileProperties(),0, 1, TimeUnit.SECONDS);
        dataTrans = new DefaultDataTrans();
    }

    public PropertyCache(String filePath, DataTrans dataTrans) {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        scheduledExecutorService.scheduleAtFixedRate(new SyncFileProperties(),0, 1, TimeUnit.SECONDS);
        this.filePath = filePath;
        this.dataTrans = dataTrans;
    }

    public <T> T getProperties(String propertyPath) {
        final Object transform = dataTrans.transform(propertyPath, properties.getProperty(propertyPath));
        return (T) transform;
    }

    private class SyncFileProperties implements Runnable{
        private long lastModify = -1;
        @Override
        public void run() {
            File file = new File(filePath);
            long modifiedTimeStamp = file.lastModified();
            if (lastModify != modifiedTimeStamp){
                lastModify = modifiedTimeStamp;
                final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.printf("重新加载配置文件: %s, 上次修改时间: %s \n", filePath, simpleDateFormat.format(new Date(lastModify)));
                properties.clear();
                try (FileInputStream fileInputStream = new FileInputStream(file)){
                    properties.load(fileInputStream);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                Set<Object> keySet = properties.keySet();
                for (Object key : keySet) {
                    System.out.printf("%s: %s \n", key,properties.getProperty((String) key).substring(0, Math.min(properties.getProperty((String) key).length(),30)));
                }
            }
        }
    }
}
