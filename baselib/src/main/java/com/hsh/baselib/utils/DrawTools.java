package com.hsh.baselib.utils;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;

/**
 * Created by Carr on 2015/10/12.
 */
public class DrawTools {


    /**
     * 获取字符串的宽度
     *
     * @param paint 画笔
     * @param str   字符
     * @return 字符宽度
     */
    public static int getStringWidth(Paint paint, String str) {
        Rect rect = new Rect();

        //返回包围整个字符串的最小的一个Rect区域
        paint.getTextBounds(str, 0, str.length(), rect);
        //strwid = rect.width();
        //strhei = rect.height();
        return rect.width();
    }


    /**
     * 获取字符串的高度
     *
     * @param paint 画笔
     * @param str   字符
     * @return 字符高度
     */
    public static int getStringHeight(Paint paint, String str) {
        Rect rect = new Rect();
        //返回包围整个字符串的最小的一个Rect区域
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.height();
    }


    /**
     * dp转px
     * @param context
     * @param n
     * @return
     */
    public static int dp2px(Context context, float n)
    {
        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        n = (int) (n * metrics.density);
        return (int)n;
    }


}
