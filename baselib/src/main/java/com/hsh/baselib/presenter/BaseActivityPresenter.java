package com.hsh.baselib.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hsh.baselib.model.RequestParam;
import com.hsh.baselib.model.Result;
import com.hsh.baselib.net.HttpClient;
import com.hsh.baselib.net.OnResultListener;
import com.hsh.baselib.utils.LogUtil;
import com.hsh.baselib.view.IBaseView;

import java.util.Map;

import okhttp3.RequestBody;

/**
 * presenter基类
 * Created by carr
 */
@SuppressWarnings("unused")
public abstract class BaseActivityPresenter<V extends IBaseView>{
    protected Context mContext;
    protected Activity activity;
    protected V mView;

    public BaseActivityPresenter(Context context, V view) {
        this.mContext = context;
        this.mView = view;
        this.activity=(Activity) context;
    }

    public void onCreate(Bundle savedInstanceState) {
    }

    public void onStart() {
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onStop() {
    }

    public void onDestroy() {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){};



}
