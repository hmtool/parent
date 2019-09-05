package tech.mhuang.core.util;


import java.util.Collection;
import java.util.Map;

/**
 * 集合工具类
 *
 * @author mhuang
 * @since 1.0.0
 */
public class CollectionUtil {

    private CollectionUtil() {

    }

    /**
     * 如果提供的Collection为{@code null}或为空，则返回{@code true}。
     * 否则，请返回{@code false}。
     *
     * @param collection 集合
     * @return boolean 给定的Collection是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * 如果提供的Collection不为{@code null}或不为空，则返回{@code true}。
     * 否则，请返回{@code false}。
     *
     * @param collection 集合
     * @return boolean 给定的Collection是否不为空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 如果提供的数组对象为为{@code null}或为空，则返回{@code true}。
     * 否则，请返回{@code false}。
     *
     * @param obj 检查这个数组
     * @return 给定的数组是否为空
     */
    public static boolean isEmpty(Object[] obj) {
        return obj == null || obj.length == 0;
    }

    /**
     * 如果提供的数组不为{@code null}或不为空，则返回{@code true}。
     * 否则，请返回{@code false}。
     *
     * @param obj 检查这个obj
     * @return 给定的数组是否不为空
     */
    public static boolean isNotEmpty(Object[] obj) {
        return !isEmpty(obj);
    }

    /**
     * 如果提供的Map为{@code null}或为空，则返回{@code true}。
     * 否则，请返回{@code false}。
     *
     * @param map 检查这个map
     * @return 给定的Map是否为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    /**
     * 如果提供的Map不为{@code null}或不为空，则返回{@code true}。
     * 否则，请返回{@code false}。
     *
     * @param map 检查这个map
     * @return 给定的Map是否不为空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }
}
