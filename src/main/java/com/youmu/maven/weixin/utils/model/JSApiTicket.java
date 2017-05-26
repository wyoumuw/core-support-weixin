package com.youmu.maven.weixin.utils.model;

/**
 * Created by dehua.lai on 2017/5/26.
 */
public class JSApiTicket extends ExpireableToken{
    private  String ticket;
    /**
     *
     * @param ticket
     * @param expires ms
     */
    public JSApiTicket(String ticket, long expires) {
        super(expires);
        this.ticket = ticket;
    }
    /**
     *
     * @param ticket
     * @param expires ms
     * @param before
     */
    public JSApiTicket(String ticket, long expires, long before) {
        super(expires,before);
        this.ticket = ticket;
    }

    public String getTicket() {
        return ticket;
    }
}
