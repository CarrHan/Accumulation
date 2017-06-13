package com.hsh.baselib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hsh.baselib.R;

/**
 * Created by Carr on 2015/10/9.
 * 自定义的评分控件
 */
public class RatingBar extends LinearLayout {

    private boolean mClickable;
    private int starCount;
    private float rating;
    private OnRatingChangeListener onRatingChangeListener;
    private float starImageSize;
    private Drawable starEmptyDrawable;
    private Drawable starFillDrawable;
    private Drawable starHalfDrawable;

    public void setOnRatingChangeListener(OnRatingChangeListener onRatingChangeListener) {
        this.onRatingChangeListener = onRatingChangeListener;
    }

    public void setmClickable(boolean clickable) {
        this.mClickable = clickable;
    }

    public void setStarFillDrawable(Drawable starFillDrawable) {
        this.starFillDrawable = starFillDrawable;
    }

    public void setStarEmptyDrawable(Drawable starEmptyDrawable) {
        this.starEmptyDrawable = starEmptyDrawable;
    }

    public void setStarImageSize(float starImageSize) {
        this.starImageSize = starImageSize;
    }

    public RatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOrientation(HORIZONTAL);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        this.starImageSize = mTypedArray.getDimension(R.styleable.RatingBar_starImageSize, 20.0F);
        this.starCount = mTypedArray.getInteger(R.styleable.RatingBar_starCount, 5);
        this.starEmptyDrawable = mTypedArray.getDrawable(R.styleable.RatingBar_starEmpty);
        this.starFillDrawable = mTypedArray.getDrawable(R.styleable.RatingBar_starFill);
        this.starHalfDrawable = mTypedArray.getDrawable(R.styleable.RatingBar_starHalf);
        this.mClickable = mTypedArray.getBoolean(R.styleable.RatingBar_clickable, false);
        this.rating=mTypedArray.getFloat(R.styleable.RatingBar_rating,0f);

        for (int i = 0; i < this.starCount; ++i) {
            ImageView imageView = this.getStarImageView(context, attrs);
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (RatingBar.this.mClickable) {
                        RatingBar.this.setStar(RatingBar.this.indexOfChild(v) + 1);
                        if (RatingBar.this.onRatingChangeListener != null) {
                            RatingBar.this.onRatingChangeListener.onRatingChange(RatingBar.this.indexOfChild(v) + 1);
                        }
                    }
                }
            });
            this.addView(imageView);
        }
        mTypedArray.recycle();

        setStar(rating);
    }

    private ImageView getStarImageView(Context context, AttributeSet attrs) {
        ImageView imageView = new ImageView(context);
        LayoutParams para = new LayoutParams(Math.round(this.starImageSize), Math.round(this.starImageSize));
        imageView.setLayoutParams(para);
        imageView.setPadding(0, 0, 5, 0);
        imageView.setImageDrawable(this.starEmptyDrawable);
        imageView.setMaxWidth(10);
        imageView.setMaxHeight(10);
        return imageView;
    }

    public void setStar(float mark) {
        mark = mark > this.starCount ? this.starCount : mark;
        mark = starCount < 0 ? 0 : mark;

        float xiaoshu = mark - (int) (mark); //分数的小数部分
        int zhengshu = (int) mark; //分数的整数部分

        //整数部分是实心star
        for (int i = 0; i < zhengshu; ++i) {
            ((ImageView) this.getChildAt(i)).setImageDrawable(this.starFillDrawable);
        }

        //小数部分如果大于0，用半实心star
        if (xiaoshu > 0) {
            if (zhengshu < this.starCount)
                ((ImageView) this.getChildAt(zhengshu)).setImageDrawable(this.starHalfDrawable);
        } else {   //如果小数部分小于或等于0，显示全空星星
            if (zhengshu < this.starCount)
                ((ImageView) this.getChildAt(zhengshu)).setImageDrawable(this.starEmptyDrawable);
        }


        //剩余部分用空心star
        for (int j = ++zhengshu; j < this.starCount; j++) {
            ((ImageView) this.getChildAt(j)).setImageDrawable(this.starEmptyDrawable);
        }

    }

    public interface OnRatingChangeListener {
        void onRatingChange(int var1);
    }

}
