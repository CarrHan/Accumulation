package com.hsh.baselib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 作者：Carr on 2016/11/16 15:11
 * 邮箱：1120199941@qq.com
 */


public class ImageUtil {

    /**
     * 把裁剪后的bitmap保存到本地并返回uri
     *
     * @param bitmap 传入的bitmap
     * @return
     * @throws IOException
     */
    public static Uri saveBitmap(Context context, Bitmap bitmap) throws IOException {
        //File f = new File(MyApp.ROOT_OF_IMAGES +"head"+System.currentTimeMillis()+ ".png");
        File file = context.getFilesDir();
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++)
                context.deleteFile(files[i].getName());
        }

        File f = new File(context.getFilesDir(), "head" + System.currentTimeMillis() + ".png");
        //File f=new File(context.getFilesDir(),"avatar.png");
        f.createNewFile();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //Toast.makeText(getActivity(),"1",Toast.LENGTH_SHORT).show();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(getActivity(),"2",Toast.LENGTH_SHORT).show();
        }
        try {
            fOut.close();
            //Toast.makeText(getActivity(),"3",Toast.LENGTH_SHORT).show();
            return Uri.fromFile(f);
        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(getActivity(),"4",Toast.LENGTH_SHORT).show();
            return null;
        }
    }



}
