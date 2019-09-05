package tech.mhuang.core.clone;


/**
 * 默认拷贝实现（基于JDK）.
 *
 * @author mhuang
 * @since 1.0.0
 */
public class DefaultCloneableSupport<T> implements BaseCloneable<T> {
    @Override
    public T clone() {
        try {
            return (T) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new CloneRuntimeException(e);
        }
    }
}
