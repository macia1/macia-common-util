package macia.cache.conf;

/**
 * @author zenggs
 * @Date 2022/10/10
 */

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "macia.conf")
public class CacheProperties {
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
