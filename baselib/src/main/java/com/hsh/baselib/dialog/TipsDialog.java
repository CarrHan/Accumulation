package com.hsh.baselib.dialog;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.hsh.baselib.R;
import com.hsh.baselib.utils.StringUtil;

/**
 * 作者：Carr on 2016/11/8 10:25
 * 邮箱：1120199941@qq.com
 */


public class TipsDialog {

    private Context context;
    android.app.AlertDialog ad;

    TextView tvMessage;
    TextView cancelButton;
    TextView okButton;

    public TipsDialog(Context context){
        this.context=context;

        ad = new android.app.AlertDialog.Builder(context, R.style.alert_dialog).create();
        ad.show();
        //关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
        Window window = ad.getWindow();
        window.setContentView(R.layout.dialog_tips);
        tvMessage=(TextView) window.findViewById(R.id.tvMessage);
        cancelButton = (TextView) window.findViewById(R.id.btnCancel);
        okButton = (TextView) window.findViewById(R.id.btnOK);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    /**
     * 设置message
     * @param message
     */
    public void setMessage(String message){
        if(tvMessage!=null)
            tvMessage.setText(message);
    }

    public void hideCancelButton(){
        if(cancelButton!=null)
            cancelButton.setVisibility(View.GONE);
    }

    /**
     * 设置取消按钮事件
     * @param text
     * @param listener
     */
    public void setCancelListener(String text,final View.OnClickListener listener){
        if(text!=null&&!text.equals("")){
            cancelButton.setText(text);
        }
        cancelButton.setOnClickListener(listener);
    }

    /**
     * 设置确认按钮事件
     * @param text
     * @param listener
     */
    public void setOkListener(String text,final View.OnClickListener listener){
        if(text!=null&&!text.equals("")){
            okButton.setText(text);
        }
        okButton.setOnClickListener(listener);
    }


    /**
     * 关闭对话框
     */
    public void dismiss() {
        ad.dismiss();
    }

}
