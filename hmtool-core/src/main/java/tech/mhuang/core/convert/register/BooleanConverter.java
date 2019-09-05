package tech.mhuang.core.convert.register;

import tech.mhuang.core.convert.AbstractConverter;

/**
 * 布尔转换器
 *
 * @author mhuang
 * @since 1.0.0
 */
public class BooleanConverter extends AbstractConverter<Boolean> {

    @Override
    public Boolean convert(Object source) {
        if (source instanceof Boolean || source.getClass() == boolean.class) {
            return (Boolean) source;
        }
        throw new IllegalArgumentException("原类型无法进行bool转换");
    }
}