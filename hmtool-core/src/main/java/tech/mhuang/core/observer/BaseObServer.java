package tech.mhuang.core.observer;

/**
 * 观察者接口
 *
 * @param T 代表请求的数据类型
 * @author mhuang
 * @since 1.0.0
 */
public interface BaseObServer<T> {

    /**
     * 获取观察者的名称
     *
     * @return 观察者的名称
     */
    String getName();

    /**
     * 获取观察者的通知类型.
     *
     * @return 同步or异步
     */
    ObserverType getType();

    /**
     * 观察者执行接口
     *
     * @param data 请求的数据
     */
    void execute(T data);
}
