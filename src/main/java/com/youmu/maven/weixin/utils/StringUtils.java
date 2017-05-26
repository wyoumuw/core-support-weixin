package com.youmu.maven.weixin.utils;

import java.util.Random;

/**
 * Created by dehua.lai on 2017/5/26.
 */
public abstract class StringUtils {
    //char for rand
    private static final char[] base = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPQqRrSsTtUuVvWwXYxyZz0123456789".toCharArray();

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
    public static String generateNonceStr(){
        byte[] bytes=new byte[16];
        new Random().nextBytes(bytes);
        int len=base.length;
        char[] out=new char[16];
        for (int i=0;i<16;i++){
            out[i]=base[(bytes[i]>>>1)%len];
        }
        return String.valueOf(out);
    }
    public static boolean isEmpty(String str){
        return null==str||0==str.length();
    }
}
