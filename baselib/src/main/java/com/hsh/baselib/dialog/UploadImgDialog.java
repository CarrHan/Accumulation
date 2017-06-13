package com.hsh.baselib.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.hsh.baselib.R;
import com.hsh.baselib.activity.ImageBucketChooseActivity;
import com.hsh.baselib.widget.mutiPicturePicker.CustomConstants;
import com.hsh.baselib.widget.mutiPicturePicker.ImageTemp;
import com.hsh.baselib.widget.mutiPicturePicker.IntentConstants;

import java.io.File;

/**
 * Created by Carr on 2015/12/17.
 */
public class UploadImgDialog {

    public static final int REQUEST_CODE_TAKE_PHOTO = 1; // 拍照
    public static final int REQUEST_CODE_GALLERY_PHOTO = 2; // 图库

    Context context;
    android.app.AlertDialog ad;
    RelativeLayout paizhaoLayout,xiangcheLayout,cancelLayout;
    String mPicName=System.currentTimeMillis()+".jpg";


    public UploadImgDialog(final Context context) {
        // TODO Auto-generated constructor stub
        this.context=context;
        ad=new android.app.AlertDialog.Builder(context, R.style.alert_dialog).create();

        ad.show();
        //关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
        Window window = ad.getWindow();
        window.setContentView(R.layout.dialog_upload_img);


        paizhaoLayout=(RelativeLayout)window.findViewById(R.id.uploalimg_paizhao_layout);
        xiangcheLayout=(RelativeLayout)window.findViewById(R.id.uploalimg_xiangche_layout);
        cancelLayout=(RelativeLayout)window.findViewById(R.id.uploalimg_cancel_layout);
        cancelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        paizhaoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri uri = Uri.fromFile(new File(mPicName));
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                ((Activity)context).startActivityForResult(cameraIntent, REQUEST_CODE_TAKE_PHOTO);
               dismiss();
            }
        });
        xiangcheLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,
                        ImageBucketChooseActivity.class);
                intent.putExtra(IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE,
                        getAvailableSize());
                context.startActivity(intent);
               dismiss();
            }
        });

    }

    /**
     * 设置按钮
     * @param listener
     */
    public void setPaiZhaoButton(final View.OnClickListener listener)
    {
        paizhaoLayout.setOnClickListener(listener);

    }  /**
     * 设置按钮
     * @param listener
     */
    public void setXiangCheButton(final View.OnClickListener listener)
    {
        xiangcheLayout.setOnClickListener(listener);
    }
    public void setCancelButton(final View.OnClickListener listener)
    {
        cancelLayout.setOnClickListener(listener);
    }
    /**
     * 关闭对话框
     */
    public void dismiss() {
        ad.dismiss();
    }


    /**
     * 获取图片数量最大值
     *
     * @return
     */
    private int getAvailableSize() {
        int availSize = CustomConstants.MAX_IMAGE_SIZE - ImageTemp.imageItems.size();
        if (availSize >= 0) {
            return availSize;
        }
        return 0;
    }

}
