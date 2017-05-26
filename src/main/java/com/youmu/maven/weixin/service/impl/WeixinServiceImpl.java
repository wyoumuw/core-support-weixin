package com.youmu.maven.weixin.service.impl;

import com.youmu.maven.weixin.cache.Cache;
import com.youmu.maven.weixin.cache.MapCache;
import com.youmu.maven.weixin.service.WeixinService;
import com.youmu.maven.weixin.utils.WeixinUtils;
import com.youmu.maven.weixin.utils.model.AccessToken;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by dehua.lai on 2017/5/26.
 */
public class WeixinServiceImpl extends WeixinService {
    private Cache<String,AccessToken> accessTokenCache=new MapCache<String, AccessToken>();
    private ReentrantLock accessTokenLock =new ReentrantLock();


    public AccessToken getAccessToken(String appId, String appSecret){
        AccessToken at=accessTokenCache.get(appId);
        if(null==at){
            accessTokenLock.lock();
            try{
                at=accessTokenCache.get(appId);
                if(null==at){
                    at= WeixinUtils.getAccessToken(appId,appSecret);
                    //TODO log 记录日志
                }
            }finally {
                accessTokenLock.unlock();
            }
        }
        return at;
    }
}
