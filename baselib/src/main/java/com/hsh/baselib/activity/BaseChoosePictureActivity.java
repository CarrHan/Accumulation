package com.hsh.baselib.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import com.hsh.baselib.dialog.UploadImgDialog;
import com.hsh.baselib.model.ImageItem;
import com.hsh.baselib.utils.AppUtil;
import com.hsh.baselib.utils.BitMapUtil;
import com.hsh.baselib.utils.LogUtil;
import com.hsh.baselib.widget.mutiPicturePicker.ImageTemp;
import com.hsh.baselib.widget.mutiPicturePicker.IntentConstants;

import java.io.File;
import java.util.List;

public abstract class BaseChoosePictureActivity extends BaseNoPresenterActivity {


    String mPicName="";
    MyBroadCast myBroadCast;

    class MyBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtil.e("接收到图片广播");
            if (intent.hasExtra("update")) {
                updateAdapter();
                return;
            }
            List<ImageItem> incomingDataList = (List<ImageItem>) intent.getSerializableExtra(IntentConstants.EXTRA_IMAGE_LIST);
            if (incomingDataList != null) {
                ImageTemp.imageItems.addAll(incomingDataList);
                for (int i = 0; i < incomingDataList.size(); i++) {
                    mPicName = AppUtil.getPicCachePath(mContext)+ File.separator
                            + System.currentTimeMillis()
                            + AppUtil.USER_IMG;
                    ImageItem imageItem = incomingDataList.get(i);
                    Bitmap bitmap = BitMapUtil.loadBitmap(mContext, imageItem.sourcePath);
                    imageItem.filePath=mPicName;
                    BitMapUtil.saveBitmap(mPicName, bitmap);
                    updateAdapter();
                }
            }
        }
    }

    @Override
    protected void initialize() {
        IntentFilter intentFilter=new IntentFilter(ImageChooseActivity.ACTION_IMG_UPDATE);
        myBroadCast=new MyBroadCast();
        registerReceiver(myBroadCast, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(ImageTemp.imageItems !=null){
            ImageTemp.imageItems.clear();
        }
        if(myBroadCast!=null){
            unregisterReceiver(myBroadCast);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case UploadImgDialog.REQUEST_CODE_TAKE_PHOTO: // 拍照
                    String mPicName=System.currentTimeMillis()+".jpg";
                    Bitmap takePictureBitmap = BitMapUtil.loadBitmap(mContext,mPicName);
                    BitMapUtil.saveBitmap(mPicName, takePictureBitmap);
                    //uploadImg();
                    ImageItem imageItem=new ImageItem();
                    imageItem.sourcePath=mPicName;
                    setPic(imageItem);
                    break;
//                case REQUEST_CODE_GALLERY_PHOTO: // 图库选择
//                    if (data != null) {
//                        Uri originalUri = data.getData(); // 获得图片的uri
//                        String imgPath = BitMapUtil.getRealPathFromURI(mContext, originalUri);
//
//                        Bitmap bitmap = BitMapUtil.loadBitmap(mContext, imgPath);
//                        BitMapUtil.saveBitmap(mPicName, bitmap);
//                        //setPic(bitmap);
//                        uploadImg();
//                    }
//                    break;
            }
        }
    }

    private void setPic(ImageItem imageItem) {
        ImageTemp.imageItems.add(imageItem);
        updateAdapter();
    }

    protected abstract void updateAdapter();

}
