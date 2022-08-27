package macia.common.trace.common;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author zenggs
 * @Date 2022/1/20
 */
@Slf4j
public class TraceContext {

    private static final ThreadLocal<String> CURRENT_TRACE = new ThreadLocal<>();

    public static String init(){
        if (CURRENT_TRACE.get() == null){
            CURRENT_TRACE.set("t_".concat(UUID.randomUUID().toString().replace("-", "")));
            log.debug("初始化traceID={}", CURRENT_TRACE.get());
        }
        return CURRENT_TRACE.get();
    }

    public static void remove(){
        CURRENT_TRACE.remove();
    }

    public static String getTraceId(){
        if (CURRENT_TRACE.get() == null){
            return init();
        }
        return CURRENT_TRACE.get();
    }

}
