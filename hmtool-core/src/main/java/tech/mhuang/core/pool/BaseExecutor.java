package tech.mhuang.core.pool;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;

/**
 * 重构线程池的执行
 *
 * @author mhuang
 * @since 1.0.0
 */
public interface BaseExecutor extends Executor {

    /**
     * 执行
     *
     * @param command 执行的线程
     * @return 执行结果
     */
    Future<?> submit(Runnable command);
}
