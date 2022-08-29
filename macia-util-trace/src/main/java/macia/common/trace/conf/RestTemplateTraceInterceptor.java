package macia.common.trace.conf;

import macia.common.trace.resultful.ActionTrackInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @author ebiz_zenggs
 * @Date 2022/8/29
 */
@Configuration
public class RestTemplateTraceInterceptor {

    @Resource
    ActionTrackInterceptor actionTrackInterceptor;

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(actionTrackInterceptor));
        return restTemplate;
    }

}
