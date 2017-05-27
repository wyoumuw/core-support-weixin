package com.youmu.maven.weixin.service.impl;

import com.youmu.maven.utils.StringUtils;
import com.youmu.maven.utils.cache.Cache;
import com.youmu.maven.utils.cache.MapCache;
import com.youmu.maven.weixin.model.AccessToken;
import com.youmu.maven.weixin.model.JSApiTicket;
import com.youmu.maven.weixin.model.WeixinJsConfig;
import com.youmu.maven.weixin.service.WeixinService;
import com.youmu.maven.weixin.utils.WeixinUtils;

import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by youmu on 2017/5/26.
 */
public class WeixinServiceImpl extends WeixinService {
    private Cache<String,AccessToken> accessTokenCache;
    private Cache<String,JSApiTicket> jsapiTicketCache;
    private ReentrantLock accessTokenLock =new ReentrantLock();
    private ReentrantLock jsapiTicketLock =new ReentrantLock();

    public WeixinServiceImpl(){
        this(MapCache.class);
    }

    public WeixinServiceImpl(Class<? extends Cache> clazz) {
        try {
            accessTokenCache = clazz.newInstance();
            jsapiTicketCache=clazz.newInstance();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public WeixinServiceImpl(Cache accessTokenCache, Cache jsapiTicketCache) {
        this.accessTokenCache = accessTokenCache;
        this.jsapiTicketCache = jsapiTicketCache;
    }

    @Override
    public AccessToken getAccessToken(String appId, String appSecret){
        AccessToken at=accessTokenCache.get(appId);
        if(null==at||at.isExpired()){
            accessTokenLock.lock();
            try{
                at=accessTokenCache.get(appId);
                if(null==at||at.isExpired()){
                    at= WeixinUtils.getAccessToken(appId,appSecret);
                    accessTokenCache.put(appId,at);
                    //TODO log 记录日志
                }
            }finally {
                accessTokenLock.unlock();
            }
        }
        return at;
    }
    @Override
    public JSApiTicket getJSApiTicket(String appId, String appSecret){
        JSApiTicket ticket=jsapiTicketCache.get(appId);
        if(null==ticket||ticket.isExpired()){
            jsapiTicketLock.lock();
            try{
                ticket=jsapiTicketCache.get(appId);
                if(null==ticket||ticket.isExpired()){
                    AccessToken at=getAccessToken(appId,appSecret);
                    ticket= WeixinUtils.getJSApiTicket(at.getToken());
                    jsapiTicketCache.put(appId,ticket);
                    //TODO log 记录日志
                }
            }finally {
                jsapiTicketLock.unlock();
            }
        }
        return ticket;
    }

    @Override
    public WeixinJsConfig makeJsConfigResult(String appId,String appSecret,String url){
        if(StringUtils.isEmpty(appId)){
            throw new NullPointerException("appId can not null!");
        }
        String nonceStr=StringUtils.generateNonceStr(16);
        long timestamp=new Date().getTime();
        String signature=WeixinUtils.jsapiSignature(getJSApiTicket(appId,appSecret).getTicket(),url,nonceStr,timestamp);
        return new WeixinJsConfig(appId,timestamp,nonceStr,signature);
    }
}
