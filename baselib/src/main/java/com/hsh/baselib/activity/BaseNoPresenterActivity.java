package com.hsh.baselib.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hsh.baselib.R;
import com.hsh.baselib.constanst.SPConstanst;
import com.hsh.baselib.utils.AntiShake;
import com.hsh.baselib.utils.SPUtils;
import com.hsh.baselib.view.IBaseView;
import com.hsh.baselib.widget.ProgressDialog;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 作者：Carr on 2016/11/2 13:40
 * 邮箱：1120199941@qq.com
 */
public abstract class BaseNoPresenterActivity extends AppCompatActivity implements IBaseView {
    protected Context mContext;

    protected Toolbar mToolbar;
    protected TextView mTvTitle;
    protected TextView mRightTitle;

    protected AlertDialog.Builder mAlertDialogBuilder;
    protected ProgressDialog mProgressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;

   protected AntiShake shake = new AntiShake();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mToolbar=(Toolbar) findViewById(R.id.toolbar);
        mTvTitle=(TextView) findViewById(R.id.tvTitle);
        mRightTitle=(TextView) findViewById(R.id.tvRightText);
        mContext = this;
        ButterKnife.bind(this);
        initSwipeRefreshLayout();
        initToolbar();
        initialize();

        //设置强制竖屛
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
     * 初始化toolbar
     */
    private void initToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNavigationClick();
                }
            });
        }
    }

    /**
     * navigation 点击
     */
    protected void onNavigationClick() {
        finish();
    }

    /**
     * 设置toolbar标题
     *
     * @param title      标题
     * @param isShowHome 是否显示home键
     */
    @SuppressWarnings("unused")
    @Override
    public void setToolbarTitle(@NonNull String title, boolean isShowHome) {
        if (mToolbar != null)
            setTitle("");

        if (mTvTitle != null)
            mTvTitle.setText(title);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(isShowHome);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(isShowHome);
        }
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
        if (mToolbar != null)
            setTitle("");

        if (mTvTitle != null)
            mTvTitle.setText(title);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(isShowHome);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(isShowHome);
        }
    }

    /**
     * 显示信息提示条
     *
     * @param tipsResId 提示信息字符串的资源id
     */
    @SuppressWarnings("unused")
    @Override
    public void showTips(@StringRes int tipsResId) {
       // Snackbar.make(getWindow().getDecorView(), tipsResId, Snackbar.LENGTH_SHORT).show();
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
        //Snackbar.make(getWindow().getDecorView(), tips, Snackbar.LENGTH_SHORT).show();
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



    private void initSwipeRefreshLayout(){
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
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
