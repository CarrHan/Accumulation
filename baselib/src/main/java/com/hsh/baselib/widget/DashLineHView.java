package com.hsh.baselib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.hsh.baselib.R;

/**
 * Created by Carr on 2016/4/6.
 * 水平虚线 设置宽高就可以了 很好用的 高度设置为1dp就可以 可以设置线条大小 颜色 间隔等
 */
public class DashLineHView extends View{


    Context context;

    private int ITEM_LENG=5;
    private int ITEM_SPCE=5;
    private int LINE_COLOR=Color.BLACK;
    private int LINE_SIZE=1;



    public DashLineHView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public DashLineHView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init(Context context,AttributeSet attrs){
        this.context=context;
        setBackgroundColor(Color.TRANSPARENT);  // 必须设置背景，否则onDraw不执行

        //获取屏幕宽高
        DisplayMetrics dm =getResources().getDisplayMetrics();

        ITEM_LENG=(int)(ITEM_LENG*dm.density);
        ITEM_SPCE=(int)(ITEM_SPCE*dm.density);
        LINE_SIZE=(int)(LINE_SIZE*dm.density);

        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.DashLineHView);
        ITEM_LENG=(int)typedArray.getDimension(R.styleable.DashLineHView_item_length,ITEM_LENG);
        ITEM_SPCE=(int)typedArray.getDimension(R.styleable.DashLineHView_item_space,ITEM_SPCE);
        LINE_SIZE=(int)typedArray.getDimension(R.styleable.DashLineHView_line_size,LINE_SIZE);
        LINE_COLOR=typedArray.getColor(R.styleable.DashLineHView_line_color,LINE_COLOR);
        typedArray.recycle();
    }



    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        int startX=0;
        int startY=0;
        Paint paint=new Paint();
        paint.setColor(LINE_COLOR);
        paint.setStrokeWidth(LINE_SIZE);
        for(int i=0;i<getMeasuredWidth();i++)
        {
            canvas.drawLine(startX,startY,startX+ITEM_LENG,startY,paint);
            startX+=(ITEM_LENG+ITEM_SPCE);
        }
    }
}
