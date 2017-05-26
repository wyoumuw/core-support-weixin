package com.youmu.maven.weixin.service;

import com.youmu.maven.weixin.model.WeixinJsConfig;
import com.youmu.maven.weixin.utils.model.AccessToken;
import com.youmu.maven.weixin.utils.model.JSApiTicket;

/**
 * Created by dehua.lai on 2017/5/26.
 */
public abstract class WeixinService {
    public abstract AccessToken getAccessToken(String appId, String appSecret);

    public abstract JSApiTicket getJSApiTicket(String appId, String appSecret);

    public abstract WeixinJsConfig makeJsConfigResult(String appId, String appSecret, String url);
}
