package com.bigdream.dream.utils;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.util.StringUtil;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @description:
 * @author: Wu Yuwei
 * @create: 2020-07-31 11:00
 **/
@Slf4j
public class StringUtils {

    /**
     * @Description: 将list集中中相同字段的值拼接成字符串，可作为sql中 in 的条件
     * @Param:
     * @return:
     * @Author: Wu Yuwei
     * @Date: 2020/6/10
     */
    public static String joinStr(List<?> T, String name){
        StringBuilder builder = new StringBuilder();
        String substring= null;
        try {
            substring = "";
            for (Object object : T) {
                Field[] fields = object.getClass().getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    if (fields[i].getName().equals(name)){
                        fields[i].setAccessible(true);
                        builder.append("'"+fields[i].get(object)+"'"+",");
                    }
                }
            }
            if (StringUtil.isNotEmpty(builder.toString())){
                substring = builder.toString().substring(0, builder.toString().length() - 1);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return substring;
    }

    /**
     * 将敏感信息转换成***
     * @param value
     * @return
     */
    public static String setStar(String value){
        String[] temp =null;
        if(value.contains("@")){
            temp  = value.split("@");
            value = temp[0];
        }
        int l = value.length()/3;
        String ss ="";
        for(int i=0;i<l;i++){
            ss+="*";
        }
        value = value.substring(0,l)+ss+value.substring(l*2,value.length());
        if(null != temp && temp.length >=2){
            value +="@"+temp[1];
        }
        return value;

    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(Object str) {
        if ((str == null) || ("".equals(String.valueOf(str).trim())) || ("null".equalsIgnoreCase(String.valueOf(str).trim()))) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(Object str) {
        return !isEmpty(str);
    }
}
