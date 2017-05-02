package com.wesley.rxjavacore.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author kermit
 * @Date 2017/3/17 15:26
 * @class Describe
 * ---
 */

public abstract class BaseFragment extends Fragment {

    protected String TAG ;
    protected Context mCtx;
    private View rootView;

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
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResId(), container, false);
            getIntentData();
            initView(rootView, savedInstanceState);
            initData();
            setOnClick();
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    public View getRootView() {
        return rootView;
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
     * 初始化各类view对象
     */
    protected abstract void initView(View rootView, Bundle savedInstanceState);

    /**
     * 初始化其他数据Data
     */
    protected abstract void initData();

    /**
     * 初始化view的点击事件
     */
    protected abstract void setOnClick();


    /**
     * 刷新数据
     */
    protected abstract void RefershData();

}