package com.youmu.maven.weixin.utils;


import com.alibaba.fastjson.JSONObject;
import com.youmu.maven.weixin.utils.model.AccessToken;
import com.youmu.maven.weixin.utils.model.JSApiTicket;
import com.youmu.maven.weixin.utils.model.NetResult;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by dehua.lai on 2017/5/26.
 */
public abstract class WeixinUtils {
    public static final String[] URL_AUTH2={"https://open.weixin.qq.com/connect/oauth2/authorize?appid=","&redirect_uri=","&response_type=code&scope=","&state=","#wechat_redirect"};
    public static final String[] URL_ACCESS_TOKEN={"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=","&secret="};
    public static final String[] URL_JSAPI_TICKET={"https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=","&type=jsapi"};



    public static String makeAuthUrl( String appId,  String redirectUrl,  Scope scope,  String state){
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


    /**
     * @param ticket
     * @param url "当前网页的URL，不包含#及其后面部分。注意：对于没有只有域名没有 path 的 URL ，浏览器会自动加上 / 作为 path，如打开 http://qq.com 则获取到的 URL 为 http://qq.com/"
     * @param nonceStr com.youmu.maven.weixin.utils.StringUtils#generateNonceStr()
     * @param timestamp ms
     * @return
     */
    public static String jsapiSignature(String ticket,String url,String nonceStr,long timestamp){
        String mix=new StringBuilder().append("jsapi_ticket=").append(ticket).append("&noncestr=").append(nonceStr).append("&timestamp=").append(timestamp/1000).append("&url=").append(url).toString();
        System.out.println(mix);
        byte[] code=DigestUtils.getSha1Digest().digest(mix.getBytes());
        return Hex.encodeHexString(code);
    }


    public static JSApiTicket getJSApiTicket(String token){
        StringBuilder sb=new StringBuilder();
        JSApiTicket ticket=null;
        sb.append(URL_JSAPI_TICKET[0]).append(token).append(URL_JSAPI_TICKET[1]);
        NetResult netResult=NetUtils.get(sb.toString(),null);
        if(netResult.isOk()) {
            try {
                //TODO 删除
                System.out.println(netResult.getContent());
                JSONObject result = JSONObject.parseObject(netResult.getContent());
                check(result,true);
                ticket = new JSApiTicket(result.getString("ticket"), Long.valueOf(result.get("expires_in").toString()).longValue()*1000);//转ms
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return ticket;
    }

    public static AccessToken getAccessToken(String appId, String appSecret){
        StringBuilder sb=new StringBuilder();
        AccessToken at=null;
        sb.append(URL_ACCESS_TOKEN[0]).append(appId).append(URL_ACCESS_TOKEN[1]).append(appSecret);
        NetResult netResult=NetUtils.get(sb.toString(),null);
        if(netResult.isOk()) {
            try {
                //TODO 删除
                System.out.println(netResult.getContent());
                JSONObject result = JSONObject.parseObject(netResult.getContent());
                check(result,false);
                at = new AccessToken(result.getString("access_token"), Long.valueOf(result.get("expires_in").toString()).longValue()*1000);//转ms
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return at;
    }

    public static void check(JSONObject result,boolean throwOut){
        try{
           Integer code= result.getInteger("errcode");
           if(null==code||0!=code){
               String msg=result.getString("errmsg");
                throw new Exception(StringUtils.isEmpty(msg)?"error":msg);
           }
        }catch (Exception e){
            if(throwOut){
                throw new RuntimeException(e);
            }
        }
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
