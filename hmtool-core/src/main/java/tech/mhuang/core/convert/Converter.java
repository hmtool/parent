package tech.mhuang.core.convert;

/**
 * 转换器调用类.
 * <span>比如从str转换成int、int转换str各种类型的转换等</span>
 *
 * @author mhuang
 * @since 1.0.0
 */
public class Converter {


    /**
     * 转换成string
     *
     * @param value 需要转换的数据
     * @return 转换后的Str结果
     */
    public static String str(Object value) {
        return str(value, null);
    }

    /**
     * 转换string
     *
     * @param value      需要转换的数据
     * @param defaultRst 转换的默认值
     * @return 转换后的Str结果
     */
    public static String str(Object value, String defaultRst) {
        return convert(String.class, value, defaultRst);
    }

    /**
     * 将原数据转换成对应类型的数据
     *
     * @param clazz      转换后的class
     * @param value      数据
     * @param defaultRst 默认值
     * @param <T>        数据换后的类型
     * @return 转换后的结果
     */
    public static <T> T convert(Class<T> clazz, Object value, Object defaultRst) {
        return (T) ConverterFactory.getInstance().getConvertByClass(clazz).convert(value, defaultRst);
    }
}
