package tech.mhuang.core.convert.register;

import tech.mhuang.core.convert.AbstractConverter;

/**
 * Integer 转换器
 *
 * @author mhuang
 * @since 1.0.0
 */
public class IntegerConverter extends AbstractConverter<Integer> {

    @Override
    public Integer convert(Object source) {
        if (source instanceof Integer || source.getClass() == int.class) {
            return (Integer) source;
        }
        throw new IllegalArgumentException("原类型无法转换成Integer类型");
    }
}
