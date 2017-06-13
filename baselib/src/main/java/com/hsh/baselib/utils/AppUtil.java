package com.hsh.baselib.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;

import com.hsh.baselib.BaseLib;
import com.hsh.baselib.config.Config;

import java.io.File;
import java.util.List;

/**
* 作者：Carr on 2016/11/3 13:37
        * 邮箱：1120199941@qq.com
*/
public class AppUtil {

    public static final String USER_IMG = ".jpg";

    /**
     * 获取app版本名
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {

        PackageInfo pi;
        try {
            pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "3.0.0";
    }

    /**
     * 获取app版本代码
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        PackageInfo pi;
        try {
            pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }


    /**
     * get app imei
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = telephonyManager.getDeviceId();
            if(StringUtil.isEmpty(imei)){
                return "123456789M";
            }
            return imei;
        }catch (Exception e)
        {
            return "1234567890J";
        }
    }


    /**
     * 判断设备是否存在储存卡
     *
     * @return
     * @author carr
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 获取glide图片缓存路径
     * @param context
     * @return
     */
    public static String getPicCachePath(Context context){
        File picFile = FileUtil.getDiskCacheDir(context, Config.picCacheDir);
        if (!picFile.exists()) {
            picFile.mkdir();
        }
        String mPicCacheDir = picFile.getAbsolutePath();
        return mPicCacheDir;
    }

    /**
     * 获取文件缓存路径
     * @param context
     * @return
     */
    public static String getFileCachePath(Context context){
        File fileFile = FileUtil.getDiskCacheDir(context, Config.fileCacheDir);
        if (!fileFile.exists()) {
            fileFile.mkdir();
        }
        String mPicCacheDir = fileFile.getAbsolutePath();
        return mPicCacheDir;
    }


    //在进程中去寻找当前APP的信息，判断是否在前台运行
    public static boolean isAppOnForeground() {
        ActivityManager activityManager =(ActivityManager) BaseLib.getContext().getSystemService(
                Context.ACTIVITY_SERVICE);
        String packageName =BaseLib.getContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }


    /**
     * 打开软键盘
     * @param context
     */
    public static void showSoftInput(Activity context){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(context.getWindow().getDecorView(),InputMethodManager.SHOW_FORCED);
    }

    /**
     * 关闭关键盘
     * @param context
     */
    public static void hideSoftInput(Activity context){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0);
    }


}
