package com.hsh.baselib.activity.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hsh.baselib.R;
import com.hsh.baselib.activity.BaseActivity;
import com.hsh.baselib.constanst.SPConstanst;
import com.hsh.baselib.presenter.BaseFragmentPresenter;
import com.hsh.baselib.utils.AntiShake;
import com.hsh.baselib.utils.SPUtils;
import com.hsh.baselib.view.IBaseView;
import com.hsh.baselib.widget.ProgressDialog;

import java.util.List;

import butterknife.ButterKnife;

/**
 * fragment基类
 * Created by Carr 16/06/16.
 */
public abstract class BaseNoPresenterFragment extends Fragment implements IBaseView {
    protected View mContentView;
    protected Context mContext;
    protected AlertDialog.Builder mAlertDialogBuilder;
    protected ProgressDialog mProgressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;

    protected AntiShake shake = new AntiShake();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(getLayoutResId(), container, false);
        mContext = getActivity();
        ButterKnife.bind(this, mContentView);
        initSwipeRefreshLayout();
        initialize();
        return mContentView;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void finish() {
        if (getActivity() != null)
            getActivity().finish();
    }

    /**
     * 获取布局的resId
     */
    @CheckResult
    protected abstract int getLayoutResId();

    /**
     * 初始化
     */
    protected abstract void initialize();



    /**
     * 设置toolbar标题
     *
     * @param title      标题
     * @param isShowHome 是否显示home键
     */
    @SuppressWarnings("unused")
    @Override
    public void setToolbarTitle(@NonNull String title, boolean isShowHome) {
        ((BaseActivity) getActivity()).setToolbarTitle(title, isShowHome);
    }

    /**
     * 设置toolbar标题
     *
     * @param title      标题
     * @param isShowHome 是否显示home键
     */
    @SuppressWarnings("unused")
    @Override
    public void setToolbarTitle(@StringRes int title, boolean isShowHome) {
        ((BaseActivity) getActivity()).setToolbarTitle(title, isShowHome);
    }

    /**
     * 显示信息提示条
     *
     * @param tipsResId 提示信息字符串的资源id
     */
    @SuppressWarnings("unused")
    @Override
    public void showTips(@StringRes int tipsResId) {
        Toast.makeText(mContext, tipsResId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示信息提示条
     *
     * @param tips 提示信息字符串
     */
    @SuppressWarnings("unused")
    @Override
    public void showTips(@NonNull final String tips) {
       // Toast.makeText(mContext, tips, Toast.LENGTH_SHORT).show();
        ((Activity)mContext).runOnUiThread(new Runnable() {
            public void run() {
                final Toast toast = Toast.makeText(mContext,tips, Toast.LENGTH_LONG);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        toast.cancel();
                    }
                },1000);
            }
        });

    }


    /**
     * 显示信息提示条
     *
     * @param tips 提示信息字符串
     */
    @SuppressWarnings("unused")
    public void showTips(@NonNull final String tips, final int time) {
        // Toast.makeText(mContext, tips, Toast.LENGTH_SHORT).show();
        ((Activity)mContext).runOnUiThread(new Runnable() {
            public void run() {
                final Toast toast = Toast.makeText(mContext,tips, Toast.LENGTH_LONG);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        toast.cancel();
                    }
                },time);
            }
        });

    }



    /**
     * 显示进度条对话框
     */
    @Override
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }


    /**
     * 取消进度条对话框
     */
    @Override
    public void dismissProgressDialog() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing())
                mProgressDialog.dismiss();
        }catch (Exception e){

        }

    }

    /**
     * 显示弹出框
     *
     * @param title                       对话框标题
     * @param message                     对话框的内容
     * @param positiveText                确定按钮的文字
     * @param positiveButtonClickListener 确定按钮的点击事件
     * @param negativeText                取消按钮的文字
     * @param negativeButtonClickListener 取消按钮的点击事件
     */
    @Override
    public void showAlertDialog(@NonNull String title, @NonNull String message, @NonNull String positiveText, DialogInterface.OnClickListener positiveButtonClickListener, @NonNull String negativeText, DialogInterface.OnClickListener negativeButtonClickListener) {
        mAlertDialogBuilder = new AlertDialog.Builder(mContext);
        mAlertDialogBuilder.setTitle(title);
        mAlertDialogBuilder.setMessage(message);
        mAlertDialogBuilder.setPositiveButton(positiveText, positiveButtonClickListener);
        mAlertDialogBuilder.setNegativeButton(negativeText, negativeButtonClickListener);
        mAlertDialogBuilder.show();
    }

    /**
     * 显示弹出框
     *
     * @param title                       对话框标题
     * @param message                     对话框的内容
     * @param positiveText                确定按钮的文字
     * @param positiveButtonClickListener 确定按钮的点击事件
     * @param negativeText                取消按钮的文字
     * @param negativeButtonClickListener 取消按钮的点击事件
     */
    @Override
    public void showAlertDialog(int title, int message, int positiveText, DialogInterface.OnClickListener positiveButtonClickListener, int negativeText, DialogInterface.OnClickListener negativeButtonClickListener) {
        mAlertDialogBuilder = new AlertDialog.Builder(mContext);
        mAlertDialogBuilder.setTitle(title);
        mAlertDialogBuilder.setMessage(message);
        mAlertDialogBuilder.setPositiveButton(positiveText, positiveButtonClickListener);
        mAlertDialogBuilder.setNegativeButton(negativeText, negativeButtonClickListener);
        mAlertDialogBuilder.show();
    }

    /**
     * 显示弹出框
     *
     * @param title                       对话框标题
     * @param message                     对话框的内容
     * @param positiveText                确定按钮的文字
     * @param positiveButtonClickListener 确定按钮的点击事件
     * @param negativeText                取消按钮的文字
     * @param negativeButtonClickListener 取消按钮的点击事件
     */
    @Override
    public void showAlertDialog(int title, String message, int positiveText, DialogInterface.OnClickListener positiveButtonClickListener, int negativeText, DialogInterface.OnClickListener negativeButtonClickListener) {
        mAlertDialogBuilder = new AlertDialog.Builder(mContext);
        mAlertDialogBuilder.setTitle(title);
        mAlertDialogBuilder.setMessage(message);
        mAlertDialogBuilder.setPositiveButton(positiveText, positiveButtonClickListener);
        mAlertDialogBuilder.setNegativeButton(negativeText, negativeButtonClickListener);
        mAlertDialogBuilder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void initSwipeRefreshLayout(){
        swipeRefreshLayout=(SwipeRefreshLayout) mContentView.findViewById(R.id.swipeRefreshLayout);
        if(swipeRefreshLayout!=null){
            swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright,R.color.holo_green_light,R.color.holo_orange_light,R.color.holo_red_light);
            swipeRefreshLayout.setProgressViewOffset(false, 0,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    pullToRefresh();
                }
            });
        }
    }

    protected void pullToRefresh(){}
    protected void showRefreshLayout(boolean flag){
        if(swipeRefreshLayout!=null){
            swipeRefreshLayout.setRefreshing(flag);
        }
    }


}
