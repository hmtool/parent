package tech.mhuang.core.observer;

/**
 * 观察者类型
 *
 * @author mhuang
 * @since 1.0.0
 */
public enum ObserverType {

    /**
     * 同步
     */
    SYNC(1),

    /**
     * 异步
     */
    NSYNC(2);

    private final int value;

    public int getValue() {
        return value;
    }

    ObserverType(int value) {
        this.value = value;
    }
}
