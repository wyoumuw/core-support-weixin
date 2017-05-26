package com.youmu.maven.weixin.utils;


import com.alibaba.fastjson.JSONObject;
import com.sun.istack.internal.NotNull;
import com.youmu.maven.weixin.utils.model.AccessToken;
import com.youmu.maven.weixin.utils.model.NetResult;
import com.youmu.maven.weixin.utils.model.ajax.result.WeixinJsConfigResult;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

/**
 * Created by dehua.lai on 2017/5/26.
 */
public abstract class WeixinUtils {
    public static final String[] URL_AUTH2={"https://open.weixin.qq.com/connect/oauth2/authorize?appid=","&redirect_uri=","&response_type=code&scope=","&state=","#wechat_redirect"};
    public static final String[] URL_ACCESS_TOKEN={"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=","&secret="};

    public static String makeAuthUrl(@NotNull String appId, @NotNull String redirectUrl, @NotNull Scope scope, @NotNull String state){
        if(StringUtils.isEmpty(appId)||StringUtils.isEmpty(redirectUrl)||null==scope||StringUtils.isEmpty(state)){
            throw new NullPointerException();
        }
        String encodedUrl="";
        try {
            encodedUrl = URLEncoder.encode(redirectUrl,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("illegal url");
        }
        return new StringBuilder(URL_AUTH2[0]).append(appId).append(URL_AUTH2[1]).append(encodedUrl).append(URL_AUTH2[2]).append(scope.getScope()).append(URL_AUTH2[3]).append(state).append(URL_AUTH2[4]).toString();

    }

    public static WeixinJsConfigResult makeJsConfigResult(@NotNull String appId){
        if(StringUtils.isEmpty(appId)){
            throw new NullPointerException("appId can not null!");
        }
return null;
    }

    public static String generateNonceStr(){
        new Random().nextLong();
        return null;
    }


    public static String getJsticket(){

        return null;
    }

    public static AccessToken getAccessToken(String appId, String appSecret){
        StringBuilder sb=new StringBuilder();
        AccessToken at=null;
        sb.append(URL_ACCESS_TOKEN[0]).append(appId).append(URL_ACCESS_TOKEN[1]).append(appSecret);
        NetResult netResult=NetUtils.get(sb.toString(),null);
        if(netResult.isOk()) {
            try {
                JSONObject result = JSONObject.parseObject(netResult.getContent());
                at = new AccessToken(result.getString("access_token"), Long.valueOf(result.get("expires_in").toString()).longValue()*1000);//转ms
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return at;
    }














    public static enum Scope{
        snsapi_base("snsapi_base"),snsapi_userinfo("snsapi_userinfo");
        private String scope;
        Scope(String scope){
            this.scope=scope;
        }

        public String getScope() {
            return scope;
        }
    }
}
