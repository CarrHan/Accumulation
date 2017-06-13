package com.hsh.baselib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.hsh.baselib.R;

/**
 * Created by Carr on 2015/11/2.
 * ViewPager顶部的指示器 可以设置指示器的类型 三角形获取线条形
 */
public class Indicator extends LinearLayout {


    public final static int TRIANGLE=0;
    public final static int LINE=1;
    private int model=LINE;

    private Paint mPaint; // 画指示符的paint

    private int mTop; // 指示符的top
    private int mLeft; // 指示符的left
    private int mWidth; // 指示符的width
    private int mHeight = 5; // 指示符的高度，固定了
    private int mColor; // 指示符的颜色
    private int mChildCount; // 子item的个数，用于计算指示符的宽度
    private int mOffSet;//指示符偏移量
    private int mTriangleWidth;

    public Indicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        setBackgroundColor(getResources().getColor(R.color.white));  // 必须设置背景，否则onDraw不执行

        // 获取自定义属性 指示符的颜色
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Indicator, 0, 0);
        mColor = ta.getColor(R.styleable.Indicator_Color,Color.WHITE);
        this.model=ta.getInt(R.styleable.Indicator_model,LINE);
        ta.recycle();

        // 初始化paint
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);  //抗锯齿
    }


    public void setModel(int model) {
        this.model = model;
    }

    public void setTriangleColor(int color)
    {
        mColor = color;
        mPaint.setColor(mColor);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mChildCount = getChildCount();  // 获取子item的个数
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mTop = getMeasuredHeight(); // 测量的高度即指示符的顶部位置
        int width = getMeasuredWidth(); // 获取测量的总宽度
        int height = mTop; // 重新定义一下测量的高度
        mWidth = width / mChildCount; // 指示符的宽度为总宽度/item的个数
        mOffSet = mWidth / 3;  //初始化偏移量
        mTriangleWidth = mWidth / 8;
        mHeight = mTop / 7;
        setMeasuredDimension(width, height);
    }

    /**
     * 指示符滚动
     *
     * @param position 现在的位置
     * @param offset   偏移量 0 ~ 1
     */
    public void scroll(int position, float offset) {
        mLeft = (int) ((position + offset) * mWidth);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 圈出一个矩形
        //  Rect rect = new Rect(mLeft, mTop-mHeight, mLeft +mWidth-mOffSet*2, mTop);
        // canvas.drawRect(rect, mPaint); // 绘制该矩形

        //画三角形
        if(model==TRIANGLE) {
            Path path = new Path();
            path.moveTo(mLeft + (mWidth / 2 - mTriangleWidth / 2), mTop);
            path.lineTo(mLeft + mWidth / 2, mTop - mHeight);
            path.lineTo(mLeft + mWidth / 2 + mTriangleWidth / 2, mTop);
            path.lineTo(mLeft + (mWidth / 2 - mTriangleWidth / 2), mTop);
            canvas.drawPath(path, mPaint);
        }

        if(model==LINE) {
            mPaint.setStrokeWidth(7);
            mPaint.setColor(getResources().getColor(R.color.colorPrimary));
            canvas.drawLine(mLeft, mTop, mLeft + mWidth, mTop, mPaint);
        }

        super.onDraw(canvas);
    }


}
