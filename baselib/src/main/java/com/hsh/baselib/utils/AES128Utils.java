/*
 * Copyright 2016 Guangdong huishenghuo science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://huihuishenghuo.com/
 */
package com.hsh.baselib.utils;
import android.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * <P> </P>
 *
 * @author: 张军平
 * @since: 2015年4月3日下午5:55:00
 * @version: 1.0
 */
public class AES128Utils {
	// 加密
    public static String encrypt(String sSrc, String sKey) throws Exception {
        LogUtil.e("sSrc是:"+sSrc);
        LogUtil.e("sKey是:"+sKey);
        if (sKey == null) {
            System.err.print("Key为空null");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        //base64转字符串
        LogUtil.e("encrypt加密后是;"+encodeBase64URLSafeString(encrypted));
        return encodeBase64URLSafeString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    private static String encodeBase64URLSafeString(byte[] binaryData) {
        return android.util.Base64.encodeToString(binaryData, Base64.NO_WRAP);
    }
 
    // 解密
    public static String decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.err.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
//            if (sKey.length() != 16) {
//                System.out.print("Key长度不是16位");
//                return null;
//            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = Base64.decode(sSrc, Base64.NO_WRAP);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.err.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.err.println(ex.toString());
            return null;
        }
    }
 
    public static void main(String[] args) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */
        String cKey = "1234567890123456";
        // 需要加密的字串
        String cSrc = "18825729094";
        System.out.println(cSrc);
        // 加密
        String enString = AES128Utils.encrypt(cSrc, cKey);
        System.out.println("加密后的字串是：" + enString);
 
        // 解密
        String DeString = AES128Utils.decrypt(enString, cKey);
        System.out.println("解密后的字串是：" + DeString);
        
        System.out.println(toHexString("2ED74935579127AFBABDEA1CCE71A0F7"));
    }
    
    public static String toHexString(String s)
    {
	    String str="";
	    for (int i=0;i<s.length();i++)
	    {
	    int ch = (int)s.charAt(i);
	    String s4 = Integer.toHexString(ch);
	    str = str + s4;
	    }
	    return str;
    } 
}
