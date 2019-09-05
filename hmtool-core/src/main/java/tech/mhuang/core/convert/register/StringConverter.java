package tech.mhuang.core.convert.register;

import tech.mhuang.core.convert.AbstractConverter;

/**
 * 字符串转换器
 *
 * @author mhuang
 * @since 1.0.0
 */
public class StringConverter extends AbstractConverter<String> {

    @Override
    public String convert(Object source) {
        if (source instanceof Character || source.getClass() == char.class) {
            return String.valueOf(source);
        }
        return source.toString();
    }
}
