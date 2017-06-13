package com.hsh.baselib.webviewclient;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.ViewGroup;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hsh.baselib.utils.LogUtil;
import com.hsh.baselib.widget.CommonWebView;

/**
 * Created by Carr on 2015/8/10.
 */
public class CommonWebViewClient extends WebViewClient {

    private CommonWebView mWebView;
    private Context context;
    private LinearLayout.LayoutParams lp;
    private RelativeLayout errorLayout;

    public CommonWebViewClient(Context context, CommonWebView webView, RelativeLayout errorLayout) {
        this.context = context;
        this.mWebView = (CommonWebView) webView;
        this.errorLayout = errorLayout;
        lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        lp.gravity = Gravity.CENTER;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // TODO Auto-generated method stub
        view.loadUrl(url);
        LogUtil.e("webview url--"+url);
        return true;
    }




    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        // TODO Auto-generated method stub

        super.onPageStarted(view, url, favicon);
        //如果当前为无网络错误页面，则切换到错误视图
        if (mWebView.isError) {
            mWebView.removeAllViews();
            view.addView(errorLayout, lp);
            mWebView.clearHistory();
        } else {
            mWebView.removeAllViews();
            if (mWebView.isClearHistory) {
                mWebView.clearHistory();
            }
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        // TODO Auto-generated method stub
        super.onPageFinished(view, url);

        if (mWebView.isError) {
            mWebView.removeAllViews();
            view.addView(errorLayout, lp);
            mWebView.clearHistory();
        } else {
            mWebView.removeAllViews();
            if (mWebView.isClearHistory) {
                mWebView.clearHistory();
                mWebView.isClearHistory = false;
            }
        }

        if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
            mWebView.getSettings().setLoadsImagesAutomatically(true);
        }
    }

//    @Override
//    public void onReceivedHttpAuthRequest(WebView view,
//                                          HttpAuthHandler handler, String host, String realm) {
//        // TODO Auto-generated method stub
//        //handler.proceed("dreamer", "123456");
//    }

    @Override
    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {
        // TODO Auto-generated method stub
        super.onReceivedError(view, errorCode, description, failingUrl);
        view.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        mWebView.isError = true;
        mWebView.isClearHistory = true;
    }

}
