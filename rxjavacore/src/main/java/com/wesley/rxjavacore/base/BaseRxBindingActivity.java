package com.wesley.rxjavacore.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.wesley.rxjavacore.entity.HttpResult;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * @author Wesley
 * @CreatedTime 2017/5/2 14:50
 * @Company SenseFun
 * @classDescription:
 */

public abstract class BaseRxBindingActivity<T extends ViewDataBinding> extends BaseBindingActivity<T> {

    private CompositeDisposable disposables2Stop;// 管理Stop取消订阅者者
    private CompositeDisposable disposables2Destroy;// 管理Destroy取消订阅者者


    /**
     * Rx优雅处理服务器返回
     *
     * @param <E>
     * @return
     */
    public <E> ObservableTransformer<HttpResult<E>, E> handleResult() {
        return new ObservableTransformer<HttpResult<E>, E>(){
            @Override
            public ObservableSource<E> apply(@NonNull Observable<HttpResult<E>> upstream) {
                return upstream.flatMap(new Function<HttpResult<E>, ObservableSource<E>>() {
                    @Override
                    public ObservableSource<E> apply(@NonNull HttpResult<E> result) throws Exception {
                        if (result.isSuccess()) {
                            return createData(result.results);
                        } else if (result.isTokenInvalid()) {
                            //处理token时效
//                               tokenInvalid();
                        } else {
                            return Observable.error(new Exception(result.msg));
                        }
                        return Observable.empty();
                    }
                });
            }
        };
    }

    private <E> Observable<E> createData(final E E) {
        return Observable.create(new ObservableOnSubscribe<E>(){
            @Override
            public void subscribe(@NonNull ObservableEmitter<E> subscriber) throws Exception {
                try {
                    subscriber.onNext(E);
                    subscriber.onComplete();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public boolean addRxStop(Disposable disposable) {
        if (disposables2Stop == null) {
            throw new IllegalStateException(
                    "addUtilStop should be called between onStart and onStop");
        }
        disposables2Stop.add(disposable);
        return true;
    }

    public boolean addRxDestroy(Disposable disposable) {
        if (disposables2Destroy == null) {
            throw new IllegalStateException(
                    "addUtilDestroy should be called between onCreate and onDestroy");
        }
        disposables2Destroy.add(disposable);
        return true;
    }

    public void remove(Disposable disposable) {
        if (disposables2Stop == null && disposables2Destroy == null) {
            throw new IllegalStateException("remove should not be called after onDestroy");
        }
        if (disposables2Stop != null) {
            disposables2Stop.remove(disposable);
        }
        if (disposables2Destroy != null) {
            disposables2Destroy.remove(disposable);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        if (disposables2Destroy != null) {
            throw new IllegalStateException("onCreate called multiple times");
        }
        disposables2Destroy = new CompositeDisposable();
        super.onCreate(savedInstanceState);
    }

    public void onStart() {
        if (disposables2Stop != null) {
            throw new IllegalStateException("onStart called multiple times");
        }
        disposables2Stop = new CompositeDisposable();
        super.onStart();
    }

    public void onStop() {
        if (disposables2Stop == null) {
            throw new IllegalStateException("onStop called multiple times or onStart not called");
        }
        disposables2Stop.dispose();
        disposables2Stop = null;
        super.onStop();
    }

    public void onDestroy() {
        if (disposables2Destroy == null) {
            throw new IllegalStateException(
                    "onDestroy called multiple times or onCreate not called");
        }
        disposables2Destroy.dispose();
        disposables2Destroy = null;
        super.onDestroy();
    }

}
