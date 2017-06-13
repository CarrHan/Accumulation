package com.hsh.baselib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;


/**
 * Created by Carr on 2015/8/10.
 */
public class CommonWebView extends WebView {

    public Boolean isError = false;  //判断当前是否为错误页
    public Boolean isClearHistory = false;  //判断是否需要清空浏览历史

    public CommonWebView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public CommonWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommonWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }
}
