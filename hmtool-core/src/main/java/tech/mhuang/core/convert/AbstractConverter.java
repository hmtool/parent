package tech.mhuang.core.convert;

import tech.mhuang.core.util.ObjectUtil;

/**
 * 转换器抽象类
 *
 * @author mhuang
 * @since 1.0.0
 */
public abstract class AbstractConverter<T> implements BaseConverter<T> {

    /**
     * imp BaseConverer interface
     *
     * @param source       源类型数据
     * @param defaultValue 默认值
     * @return convert 需要实现
     */
    @Override
    public T convert(Object source, T defaultValue) {
        if (ObjectUtil.isEmpty(source)) {
            return defaultValue;
        }

        return convert(source);
    }

    /**
     * 抽象方法、需继承后自行实现
     *
     * @param source 源数据
     * @return 转换后的数据类型的数据
     * @throws IllegalArgumentException 转换失败则需抛出该对应异常
     */
    public abstract T convert(Object source) throws IllegalArgumentException;
}
