package macia.common.trace.resultful;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import macia.common.trace.common.TraceContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ebiz_zenggs
 * @Date 2022/8/26
 * control层链路id传递
 */
@Slf4j
public class ControlAdvice {

    /**
     * 1# 使用级别: 类
     * @Around("execution(* com.crawler.parse.control..*.*(..))")
     * 2# 适用级别: 包
     *<bean id="traceAdvice" class="com.crawler.parse.common.TraceAdvice"/>
     *<aop:config>
     *    <aop:aspect ref="traceAdvice" order="0">
     *        <aop:around method="around"
     *                    pointcut="execution(* com.crawler.parse.control..*.*(..))"
     *                    />
     *    </aop:aspect>
     *</aop:config>
     */
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String controllerName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof HttpServletResponse || args[i] instanceof HttpServletRequest) {
                args[i] = null;
            }
        }
        // 将traceID添加到请求头中
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String traceId = servletRequestAttributes.getRequest().getHeader("traceId") != null ? servletRequestAttributes.getRequest().getHeader("traceId") : TraceContext.getTraceId();
        MDC.put("traceId", traceId);
        servletRequestAttributes.getResponse().addHeader("traceId", traceId);
        log.info("开始访问[{}]{}.{} 接口", traceId, controllerName, methodName);
        Object response = null;
        long costTime = 0;
        try {
            response = joinPoint.proceed();
            costTime = System.currentTimeMillis() - start;
            log.error("调用服务[{}] {}.{} ," +
                            "花费时长：{}," +
                            "\n 入参: {} " +
                            "\n 出参: {}",
                    traceId, controllerName, methodName, costTime, JSONObject.toJSON(args), JSONObject.toJSON(response));
        } catch (Throwable e) {
            log.error("调用服务[{}] {}.{} , 异常" +
                            "花费时长：{}," +
                            "\n 入参: {} " +
                            "\n 出参: {}",
                    traceId, controllerName, methodName, costTime, JSONObject.toJSON(args), JSONObject.toJSON(response));
            throw e;
        } finally {
            MDC.clear();
            TraceContext.remove();
        }
        return response;
    }

}
