package com.wesley.rxjavacore.base;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Wesley
 * @CreatedTime 2017/4/28 10:23
 * @Company SenseFun
 * @classDescription:
 */

public abstract class BaseBindingFragment<T extends ViewDataBinding> extends Fragment {

    protected String TAG ;
    protected Context mCtx;

    protected T dataBinding;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCtx = getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (dataBinding == null) {
            dataBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), container,false);
            getIntentData();
            initView(dataBinding, savedInstanceState);
            initData();
        }
        ViewGroup parent = (ViewGroup) dataBinding.getRoot().getParent();
        if (parent != null) {
            parent.removeView(dataBinding.getRoot());
        }
        return dataBinding.getRoot();
    }

    public T getDataBinding() {
        return dataBinding;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        TAG = getClass().getSimpleName();
        RefershData();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 获取layout id
     */
    protected abstract int getLayoutResId();

    /**
     * 获取Intent中传递的数据
     */
    protected abstract void getIntentData();

    /**
     * 初始化各类view对象或者添加点击事件
     */
    protected void initView(T dataBinding, Bundle savedInstanceState){

    }

    /**
     * 初始化其他数据Data
     */
    protected abstract void initData();

    /**
     * 刷新数据
     */
    protected abstract void RefershData();


}
