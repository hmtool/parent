package tech.mhuang.core.observer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 主题工厂类.
 * <p>需要将订阅者进行管理时可使用该工厂进行操作</p>
 *
 * @author mhuang
 * @since 1.0.0
 */
public class ObFactory {

    private static Map<String, BaseSubject> subjectMap = new ConcurrentHashMap<>();

    /**
     * 添加主题到工厂中
     *
     * @param key   别名
     * @param value 主题
     */
    public static void add(String key, BaseSubject value) {
        subjectMap.put(key, value);
    }

    /**
     * 删除工厂中的主题
     *
     * @param key 别名
     */
    public static void remove(String key) {
        subjectMap.remove(key);
    }

    /**
     * 根据别名获取工厂的主题
     *
     * @param key 别名
     * @return 返回对应别名的主题
     */
    public static BaseSubject get(String key) {
        return subjectMap.get(key);
    }
}
