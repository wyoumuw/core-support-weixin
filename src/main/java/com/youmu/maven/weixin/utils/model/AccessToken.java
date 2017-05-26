package com.youmu.maven.weixin.utils.model;

/**
 * Created by dehua.lai on 2017/5/26.
 */
public class AccessToken {
    //https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
    //{"access_token":"ACCESS_TOKEN","expires_in":7200}
    // 10 min before
    public static final long DEFAULT_BEFORE=60*10*1000;
    private  String token;

    /**
     * time unit is (ms)
     */
    private  long expires;
    private  long createTime;
    private long before;


    /**
     *
     * @param token
     * @param expires ms
     */
    public AccessToken(String token, long expires) {
        this(token,expires,DEFAULT_BEFORE);
    }

    /**
     *
     * @param token
     * @param expires ms
     * @param before
     */
    public AccessToken(String token, long expires, long before) {
        this.token = token;
        this.expires = expires;
        this.createTime=System.currentTimeMillis();
        this.before = before;
    }

    public boolean isExpired(){
        return (this.expires-this.before)<=(System.currentTimeMillis()-this.createTime);
    }

    public String getToken() {
        return token;
    }

    public long getExpires() {
        return expires;
    }

    public long getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "token='" + token + '\'' +
                ", expires=" + expires +
                ", createTime=" + createTime +
                ", before=" + before +
                '}';
    }
}
