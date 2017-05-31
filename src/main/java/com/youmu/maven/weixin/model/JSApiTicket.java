package com.youmu.maven.weixin.model;

import com.youmu.maven.weixin.model.ExpireableToken;

/**
 * Created by youmu on 2017/5/26.
 */
public class JSApiTicket extends ExpireableToken {
    private  String ticket;


    @SuppressWarnings("unsafe")
    public JSApiTicket(){}
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

    @SuppressWarnings("unsafe")
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
