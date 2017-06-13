package com.hsh.baselib.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.hsh.baselib.R;
import com.hsh.baselib.webviewclient.CommonWebChromeClient;
import com.hsh.baselib.webviewclient.CommonWebViewClient;
import com.hsh.baselib.widget.CommonWebView;
import butterknife.ButterKnife;

public class CommonWebActivity extends BaseNoPresenterActivity {


    public static String URLKEY = "url";
    public static String TITLEKEY = "title";

    SwipeRefreshLayout mSwipeLayout;

   CommonWebView mWebView;
    private WebSettings webSettings;
    private String titleString = "null";
    private RelativeLayout mErrorLayout;
    private ImageView mLoadAgain;
    private LinearLayout.LayoutParams lp;
    private String url = "";


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_common_web;
    }

    @Override
    protected void initialize() {
        mSwipeLayout=(SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        ButterKnife.bind(this);
        titleString = getIntent().getStringExtra(TITLEKEY);
        setToolbarTitle(titleString,true);
        initView(this);
        initListener();
        loadurlFromIntent(mWebView, getIntent());

        url = getUrlString(getIntent());

       // mSwipeLayout.setEnabled(false);

    }


    /**
     * 初始化webview等控件
     *
     * @param context
     */
    private void initView(Context context) {
//        mWebView = new CommonWebView(context);
//        lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp.gravity = Gravity.CENTER;
//        mSwipeLayout.addView(mWebView, lp);
        mWebView=(CommonWebView) findViewById(R.id.webView);

        webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        //webSettings.setUserAgentString(Tools.getUserAgent(this));
        mWebView.requestFocus(View.FOCUS_DOWN);


        mWebView.setBackgroundColor(0);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setBlockNetworkImage(false);
        mWebView.getSettings().setAllowFileAccess(true); //设置可访问文件
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);


        mErrorLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.webview_error, null);
        mLoadAgain = (ImageView) mErrorLayout.findViewById(R.id.img_internet_error);

        mWebView.setWebViewClient(new CommonWebViewClient(this, mWebView, mErrorLayout));
        mWebView.setWebChromeClient(new CommonWebChromeClient(mSwipeLayout));

        //添加此接口js可以调用java的源码
        // mWebView.addJavascriptInterface(new AppBridgeInterface(view.getContext()),MyInjectedCls.HOSTAPP_NAME);
    }


    /**
     * 初始化点击监听器
     */
    private void initListener() {
        /**
         * 后退上一页
         */
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWebView.canGoBack())
                    mWebView.goBack();
                else {
                    CommonWebActivity.this.finish();
                }
            }
        });

        /**
         * 下拉刷新
         */
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                //重新刷新页面
                if (mWebView.isError)
                    mWebView.loadUrl(getUrlString(getIntent()));
                else
                    mWebView.loadUrl(mWebView.getOriginalUrl());
                mWebView.isError = false;
            }
        });

        /**
         * 点击中间图标自动reload
         */
        mLoadAgain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mWebView.isError = false;
                // TODO Auto-generated method stub
                mWebView.loadUrl(getUrlString(getIntent()));
            }
        });

        mSwipeLayout.setColorSchemeResources(R.color.holo_blue_bright,R.color.holo_green_light,R.color.holo_orange_light,R.color.holo_red_light);
        mWebView.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                // TODO Auto-generated method stub
                //返回键
                if (arg2.getAction() == KeyEvent.ACTION_DOWN) {
                    if (arg1 == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                        mWebView.goBack();
                        return true;
                    } else {
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mWebView.removeAllViews();
        mWebView.destroy();
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
    }

    private void loadurlFromIntent(CommonWebView webView, Intent intent) {
        String urlString = intent.getStringExtra(URLKEY);
        if (urlString != null) {
            webView.loadUrl(urlString);
        }
    }

    private String getUrlString(Intent intent) {
        String urlString = "";
        urlString = intent.getStringExtra(URLKEY);
        return urlString;
    }



}
