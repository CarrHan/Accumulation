package com.hsh.baselib.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import com.hsh.baselib.utils.AppUtil;
import com.hsh.baselib.utils.ImageUtil;

import java.io.File;

public abstract class BaseCropPictureActivity extends BaseNoPresenterActivity {

    protected static final int IMAGE_REQUEST_CODE = 077;
    protected static final int CAMERA_REQUEST_CODE = 188;
    protected static final int RESULT_REQUEST_CODE = 2;
    protected static final String IMAGE_FILE_NAME = "img.jpg";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    if (AppUtil.hasSdcard()) {
                        startPhotoZoom(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
                    } else {
                        showTips("抱歉,手机找不到储存卡.");
                    }
                    break;
                case RESULT_REQUEST_CODE:
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;
            }
        }
    }


    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //intent.setData(uri);
        //mUri=uri;
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        //intent.putExtra("aspectX", 1);
        //intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        //intent.putExtra("outputX", 320);
        //intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }


    /**
     * 保存裁剪之后的图片数据
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            callBackBitmap(photo);
            try {
                Uri uri = ImageUtil.saveBitmap(this, photo);
                if (uri != null) {
                    //callBackUri(uri);
                } else {
                    //showTips("抱歉，发生异常,请联系平台技术员进行处理：uri为空");
                }

            } catch (Exception e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
                showTips("抱歉，发生异常,请联系平台技术员进行处理：catch");
            }
        } else {
            showTips("发生异常-extras为空");
        }
    }

    /**
     * 子类处理bitmap
     * @param bitmap
     */
    protected abstract void callBackBitmap(Bitmap bitmap);

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

//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("image/*");
//        startActivityForResult(intent,IMAGE_REQUEST_CODE);

    }


}
