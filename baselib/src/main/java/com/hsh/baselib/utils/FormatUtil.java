package com.hsh.baselib.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 格式化工具类
 * 作者：Carr on 2016/11/29 09:49
 * 邮箱：1120199941@qq.com
 */


public class FormatUtil {


    public static String NumberFormat(float f,int m){
        return String.format("%."+m+"f",f);
    }

    public static float NumberFormatFloat(float f,int m){
        String strfloat = NumberFormat(f,m);
        return Float.parseFloat(strfloat);
    }


    /**
     * 时间戳格式化为时间
     * @param time
     * @return
     */
    public static String timeTamp2String(long time){
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long aLong=new Long(time);
        String d = format.format(aLong);
        return  d;
    }

    public static String timeTamp2String(long time,String f){
        try {
            SimpleDateFormat format =  new SimpleDateFormat(f);
            Long aLong=new Long(time);
            String d = format.format(aLong);
            return  d;
        }
        catch (Exception e){
            return "";
        }
    }

    /**
     * 时间字符串转时间戳
     * @param time
     * @return
     */
    public static long string2TimeTamp(String time){
        try {
            SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(time);
            return date.getTime();
        }catch (Exception e){
            return 0;
        }
    }


    /**
     * float转为保留n位小数 返回string
     * @param data 浮点数
     * @param format "0.00" "0.000" "0.0000"
     * @return
     */
    public static String float2String(Float data,String format){
        if(data==null)return "0";
        try {
            DecimalFormat decimalFormat=new DecimalFormat(format);//构造方法的字符格式这里如果小数不足2位,会以0补足.
            String p=decimalFormat.format(data);//format 返回的是字符串
            return p;
        }catch (Exception e){
            return data+"";
        }
    }

    /**
     * 把手机号码中间4位隐藏显示
     * @param mobile
     * @return
     */
    public static String hideMobile(String mobile){
        if(!StringUtil.isEmpty(mobile)){
            if(mobile.length()<11){
                return mobile;
            }
            return mobile.substring(0,4)+"****"+mobile.substring(mobile.length()-3,mobile.length());
        }
        return "";
    }



}
