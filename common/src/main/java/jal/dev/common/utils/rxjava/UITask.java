package jal.dev.common.utils.rxjava;

/**
 * 在主线程中执行的任务
 */
public abstract class UITask<T> implements IUITask {

    protected T t;

    public UITask(T t) {
        this.t = t;
    }

    public UITask() {
    }
}