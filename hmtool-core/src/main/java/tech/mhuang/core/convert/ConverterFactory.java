package tech.mhuang.core.convert;

import tech.mhuang.core.check.CheckAssert;
import tech.mhuang.core.convert.register.BooleanConverter;
import tech.mhuang.core.convert.register.IntegerConverter;
import tech.mhuang.core.convert.register.StringConverter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 转换工厂类
 *
 * @author mhuang
 * @since 1.0.0
 */
public class ConverterFactory {

    /**
     * 默认提供的转换器
     */
    private Map<Class, AbstractConverter> defaultConverterMap = new ConcurrentHashMap<>();

    /**
     * 扩展提供的转换器、通过addConverter方法进行添加、优先与默认转换器
     */
    private Map<Class, AbstractConverter> customConverterMap = new ConcurrentHashMap<>();

    /**
     * 内部单列模式保证可行性
     */
    private static class LazyHolder {
        private final static ConverterFactory INSTANCE = new ConverterFactory();
    }

    /**
     * 新增转换器、新增的转换器都将放入自定义下、优先选择
     *
     * @param clazz     转换绑定的类
     * @param converter 类的转换器
     * @return 转换器的工厂类
     */
    public ConverterFactory addConverter(Class clazz, AbstractConverter converter) {
        customConverterMap.put(clazz, converter);
        return this;
    }

    /**
     * 避免通过构造方法获取
     */
    private ConverterFactory() {
        defaultConverterMap.put(String.class, new StringConverter());
        defaultConverterMap.put(Boolean.class, new BooleanConverter());
        defaultConverterMap.put(Integer.class, new IntegerConverter());
    }

    /**
     * 根据类来获取转换器
     *
     * @param clazz 类
     * @return 返回对应的转换器
     */
    public BaseConverter getConvertByClass(Class clazz) {
        CheckAssert.check(clazz, "未指定转换器类型...");
        //pre query custom
        if (customConverterMap.containsKey(clazz)) {
            return customConverterMap.get(clazz);
        }
        //don`t custom query default
        if (defaultConverterMap.containsKey(clazz)) {
            return defaultConverterMap.get(clazz);
        }
        throw new NullPointerException(String.format("不存在对应%s的转换器", clazz));
    }

    /**
     * 获取实例
     *
     * @return 获取转换器工厂
     */
    public static ConverterFactory getInstance() {
        return LazyHolder.INSTANCE;
    }

}
