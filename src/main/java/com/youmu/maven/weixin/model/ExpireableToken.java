package com.youmu.maven.weixin.model;

/**
 * Created by youmu on 2017/5/26.
 */
public abstract class ExpireableToken {
    // 10 min before
    public static final long DEFAULT_BEFORE=60*10*1000;
    /**
     * time unit is (ms)
     */
    private  long expires;
    private  long createTime;
    private long before;
    /**
     *
     * @param expires ms
     */
    public ExpireableToken(long expires) {
        this(expires,DEFAULT_BEFORE);
    }

    /**
     *
     * @param expires ms
     * @param before
     */
    public ExpireableToken(long expires, long before) {

        this.expires = expires;
        this.createTime=System.currentTimeMillis();
        this.before = before;
    }

    public boolean isExpired(){
        return (this.expires-this.before)<=(System.currentTimeMillis()-this.createTime);
    }

    public long getExpires() {
        return expires;
    }

    public long getCreateTime() {
        return createTime;
    }
}
