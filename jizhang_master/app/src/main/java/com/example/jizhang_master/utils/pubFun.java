package com.example.jizhang_master.utils;

import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * pubFun.java
 */
public class pubFun {
    public static boolean isEmpty( String input )
    {
        if ( input == null || "".equals( input ) )
            return true;

        for ( int i = 0; i < input.length(); i++ )
        {
            char c = input.charAt( i );
            if ( c != ' ' && c != '\t' && c != '\r' && c != '\n' )
            {
                return false;
            }
        }
        return true;
    }

    /**
     *
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        //Acceptable phone formats include:
        String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{5})$";
        String expression2 = "^\\(?(\\d{3})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);

        Pattern pattern2 = Pattern.compile(expression2);
        Matcher matcher2 = pattern2.matcher(inputStr);
        if(matcher.matches() || matcher2.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * 获取当前年月日
     * 输出的值为System.out: 月：8 ##年：2018 ##日：20 ##时：11（其中输出的月份的值是实际月份-1）
     */
    public static Integer getTime(String strFlag){
        Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料
        t.setToNow(); // 取得系统时间
        int reValue = 0;
        if(strFlag == "Y"){
            reValue = t.year;
        }else if(strFlag == "M"){
            reValue = t.month;
        }else if(strFlag == "D"){
            reValue = t.monthDay;
        }else if(strFlag == "H"){
            reValue = t.hour;
        }
        return reValue;
    }

    /**
     *
     * date
     */
    public static String format(Date date){
        String str = "";
        SimpleDateFormat ymd = null;
        ymd = new SimpleDateFormat("yyyy-MM-dd");
        str = ymd.format(date);
        return str;
    }
}
