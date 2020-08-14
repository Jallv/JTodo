package jal.dev.common.utils.rxjava;


import io.reactivex.ObservableOnSubscribe;

/**
 * 通用的Rx执行任务
 */
public abstract class CommonRxTask<T> implements ICommonRxTask {
    public CommonRxTask(T t) {
        this.t = t;
    }

    protected T t;

    /**
     * 在IO线程执行
     */
    public void doInIOThread() {
    }

    /**
     * 在UI线程执行
     */
    public void doInUIThread() {
    }

    public static abstract class MyOnSubscribe<C> implements ObservableOnSubscribe<C> {
        private C c;

        public MyOnSubscribe(C c) {
            this.c = c;
        }

        public C getC() {
            return c;
        }

        public void setC(C c) {
            this.c = c;
        }
    }

}