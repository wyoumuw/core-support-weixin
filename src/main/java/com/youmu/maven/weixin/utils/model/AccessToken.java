package com.youmu.maven.weixin.utils.model;

/**
 * Created by dehua.lai on 2017/5/26.
 */
public class AccessToken extends ExpireableToken{
    private  String token;
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
}
