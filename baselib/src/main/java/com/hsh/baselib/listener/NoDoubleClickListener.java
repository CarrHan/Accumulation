package com.hsh.baselib.listener;

import android.view.View;

import java.util.Calendar;

/**
 * 防止重复点击事件
 * 作者：Carr on 2017/1/16 20:14
 * 邮箱：1120199941@qq.com
 */


public abstract class NoDoubleClickListener implements View.OnClickListener {

    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }
    public abstract void onNoDoubleClick(View v);
}
