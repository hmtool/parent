package tech.mhuang.core.convert.register;

import tech.mhuang.core.convert.AbstractConverter;

import java.math.BigDecimal;

/**
 * bigDecimal转换器
 *
 * @author mhuang
 * @since 1.0.0
 */
public class BigDecimalConverter extends AbstractConverter<BigDecimal> {


    @Override
    public BigDecimal convert(Object source) {
        if (source instanceof BigDecimal) {

        }
        return null;
    }
}
