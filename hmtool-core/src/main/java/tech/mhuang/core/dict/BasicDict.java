package tech.mhuang.core.dict;

import java.util.LinkedHashMap;

/**
 * 字典类
 *
 * @author mhuang
 * @since 1.0.0
 */
public class BasicDict extends LinkedHashMap<String, Object> {

    public BasicDict() {
        super();
    }

    /**
     * set集合后可继续进行操作
     *
     * @param key   键
     * @param value 值
     * @return BasicDict
     */
    public BasicDict set(String key, Object value) {
        super.put(key, value);
        return this;
    }

    /**
     * 根据键获取对应字典的值.
     *
     * @param key   键
     * @param clazz 转换后值的class
     * @param <T>   转换后值的类型
     * @return 转换后的对象
     */
    public <T> T get(String key, Class<T> clazz) {
        return get(key, null, clazz);
    }

    /**
     * 根据健获取对应字典的值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @param clazz        转换后值的class
     * @param <T>          转换后值的类型
     * @return 转换后的对象
     */
    public <T> T get(String key, Object defaultValue, Class<T> clazz) {
        Object value = super.getOrDefault(key, defaultValue);
        return (T) value;
    }
}
