package com.hsh.baselib.utils;

import java.util.Calendar;

/**
 * 作者：Carr on 2017/1/16 20:30
 * 邮箱：1120199941@qq.com
 */


public class OneClickUtil {

    private String methodName;
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    public OneClickUtil(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public boolean check() {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            return false;
        } else {
            return true;
        }
    }
}
