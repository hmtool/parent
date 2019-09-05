package tech.mhuang.core.util;

/**
 * Object工具类
 *
 * @author mhuang
 * @since 1.0.0
 */
public class ObjectUtil {

    /**
     * 判断对象是否为空
     *
     * @param object 对象
     * @return 若为空则返回true、不为空返回false
     */
    public static boolean isEmpty(Object object) {
        return object == null;
    }

    /**
     * 判断对象是否不为空
     *
     * @param object 对象
     * @return 若为空则返回false、不为空返回true
     */
    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }
}
