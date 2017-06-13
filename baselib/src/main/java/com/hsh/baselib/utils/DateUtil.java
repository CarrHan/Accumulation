package com.hsh.baselib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 作者：Carr on 2016/12/12 15:44
 * 邮箱：1120199941@qq.com
 */


public class DateUtil {


    public static String YYYY_MM_DD="yyyy-MM-dd";
    public static String YYYY_MM_DD_HH_MM_SS="yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前日期
     * @param format
     * @return
     */
    public static String getCurrentDate(String format){
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        date=calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);
        return dateString;
    }


    /**
     * 获取下一年当前日期
     * @param format
     * @return
     */
    public static String getNextYearDate(String format){
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        date=calendar.getTime(); //这个时间就是日期往后推一天的结果
        date.setYear(date.getYear()+1);
        date.setDate(date.getDate()-1);
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String getNextYearData(Long current,String format){
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(current);
        calendar.setTime(date);
        date = calendar.getTime();
        date.setYear(date.getYear()+1);
        date.setDate(date.getDate()-1);
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);
        return dateString;
    }


    /**
     * 字符串转换成日期
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 日期转换成字符串
     * @param date
     * @return str
     */
    public static String DateToStr(Date date,String f) {

        SimpleDateFormat format = new SimpleDateFormat(f);
        String str = format.format(date);
        return str;
    }


}
