package com.wesley.rxjavademo;

import com.wesley.rxjavacore.base.BaseRxBindingActivity;
import com.wesley.rxjavacore.utils.LogUtil;
import com.wesley.rxjavademo.databinding.ActivityMainBinding;
import com.wesley.rxjavademo.entity.Gank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseRxBindingActivity<ActivityMainBinding> {

    Gank gank;
    Map<String, String> params;

    @Override
    protected void getIntentData() {

    }

    @Override
    protected void initData() {
        loadData();
    }

    @Override
    protected void RefershData() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    protected void loadData() {

        params = new HashMap<>(2);
        params.put("gank", "Android");

        gank = new Gank();
        gank.setParam(params);


        addRxDestroy(gank.getPage(1)
                .compose(this.<List<Gank>>handleResult())
                .subscribe(new Consumer<List<Gank>>() {
                    @Override
                    public void accept(@NonNull List<Gank> ganks) throws Exception {
                        LogUtil.d(ganks.size() + "success");
                        getDataBinding().setDataNum(ganks.size() + "success");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        LogUtil.d("fail");
                    }
                }));
    }
}
