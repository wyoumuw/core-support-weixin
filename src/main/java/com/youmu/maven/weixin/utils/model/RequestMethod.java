package com.youmu.maven.weixin.utils.model;

/**
 * Created by dehua.lai on 2017/5/26.
 */
public enum RequestMethod {
    GET("GET"),POST("POST");
    private String method;
    RequestMethod(String method) {
        this.method=method;
    }

    public String getMethod() {
        return method;
    }
}
