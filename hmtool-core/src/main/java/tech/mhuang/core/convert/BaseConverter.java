package tech.mhuang.core.convert;

/**
 * 转换器接口
 *
 * @author mhuang
 * @since 1.0.0
 */
public interface BaseConverter<T> {


    /**
     * 根据原类型数据转换成指定数据类型、并且带上默认值
     *
     * @param source       源类型数据
     * @param defaultValue 默认值
     * @return T 转换后的对象
     */
    T convert(Object source, T defaultValue);
}
