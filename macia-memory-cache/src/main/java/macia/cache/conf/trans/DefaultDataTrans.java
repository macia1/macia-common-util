package macia.cache.conf.trans;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author zenggs
 * @Date 2022/10/10
 */
@Slf4j
public class DefaultDataTrans implements DataTrans{
    @Override
    public Object transform(String path, String data) {
        if (data == null || data.length() == 0) {
            return null;
        }
        System.out.println("属性路径:" + path +",属性值:"
                + data.substring(0, Math.min(data.length(), 10))
                + "...");
        return data;
    }
}
