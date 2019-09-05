package tech.mhuang.core.async;

import tech.mhuang.core.check.CheckAssert;
import tech.mhuang.core.pool.BaseExecutor;
import tech.mhuang.core.pool.DefaultThreadPool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


/**
 * 任务异步执行类（基于JDK线程池运行）
 *
 * @author mhuang
 * @since 1.1.0
 */
public class DefaultAsyncTaskSupport implements AsyncTaskService {

    private BaseExecutor executor;

    /**
     * 重定义线程池（提供扩展自行定义线程池）
     *
     * @param executor 线程池-需实现BaseExecutor接口
     * @return 线程池对象
     */
    public DefaultAsyncTaskSupport executor(BaseExecutor executor) {
        this.executor = executor;
        return this;
    }

    /**
     * 初始化
     */
    public DefaultAsyncTaskSupport() {
        this.executor = new DefaultThreadPool();
    }

    /**
     * 带线程池的初始化
     *
     * @param corePoolSize    核心池大小
     * @param maximumPoolSize 最大池大小
     * @param keepAliveTime   等待唤醒时间的值
     * @param unit            等待唤醒时间的类型
     * @param queue           队列
     */
    public DefaultAsyncTaskSupport(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue queue) {
        this.executor = new DefaultThreadPool(corePoolSize, maximumPoolSize, keepAliveTime, unit, queue);
    }

    /**
     * 提交任务进行执行
     *
     * @param task 提交的任务
     */
    @Override
    public void submit(AsyncTask task) {

        CheckAssert.check(executor, "执行线程池不能为null、请先初始化....");

        if (task == null) {
            return;
        }

        //异步任务调用
        CompletableFuture.supplyAsync(() -> task.execute(), executor).whenComplete((result, throwable) -> {
            if (throwable == null) {
                task.onSuccess(result);
            } else {
                task.onFailed(throwable);
            }
        });
    }
}
