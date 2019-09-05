package tech.mhuang.core.clone;

/**
 * 拷贝接口
 *
 * @param T 拷贝后的对象
 * @author mhuang
 * @since 1.0.0
 */
public interface BaseCloneable<T> extends java.lang.Cloneable {

    /**
     * 拷贝接口
     *
     * @return 拷贝后得到的数据
     */
    T clone();
}
