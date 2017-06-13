package com.hsh.baselib.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.hsh.baselib.R;

/**
 * Created by Carr on 2015/8/27.
 * 自定义的图片滚动小圆点控件 用法参考布局fragment_product.xml
 */
public class PointView extends View {

    public static int TYPE_CIRCLE = 0;
    public static int TYPE_RECTANGLE = 1;

    public static int MODELE_RIGHT = 3;
    public static int MODEL_CENTER = 4;

    private int COLOR_SELECTED = Color.BLACK; //选中颜色
    private int COLOR = Color.GRAY; //未选中颜色
    private int POINT_SIZE = 3; //圆点半径
    private int POINT_SPACE = 5; //圆点间隔
    private int pointNumber; //圆点数量

    private int type = TYPE_RECTANGLE;
    private int model = MODELE_RIGHT;

    private Paint mPaint; //画笔

    private int screenWidth, screenHeigh; //屏幕宽高

    private int currentPoint;



    public PointView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public PointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs) {
        mPaint = new Paint();

        //获取屏幕宽高
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeigh = dm.heightPixels;

        this.POINT_SIZE=(int)(this.POINT_SIZE*dm.density);
        this.POINT_SPACE=(int)(this.POINT_SPACE*dm.density);

        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.PointView);
        this.type=typedArray.getInteger(R.styleable.PointView_typeShape,TYPE_RECTANGLE);
        this.model=typedArray.getInteger(R.styleable.PointView_alignModel,MODELE_RIGHT);
        this.COLOR_SELECTED=typedArray.getColor(R.styleable.PointView_colorSelected, Color.BLACK);
        this.COLOR=typedArray.getColor(R.styleable.PointView_colorUnSelect, Color.GRAY);
        this.POINT_SIZE=(int)typedArray.getDimension(R.styleable.PointView_pointSize, POINT_SIZE);
        this.POINT_SPACE=(int)typedArray.getDimension(R.styleable.PointView_pointSpace, POINT_SPACE);

        typedArray.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));  //抗锯齿
        //
        int startX;
        if (model == MODELE_RIGHT)
            startX = screenWidth - 2 * POINT_SIZE * pointNumber - POINT_SPACE * (pointNumber - 1) - 4 * POINT_SIZE;
        else
            startX = (screenWidth - 2 * POINT_SIZE * pointNumber - POINT_SPACE * (pointNumber - 1)) / 2;
        for (int i = 0; i < pointNumber; i++) {
            if (i == currentPoint) {
                mPaint.setColor(COLOR_SELECTED);
            } else {
                mPaint.setColor(COLOR);
            }
            if (type == TYPE_CIRCLE)
                canvas.drawCircle(startX + (i * (2 * POINT_SIZE + POINT_SPACE)), POINT_SIZE, POINT_SIZE, mPaint);
            if (type == TYPE_RECTANGLE)
                canvas.drawRect((float) (startX + (i * (2 * POINT_SIZE + POINT_SPACE))), 0, (float) (startX + (i * (2 * POINT_SIZE + POINT_SPACE)) + 2 * POINT_SIZE), (float) POINT_SIZE, mPaint);
        }
    }

    public int getPointNumber() {
        return pointNumber;
    }

    public void setPointNumber(int pointNumber) {
        this.pointNumber = pointNumber;
        invalidate();
    }

    public int getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(int currentPoint) {
        this.currentPoint = currentPoint;
        invalidate();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }
}
