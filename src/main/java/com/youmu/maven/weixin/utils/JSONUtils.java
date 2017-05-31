package com.youmu.maven.weixin.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by dehua.lai on 2017/5/31.
 */
public class JSONUtils {

    public static <V> V parse(String json){
        return (V) JSONObject.parseObject(json);
    }

    public static <V> String toJSONString(V obj){
        return JSONObject.toJSONString(obj);
    }
}
