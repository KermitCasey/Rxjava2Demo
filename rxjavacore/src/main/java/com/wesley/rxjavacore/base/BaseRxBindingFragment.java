package com.wesley.rxjavacore.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.wesley.rxjavacore.entity.HttpResult;

import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Wesley
 * @CreatedTime 2017/5/2 14:52
 * @Company SenseFun
 * @classDescription:
 */

public abstract class BaseRxBindingFragment<T extends ViewDataBinding> extends BaseBindingFragment<T> {

    private CompositeDisposable disposables2Stop;// 管理Stop取消订阅者者
    private CompositeDisposable disposables2Destroy;// 管理Destroy取消订阅者者

    /**
     * Rx优雅处理服务器返回
     *
     * @param <E>
     * @return
     */
    public <E> ObservableTransformer<HttpResult<E>, E> handleResult() {
        BaseRxBindingActivity baseActivity=(BaseRxBindingActivity) getActivity();
        return baseActivity.handleResult();
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
