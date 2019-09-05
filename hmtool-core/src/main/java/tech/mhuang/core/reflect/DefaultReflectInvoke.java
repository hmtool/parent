package tech.mhuang.core.reflect;

import tech.mhuang.core.check.CheckAssert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * JDK反射封裝
 *
 * @author mhuang
 * @since 1.0.0
 */
public class DefaultReflectInvoke implements BaseReflectInvoke {

    /**
     * 反射调用方法
     *
     * @param clazz      调用的class类
     * @param methodName 调用的方法
     * @param params     调用的参数
     * @return 返回类型
     * @throws NoSuchMethodException     没有找到方法异常
     * @throws SecurityException         安全异常
     * @throws IllegalAccessException    非法访问异常
     * @throws IllegalArgumentException  非法参数异常
     * @throws InvocationTargetException 调用目标异常
     * @throws InstantiationException    实例化异常
     */
    @Override
    public <T> T getMethodToValue(Class<?> clazz,
                                  String methodName,
                                  Object... params)
            throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, InstantiationException {
        CheckAssert.check(clazz, "没有传递类");
        CheckAssert.check(methodName, "没有传递方法名称");
        if (params != null && params.length > 0) {
            Class<?>[] clazzes = getClasses(params);
            Method method = clazz.getMethod(methodName, clazzes);
            return (T) method.invoke(clazz.newInstance(), params);
        } else {
            Method method = clazz.getMethod(methodName);
            return (T) method.invoke(clazz.newInstance());
        }
    }

    /**
     * 反射调用方法
     *
     * @param clazzName  调用的class类的全路径
     * @param methodName 调用的方法
     * @param params     调用的参数
     * @return 返回类型
     * @throws NoSuchMethodException     没有找到方法异常
     * @throws SecurityException         安全异常
     * @throws IllegalAccessException    非法访问异常
     * @throws IllegalArgumentException  非法参数异常
     * @throws InvocationTargetException 调用目标异常
     * @throws InstantiationException    实例化异常
     */
    @Override
    public <T> T getMethodToValue(String clazzName, String methodName, Object... params) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        CheckAssert.check(clazzName, "没有传递类名称");
        Class clazz = Class.forName(clazzName);
        return getMethodToValue(clazz, methodName, params);
    }
}
