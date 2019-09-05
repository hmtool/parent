package tech.mhuang.core.async;

/**
 * 异步执行
 *
 * @author mhuang
 * @since 1.0.0
 */
public interface AsyncTaskService<T> {

    /**
     * 异步提交
     *
     * @param task 提交的任务
     */
    void submit(AsyncTask<T> task);

}
