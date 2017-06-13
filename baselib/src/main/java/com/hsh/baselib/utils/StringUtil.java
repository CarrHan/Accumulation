package com.hsh.baselib.utils;

import android.text.Html;
import android.text.Spanned;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by win10 on 2016/6/19.
 */
public class StringUtil {
    /**
     * 字符串拼接  线程安全
     * @param
     * @return
     */
    public static String buffer(String... array){
        StringBuffer s = new StringBuffer();
        for (String str:array) {
            s.append(str);
        }
        return s.toString();
    }
    /**
     * 字符串拼接 线程不安全 效率高
     * @param
     * @return
     */
    public static String builder(String... array){
        StringBuilder s = new StringBuilder();
        for (String str: array) {
            s.append(str);
        }
        return s.toString();
    }


    /**
     * md5加密
     *
     * @param key 加密字符串
     * @return 加密字符串
     */
    public static String encodeByMD5(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    /**
     * 转换字节数组为十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 验证手机号码格式
     * @param mobile
     * @return
     */
    public static Boolean isMobile(String mobile){
        String str="/^[1][34578][0-9]{9}$/";
        Pattern p=Pattern.compile(str);
        Matcher m=p.matcher(mobile);
        return m.matches();
    }


    /**
     * 检测字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if(str==null||str.equals(""))
            return true;
            return false;
    }

    /**
     * left
     * @param first
     * @param inner
     * @return
     */
    public static Spanned sysColorHtmlString(String first, String inner){
        return Html.fromHtml("<font color=\"#158cff\"><strong>" + first + "</strong></font>"+inner);
    }

    public static Spanned sysColorHtmlStringNoStrong(String first, String inner){
        return Html.fromHtml("<font color=\"#158cff\">" + first + "</font>"+inner);
    }

    /**
     * right
     * @param first
     * @param second
     * @return
     */
    public static Spanned sysColorHtmlStringRight(String first, String second){
        return Html.fromHtml(first+" "+"<font color=\"#158cff\">" + second + "</font>");
    }

    /**
     * center
     * @param first
     * @param center
     * @param end
     * @return
     */
    public static Spanned sysColorHtmlStringCenter(String first, String center,String end){
        return Html.fromHtml(first+" "+"<font color=\"#158cff\"><strong>" + center + "</strong></font>"+" "+end);
    }



    /**
     * 使用java正则表达式去掉多余的.与0
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

}
