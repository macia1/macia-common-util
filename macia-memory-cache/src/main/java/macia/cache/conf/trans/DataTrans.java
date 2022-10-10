package macia.cache.conf.trans;

/**
 * @author zenggs
 * @Date 2022/10/10
 */
public interface DataTrans {

    /**
     * 数据转换
     * @param path 属性路径
     * @param data 属性值字节类型
     * @return 属性值
     */
    public Object transform(String path,String data);

}
