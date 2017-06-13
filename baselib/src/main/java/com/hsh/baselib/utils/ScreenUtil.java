package com.hsh.baselib.utils;

import com.hsh.baselib.BaseLib;
/**
 * 屏幕工具类
 * Created by  carr on 16/04/08.
 */
public class ScreenUtil {
    /**
     * 屏幕相关
     */
    public static final float DENSITY = BaseLib.getContext().getResources().getDisplayMetrics().density;
    public static final int WIDTHPX = BaseLib.getContext().getResources().getDisplayMetrics().widthPixels;
    public static final int HEIGHTPX = BaseLib.getContext().getResources().getDisplayMetrics().heightPixels;
}
