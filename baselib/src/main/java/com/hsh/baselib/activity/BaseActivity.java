package com.hsh.baselib.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.hsh.baselib.R;
import com.hsh.baselib.constanst.SPConstanst;
import com.hsh.baselib.model.Result;
import com.hsh.baselib.net.OnResultListener;
import com.hsh.baselib.presenter.BaseActivityPresenter;
import com.hsh.baselib.utils.AppUtil;
import com.hsh.baselib.utils.HttpUtil;
import com.hsh.baselib.utils.SPUtils;
import com.hsh.baselib.view.IBaseView;
import com.hsh.baselib.widget.ProgressDialog;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;

/**
 * activity基类
 */
public abstract class BaseActivity<P extends BaseActivityPresenter> extends AppCompatActivity implements IBaseView {
    protected Context mContext;
    protected P mPresenter;

    protected Toolbar mToolbar;

    protected TextView mTvTitle;

    protected SwipeRefreshLayout swipeRefreshLayout;

    protected AlertDialog.Builder mAlertDialogBuilder;
    protected ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mToolbar=(Toolbar) findViewById(R.id.toolbar);
        mTvTitle=(TextView) findViewById(R.id.tvTitle);

        mContext = this;
        ButterKnife.bind(this);

        if (mPresenter == null)
            initPresenter();
        checkPresenterIsNull();
        initToolbar();
        initialize();
        mPresenter.onCreate(savedInstanceState);

        //设置强制竖屛
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mPresenter != null)
            mPresenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mPresenter != null)
            mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mPresenter != null)
            mPresenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mPresenter != null)
            mPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
        if (mPresenter != null)
            mPresenter.onDestroy();
    }

    /**
     * 初始化presenter
     */
    protected abstract void initPresenter();

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
     * 检查mPresenter是否为空
     */
    protected void checkPresenterIsNull() {
        if (mPresenter == null) {
            throw new IllegalStateException("please init mPresenter at initPresenter() method...");
        }
    }

    /**
     * 初始化toolbar
     */
    protected void initToolbar() {
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

        if (mTvTitle != null) {
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(title);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(isShowHome);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(isShowHome);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
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

        if (mTvTitle != null) {
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(title);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(isShowHome);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(isShowHome);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        Toast.makeText(mContext, tipsResId, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示信息提示条
     *
     * @param tips 提示信息字符串
     */
    @SuppressWarnings("unused")
    @Override
    public void showTips(@NonNull final String tips) {
        //Toast.makeText(mContext, tips, Toast.LENGTH_LONG).show();
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
        }catch (Exception E){

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

    public Toolbar getToolbar() {
        return mToolbar;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mPresenter!=null)
        mPresenter.onActivityResult(requestCode,resultCode,data);
    }


}
