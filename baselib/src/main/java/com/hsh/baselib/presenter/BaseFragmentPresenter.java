package com.hsh.baselib.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hsh.baselib.presenter.interfaces.IBasePresenter;
import com.hsh.baselib.view.IBaseView;

/**
 * presenter基类
 * Created by Carr on 16/06/16.
 */
@SuppressWarnings("unused")
public abstract class BaseFragmentPresenter<V extends IBaseView> implements IBasePresenter {
    protected Context mContext;
    protected Activity activity;
    protected V mView;

    public BaseFragmentPresenter(Context context, V view) {
        this.mContext = context;
        this.activity=(Activity) context;
        this.mView = view;
    }

    public void onCreateView(Bundle savedInstanceState) {
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    }

    public void onStart() {
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onStop() {
    }

    public void onDestroyView() {

    }

    public void onDestroy() {
    }

    public void onDetach() {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){}


}
