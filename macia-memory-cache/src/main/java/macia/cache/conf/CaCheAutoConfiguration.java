package macia.cache.conf;

import macia.cache.conf.property.PropertyCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * @author zenggs
 * @Date 2022/10/10
 */
@ConditionalOnClass(CacheProperties.class)
@EnableConfigurationProperties(CacheProperties.class)
@Configuration
public class CaCheAutoConfiguration {

    @Autowired
    private CacheProperties cacheProperties;

    @Bean
    @ConditionalOnMissingBean
    public PropertyCache propertyCache(){
        String filePath = cacheProperties.getFilePath();
        if (StringUtils.isEmpty(filePath)){
            throw new RuntimeException("缓存配置文件路径不能为空！");
        }
        return new PropertyCache(filePath);
    }

}
