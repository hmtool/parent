package tech.mhuang.core.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 默认线程池--基于jdk原生线程池进行封装
 *
 * @author mhuang
 * @since 1.0.0
 */
public class DefaultThreadPool implements BaseExecutor {

    private ExecutorService executorService;

    public static final Long DEFAULT_KEEP_ALIVE_TIME = 0L;
    public static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MILLISECONDS;
    public static final int DEFAULT_CORE_POOL_SIZE = 5;
    public static final int DEFAULT_MAX_POOL_SIZE = 200;
    public static final BlockingQueue<Runnable> DEFAULT_WORK_QUEUE = new LinkedBlockingQueue();
    public static final ThreadFactory DEFAULT_THREAD_FACTORY = Executors.defaultThreadFactory();
    public static final RejectedExecutionHandler DEFAULT_HANDLER = new ThreadPoolExecutor.AbortPolicy();

    /**
     * default configuration.
     */
    public DefaultThreadPool() {
        this(DEFAULT_CORE_POOL_SIZE, DEFAULT_MAX_POOL_SIZE, DEFAULT_KEEP_ALIVE_TIME,
                DEFAULT_TIME_UNIT, DEFAULT_WORK_QUEUE);
    }

    public DefaultThreadPool(boolean fal) {
        this.executorService = Executors.newCachedThreadPool();
        this.executorService = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });
        this.executorService = Executors.newWorkStealingPool();
    }

    public DefaultThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        this(corePoolSize, maximumPoolSize, keepAliveTime,
                unit, workQueue, DEFAULT_THREAD_FACTORY, DEFAULT_HANDLER);
    }

    public DefaultThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        this.executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                threadFactory, handler);
    }

    @Override
    public Future<?> submit(Runnable command) {
        return this.executorService.submit(command);
    }

    @Override
    public void execute(Runnable command) {
        this.executorService.execute(command);
    }
}
