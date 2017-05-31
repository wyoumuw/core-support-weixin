package com.youmu.maven.weixin.model;

/**
 * Created by youmu on 2017/5/26.
 */
public class AccessToken extends ExpireableToken {
    private  String token;

    @SuppressWarnings("unsafe")
    public AccessToken(){
    }
    /**
     *
     * @param token
     * @param expires ms
     */
    public AccessToken(String token, long expires) {
        super(expires);
        this.token = token;
    }

    /**
     *
     * @param token
     * @param expires ms
     * @param before
     */
    public AccessToken(String token, long expires, long before) {
        super(expires,before);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @SuppressWarnings("unsafe")
    public void setToken(String token) {
        this.token = token;
    }
}
