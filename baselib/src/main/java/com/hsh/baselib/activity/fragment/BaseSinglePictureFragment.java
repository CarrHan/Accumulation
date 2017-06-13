package com.hsh.baselib.activity.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.hsh.baselib.dialog.UploadImgDialog;
import com.hsh.baselib.utils.AppUtil;
import java.io.File;
import static android.app.Activity.RESULT_OK;
/**
 * 作者：Carr on 2016/11/16 16:33
 * 邮箱：1120199941@qq.com
 */


public abstract class BaseSinglePictureFragment extends BaseNoPresenterFragment{

    protected static final int IMAGE_REQUEST_CODE = 077;
    protected static final int CAMERA_REQUEST_CODE = 188;
    protected static final String IMAGE_FILE_NAME = "img.jpg";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    Uri uri = data.getData();
                    if (uri != null) {
                        callBackUri(uri);
                    } else {
                        showTips("抱歉，发生异常,请联系平台技术员进行处理：uri为空");
                    }
                    break;
                case CAMERA_REQUEST_CODE:
                    if (AppUtil.hasSdcard()) {
                        Uri uri1=Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME));
                        callBackUri(uri1);
                    } else {
                        showTips("抱歉,手机找不到储存卡.");
                    }
                    break;
            }
        }
    }


    /**
     * 子类处理uri
     * @param uri
     */
    protected abstract void callBackUri(Uri uri);

    /**
     * 拍照
     */
    protected void camera(){
        Intent intentFromCapture = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (AppUtil.hasSdcard()) {

            intentFromCapture.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(),
                            IMAGE_FILE_NAME)));
        }else{
            showTips("抱歉您的手机找不到储存卡!");
        }

        startActivityForResult(intentFromCapture,
                CAMERA_REQUEST_CODE);
    }

    /**
     * 选择相册
     */
    protected void album(){

        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,IMAGE_REQUEST_CODE);
    }


    protected void selectDialog(){
        final UploadImgDialog dialog=new UploadImgDialog(mContext);
        dialog.setPaiZhaoButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera();
                dialog.dismiss();
            }
        });
        dialog.setXiangCheButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                album();
                dialog.dismiss();
            }
        });
    }


}
