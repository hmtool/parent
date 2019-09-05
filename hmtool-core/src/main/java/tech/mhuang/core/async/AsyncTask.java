package tech.mhuang.core.async;


/**
 * 异步封装
 *
 * @author mhuang
 * @since 1.0.0
 */
public interface AsyncTask<T> {

    /**
     * 任务成功执行的操作
     *
     * @param result 成功的数据
     */
    void onSuccess(T result);

    /**
     * 任务执行失败的操作
     *
     * @param t 失败的异常
     */
    void onFailed(Throwable t);

    /**
     * 任务执行接口
     *
     * @return T 执行任务的接口
     */
    T execute();
}
