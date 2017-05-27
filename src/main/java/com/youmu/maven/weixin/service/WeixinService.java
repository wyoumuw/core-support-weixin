package com.youmu.maven.weixin.service;

import com.youmu.maven.weixin.model.WeixinJsConfig;
import com.youmu.maven.weixin.model.AccessToken;
import com.youmu.maven.weixin.model.JSApiTicket;

/**
 * Created by youmu on 2017/5/26.
 */
public abstract class WeixinService {
    public abstract AccessToken getAccessToken(String appId, String appSecret);

    public abstract JSApiTicket getJSApiTicket(String appId, String appSecret);

    public abstract WeixinJsConfig makeJsConfigResult(String appId, String appSecret, String url);
}
