package tech.mhuang.core.editor;

/**
 * 编辑器接口
 *
 * @author mhuang
 * @since 1.0.0
 */
public interface BaseEditor<T> {

    /**
     * 编辑
     *
     * @param t 需要编辑的对象
     * @return 编辑后的对象
     */
    T edit(T t);
}
