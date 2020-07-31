package com.bigdream.dream.utils;

import tk.mybatis.mapper.util.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @description:
 * @author: Wu Yuwei
 * @create: 2020-07-31 10:47
 **/
public class DateUtil {

    /**
     * @Description: 将String类型的时间 转换为需要的格式format
     * @Author: Wu Yuwei
     * @Date: 2020/6/10 14:14
     */
    public static String formatDateStr(String date,String format) throws ParseException {
        String dateStr = null;
        try {
            if (StringUtil.isEmpty(date) || StringUtil.isEmpty(format)){
                return null;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            dateStr = simpleDateFormat.format(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * @Description: 将Date类型的时间 转换为需要的格式format
     * @Author: Wu Yuwei
     * @Date: 2020/6/10 14:14
     */
    public static String formatDate(Date date, String format){
        String dateStr=null;
        try {
            if (null==date || StringUtil.isEmpty(format)){
                return null;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            dateStr = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }
    /**
     * @Description: 获取当前时间的前 i 小时的时间
     * @Author: Wu Yuwei
     * @Date: 2020/7/3 14:10
     */
    public static String getBeforeTimeByArgs(int i){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //（1）获取当前时间
        LocalDateTime date = LocalDateTime.now();
        //（2）获取当前时间的前i小时时间
        LocalDateTime localDateTime = date.minusHours(i);
        String time=dateTimeFormatter.format(localDateTime);
        return time;
    }

    /**
     * @Description: 获取 n 分钟前的时间   正数返回当前时间之后  负数返回当前时间之前
     * @Author: Wu Yuwei
     * @Date: 2020/7/3 18:41
     */
    public static String getNowBeforeTime(int n){
        Calendar nowBefore = Calendar.getInstance();
        nowBefore.add(Calendar.MINUTE, n);
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(nowBefore.getTimeInMillis());
        return time;
    }

    /**
     * @Description: 根据条件获取指定日期的时间   正数当前时间之后  负数当前时间之前
     * @Author: Wu Yuwei
     * @Date: 2020/7/3 18:59
     */
    public static String getDateBySelect(String type,int n){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        String date="";
        if (type.equals("day")){
            //过去、未来七天的时间
            c.setTime(new Date());
            c.add(Calendar.DATE, n);
            Date d = c.getTime();
            date = format.format(d);
        }else if (type.equals("month")){
            //过去、未来n月的时间
            c.setTime(new Date());
            c.add(Calendar.MONTH, n);
            Date m = c.getTime();
            date = format.format(m);
        }else if (type.equals("year")){
            //过去、未来n年的时间
            c.setTime(new Date());
            c.add(Calendar.YEAR, n);
            Date y = c.getTime();
            date = format.format(y);
        }
        return date;
    }

}
