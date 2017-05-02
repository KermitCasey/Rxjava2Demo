package com.wesley.rxjavacore.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

/**
 * @author Wesley
 * @CreatedTime 2017/4/26 18:26
 * @Company SenseFun
 * @classDescription:
 * Activity基类
 */

public abstract class BaseBindingActivity<T extends ViewDataBinding> extends AppCompatActivity {

    protected String TAG;
    protected Context mCtx;
    private T dataBinding;
    protected Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mCtx = this;
        dataBinding = DataBindingUtil.setContentView(this, getLayoutResId());
        TAG = getClass().getSimpleName();

        getIntentData();
        initView();
        initData();
    }

    public T getDataBinding() {
        return dataBinding;
    }


    @Override
    protected void onResume() {
        super.onResume();
        TAG = getClass().getSimpleName();
        RefershData();
    }

    @Override
    protected void onDestroy() {
        mCtx = null;
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
    protected abstract void initView();

    /**
     * 初始化其他数据Data
     */
    protected abstract void initData();

    /**
     * Activity重启时刷新数据Data
     */
    protected abstract void RefershData();

}
