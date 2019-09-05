package tech.mhuang.core.id;

/**
 * 生成id
 *
 * @author mhuang
 * @since 1.0.0
 */
public interface BaseIdeable<T> {

    /**
     * id生成
     *
     * @return id结果
     */
    T generateId();
}
