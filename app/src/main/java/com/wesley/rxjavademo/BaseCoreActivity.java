package com.wesley.rxjavademo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wesley.rxjavacore.utils.ToastUtils;


/**
 * Created by Weiss on 2017/1/10.
 */

public abstract class BaseCoreActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.getLayoutId());
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        initView();
    }

    public void showToast(String msg){
        ToastUtils.show(msg);
    }

    public void showProgress(String msg){
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    public void dismissProgress(){
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initView();
}
