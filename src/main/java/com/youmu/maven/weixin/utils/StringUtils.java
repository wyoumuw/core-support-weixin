package com.youmu.maven.weixin.utils;

/**
 * Created by dehua.lai on 2017/5/26.
 */
public abstract class StringUtils {
    public static String concat(String[] strs){
        if(null==strs){
            return null;
        }
        StringBuilder sb=new StringBuilder();
        for (String str : strs) {
            sb.append(str);
        }
    return sb.toString();
    }

    public static boolean isEmpty(String str){
        return null==str||0==str.length();
    }
}
