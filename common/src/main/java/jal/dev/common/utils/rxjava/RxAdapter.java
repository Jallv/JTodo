package jal.dev.common.utils.rxjava;


import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.reactivestreams.Publisher;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import jal.dev.common.utils.rxjava.CommonRxTask;
import jal.dev.common.utils.rxjava.IIOTask;
import jal.dev.common.utils.rxjava.IUITask;

/**
 * Description: <Rx适配器><br>
 * Author:      mxdl<br>
 * Date:        2019/3/18<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class RxAdapter {
    /**
     * 生命周期绑定
     *
     * @param lifecycle Activity
     */
    public static <T> LifecycleTransformer<T> bindUntilEvent(@NonNull LifecycleProvider lifecycle) {
        if (lifecycle != null) {
            return lifecycle.bindUntilEvent(ActivityEvent.DESTROY);
        } else {
            throw new IllegalArgumentException("context not the LifecycleProvider type");
        }
    }
    /**
     * 生命周期绑定
     *
     * @param lifecycle Activity
     */
    public static <T> LifecycleTransformer<T> bindUntilFragmentEvent(@NonNull LifecycleProvider lifecycle) {
        return lifecycle.bindUntilEvent(FragmentEvent.DESTROY);
    }

    /**
     * 线程调度器
     */
    public static <T> ObservableTransformer<T, T> schedulersTransformer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
    public static <T> FlowableTransformer<T, T> schedulersFlowableTransformer() {
        return new FlowableTransformer<T, T>() {

            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
    public static <T> SingleTransformer<T, T> schedulersSingleTransformer() {
        return new SingleTransformer<T, T>() {

            @Override
            public SingleSource<T> apply(Single<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 在ui线程中工作
     *
     * @param uiTask
     */
    public static Disposable doInUIThread(IUITask uiTask) {
        return Observable.just(uiTask)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<IUITask>() {
                    @Override
                    public void accept(IUITask iuiTask) {
                        iuiTask.doInUIThread();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    /***
     * 在IO线程中执行任务
     * @param ioTask
     */
    public static Disposable doInIOThread(IIOTask ioTask) {
        return Observable.just(ioTask)
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<IIOTask>() {
                    @Override
                    public void accept(IIOTask iioTask) {
                        iioTask.doInIOThread();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    /**
     * 执行Rx通用任务 (IO线程中执行耗时操作 执行完成调用UI线程中的方法)
     */
    public static <T> Disposable executeRxTask(CommonRxTask<T> t,LifecycleTransformer<CommonRxTask<T>> transformer) {
        CommonRxTask.MyOnSubscribe<CommonRxTask<T>> onsubscribe = new CommonRxTask.MyOnSubscribe<CommonRxTask<T>>(t) {

            @Override
            public void subscribe(ObservableEmitter<CommonRxTask<T>> emitter) {
                getC().doInIOThread();
                emitter.onNext(getC());
                emitter.onComplete();
            }
        };
        return Observable.create(onsubscribe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(new Consumer<CommonRxTask<T>>() {
                    @Override
                    public void accept(CommonRxTask<T> tCommonRxTask) {
                        tCommonRxTask.doInUIThread();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

}
