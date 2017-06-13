package com.hsh.baselib.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

import com.hsh.baselib.R;

/**
 * 作者：Carr on 2016/11/2 13:40
 * 邮箱：1120199941@qq.com
 */
public class ProgressDialog extends Dialog {

    public ProgressDialog(Context context) {
        this(context, R.style.ProgressDialog);
    }

    public ProgressDialog(Context context, int theme) {
        super(context, theme);
        this.setContentView(R.layout.progress_wheel);
        // 设置居中
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        // 设置弹出对话框背景变暗
        this.getWindow().getAttributes().dimAmount = 0.3f;
        // 设置点击外边布局不可取消
        this.setCanceledOnTouchOutside(false);
    }

}
