package tech.mhuang.core.observer;

/**
 * 主题
 *
 * @author mhuang
 * @since 1.0.0
 */
public interface BaseSubject<T> {

    /**
     * 添加观察者
     *
     * @param obServer 观察者
     */
    void add(BaseObServer<T> obServer);

    /**
     * 删除观察者
     *
     * @param obServer 观察者
     */
    void delete(BaseObServer<T> obServer);

    /**
     * 清空观察者--所有
     */
    void clear();

    /**
     * 发送至观察者
     *
     * @param data 数据
     */
    void send(T data);
}
