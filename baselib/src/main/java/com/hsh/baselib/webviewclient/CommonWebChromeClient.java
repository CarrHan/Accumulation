package com.hsh.baselib.webviewclient;

import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by Carr on 2015/8/10.
 */
public class CommonWebChromeClient extends WebChromeClient {


    private SwipeRefreshLayout mSwipeLayout;

    public CommonWebChromeClient(SwipeRefreshLayout swipeRefreshLayout) {
        mSwipeLayout = swipeRefreshLayout;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        // TODO Auto-generated method stub
        if (newProgress == 100) {
            //隐藏进度条
            mSwipeLayout.setRefreshing(false);
        } else {
            if (!mSwipeLayout.isRefreshing())
                mSwipeLayout.setRefreshing(true);
        }
        super.onProgressChanged(view, newProgress);
    }


}
