package macia.common.trace.resultful;

import macia.common.trace.common.TraceContext;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * @author zenggs
 * @Date 2022/1/20 11:25
 * restfulTemplate调用链路传递
 */
@Component
public class ActionTrackInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        httpRequest.getHeaders().add("traceId", TraceContext.getTraceId());
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
