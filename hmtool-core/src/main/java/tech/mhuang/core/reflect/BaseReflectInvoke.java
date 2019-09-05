package tech.mhuang.core.reflect;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * 反射调用抽象.
 * <p>可自行实现对应的实现</p>
 *
 * @author mhuang
 * @since 1.0.0
 */
public interface BaseReflectInvoke {

    Map<Class<?>, Class<?>> CONST_TYPES = new LinkedHashMap<Class<?>, Class<?>>() {
        private static final long serialVersionUID = 1L;

        {
            put(HashMap.class, Map.class);
            put(LinkedHashMap.class, Map.class);
            put(TreeMap.class, Map.class);
            put(Hashtable.class, Map.class);
            put(ArrayList.class, List.class);
            put(LinkedList.class, List.class);
        }
    };

    default Class<?> checkType(Object obj) {
        if (CONST_TYPES.containsKey(obj.getClass())) {
            return CONST_TYPES.get(obj.getClass());
        } else {
            return obj.getClass();
        }
    }

    default Class<?>[] getClasses(Object[] params) {
        Class<?>[] clazzes = new Class[params.length];
        int index = 0;
        for (Object obj : params) {
            clazzes[index++] = checkType(obj);
        }
        return clazzes;
    }

    /**
     * 抽象获取方法的值
     *
     * @param clazz      获取方法的类
     * @param methodName 获取的方法名
     * @param params     获取方法的参数
     * @param <T>        应答的结果类型
     * @return 返回方法的参数值
     * @throws NoSuchMethodException     没有找到方法异常
     * @throws SecurityException         安全异常
     * @throws IllegalAccessException    非法访问异常
     * @throws IllegalArgumentException  非法参数异常
     * @throws InvocationTargetException 调用目标异常
     * @throws InstantiationException    实例化异常
     */
    <T> T getMethodToValue(Class<?> clazz,
                           String methodName,
                           Object... params)
            throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, InstantiationException;

    /**
     * 抽象获取方法的值
     *
     * @param clazzName  获取方法的类名
     * @param methodName 获取的方法名
     * @param params     获取方法的参数
     * @param <T>        应答的结果类型
     * @return 返回方法的参数值
     * @throws NoSuchMethodException     没有找到方法异常
     * @throws SecurityException         安全异常
     * @throws IllegalAccessException    非法访问异常
     * @throws IllegalArgumentException  非法参数异常
     * @throws InvocationTargetException 调用目标异常
     * @throws InstantiationException    实例化异常
     * @throws ClassNotFoundException    未找到对应Class异常
     */
    <T> T getMethodToValue(String clazzName,
                           String methodName,
                           Object... params)
            throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, InstantiationException, ClassNotFoundException;

}
