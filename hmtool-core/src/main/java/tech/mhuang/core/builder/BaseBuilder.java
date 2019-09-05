package tech.mhuang.core.builder;

/**
 * 建造者模式
 *
 * @param T 构建的对象
 * @author mhuang
 * @since 1.0.0
 */
public interface BaseBuilder<T> {

    /**
     * 构建对应的对象
     *
     * @return 构建后得到的对象
     */
    T builder();
}
