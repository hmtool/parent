package tech.mhuang.core.observer;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 通用观察者服务.
 *
 * @author mhuang
 * @since 1.0.0
 */
public abstract class AbstractObServer<T> implements BaseObServer<T> {

    /**
     * 观察者名称
     */
    private String name;

    /**
     * 调用的数据
     */
    public T data;

    /**
     * 调用方式
     */
    private ObserverType type = ObserverType.SYNC;

    /**
     * 运行次数
     */
    private AtomicInteger runCount = new AtomicInteger(0);

    /**
     * 最后次调用时间
     */
    private Long lastReqTime;

    @Override
    public String getName() {
        return this.name;
    }

    public Integer getExecuteCount() {
        return runCount.get();
    }

    public AbstractObServer name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ObserverType getType() {
        return this.type;
    }

    public AbstractObServer type(ObserverType type) {
        this.type = type;
        return this;
    }

    public Long getLastReqTime() {
        return this.lastReqTime;
    }

    @Override
    public void execute(T data) {
        this.data = data;
        this.runCount.incrementAndGet();
        this.lastReqTime = System.currentTimeMillis();
        //TODO 异步需重新考虑、CompletableFuture.runAsync(this::execute)在使用自旋锁时波动太大
        if (this.type == ObserverType.NSYNC) {
            try {
                //避免执行时第二次进入
                CompletableFuture future = CompletableFuture.runAsync(this::execute);
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            //默认为同步
            execute();
        }
    }

    /**
     * 封装提供实现
     */
    protected abstract void execute();
}
