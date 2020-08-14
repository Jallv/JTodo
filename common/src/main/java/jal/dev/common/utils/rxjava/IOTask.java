package jal.dev.common.utils.rxjava;

/**
 * 在IO线程中执行的任务
 */
public abstract class IOTask<T> implements IIOTask {

    protected T t;

    public IOTask(T t) {
        this.t = t;
    }

    public IOTask() {
    }

    public abstract void doInIOThread();
}