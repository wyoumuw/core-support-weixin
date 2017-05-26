package com.youmu.maven.weixin.service.impl;

import com.youmu.maven.weixin.cache.Cache;
import com.youmu.maven.weixin.cache.MapCache;
import com.youmu.maven.weixin.model.WeixinJsConfig;
import com.youmu.maven.weixin.service.WeixinService;
import com.youmu.maven.weixin.utils.StringUtils;
import com.youmu.maven.weixin.utils.WeixinUtils;
import com.youmu.maven.weixin.utils.model.AccessToken;
import com.youmu.maven.weixin.utils.model.JSApiTicket;

import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by dehua.lai on 2017/5/26.
 */
public class WeixinServiceImpl extends WeixinService {
    private Cache<String,AccessToken> accessTokenCache=new MapCache<String, AccessToken>();
    private ReentrantLock accessTokenLock =new ReentrantLock();
    private Cache<String,JSApiTicket> jsapiTicketCache=new MapCache<String, JSApiTicket>();
    private ReentrantLock jsapiTicketLock =new ReentrantLock();

    @Override
    public AccessToken getAccessToken(String appId, String appSecret){
        AccessToken at=accessTokenCache.get(appId);
        if(null==at||at.isExpired()){
            accessTokenLock.lock();
            try{
                at=accessTokenCache.get(appId);
                if(null==at||at.isExpired()){
                    at= WeixinUtils.getAccessToken(appId,appSecret);

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
        String nonceStr=StringUtils.generateNonceStr();
        long timestamp=new Date().getTime();
        String signature=WeixinUtils.jsapiSignature(getJSApiTicket(appId,appSecret).getTicket(),url,nonceStr,timestamp);
        return new WeixinJsConfig(appId,timestamp,nonceStr,signature);
    }
}
