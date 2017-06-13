package com.hsh.baselib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.hsh.baselib.R;
import com.hsh.baselib.utils.DrawTools;

/**
 * Created by Carr on 2016/8/5.
 * 这个控件的功能是：设置文字可以自动缩放大小填充View的大小 布局文件item_coupon有使用,请参考使用
 */
public class AutoScaleTextView extends View {


    final Context context;
    private int TEXT_SIZE_DEFAULT=5;  //默认字体大小
    private int TEXT_SIZE_DEFAULT_SCALE=TEXT_SIZE_DEFAULT; //默认字体通过适配缩放后的大小
    private int TEXT_COLOR= Color.BLACK; //字体颜色
    private int BACKGROUND_COLOR= Color.TRANSPARENT; //背景色
    private String string="Text"; //文字

    DisplayMetrics metrics;

    public AutoScaleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);


        this.context=context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoScaleTextView);
        this.TEXT_COLOR = a.getColor(R.styleable.AutoScaleTextView_textColor,TEXT_COLOR);
        this.BACKGROUND_COLOR=a.getColor(R.styleable.AutoScaleTextView_backgroundColor,BACKGROUND_COLOR);
        this.string=a.getString(R.styleable.AutoScaleTextView_text);
        a.recycle();

        if(string==null){string="test";}
        metrics=getResources().getDisplayMetrics();
        TEXT_SIZE_DEFAULT_SCALE=getScaleTextSize(TEXT_SIZE_DEFAULT);

        setBackgroundColor(BACKGROUND_COLOR);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    /**
     * 设置文字
     * @param text
     */
    public void setText(String text)
    {
        this.string=text;
        invalidate();
    }


    /**
     * 设置字体颜色
     * @param color
     */
    public void setTextColor(int color)
    {
        this.TEXT_COLOR=color;
        invalidate();
    }

    /**
     * 设置背景颜色
     * @param color
     */
    public void setBackgroundColor(int color)
    {
        this.BACKGROUND_COLOR=color;
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);


        //初始化画笔
        Paint paint=new Paint();
        paint.setColor(TEXT_COLOR);
        paint.setTextSize(TEXT_SIZE_DEFAULT_SCALE);
        paint.setAntiAlias(true);//去锯齿


        //获取文字和控件的宽度
        int textWidth= DrawTools.getStringWidth(paint,string);
        int viewWidth=getMeasuredWidth();


        /**
         * 循环计算当字体宽度大于view宽度时推出循环
         */
        while (textWidth<=viewWidth-DrawTools.dp2px(context,10))
        {
            TEXT_SIZE_DEFAULT++;
            TEXT_SIZE_DEFAULT_SCALE=getScaleTextSize(TEXT_SIZE_DEFAULT);
            paint.setTextSize(TEXT_SIZE_DEFAULT_SCALE);

            textWidth=DrawTools.getStringWidth(paint,string);

        }

        TEXT_SIZE_DEFAULT_SCALE=getScaleTextSize(TEXT_SIZE_DEFAULT);
        paint.setTextSize(TEXT_SIZE_DEFAULT_SCALE);
        canvas.drawText(string,(getMeasuredWidth()-DrawTools.getStringWidth(paint,string))/2,DrawTools.getStringHeight(paint,string),paint);

        //调整高度
        ViewGroup.LayoutParams layoutParams=getLayoutParams();
        layoutParams.height=DrawTools.getStringHeight(paint,string)+DrawTools.dp2px(context,2);
        layoutParams.width=getMeasuredWidth();
        setLayoutParams(layoutParams);

    }


    /**
     * 文字大小转换
     * @param size
     * @return
     */
    private int getScaleTextSize(int size)
    {
        return (int)(size*metrics.scaledDensity);
    }

}
