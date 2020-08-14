package jal.dev.common.utils.rxjava;

/**
 * 通用任务接口
 */
public interface ICommonRxTask {
    /**
     * 在IO线程执行
     */
    void doInIOThread();

    /**
     * 在UI线程执行
     */
    void doInUIThread();
}