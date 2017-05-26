package com.youmu.maven.weixin.utils.model.ajax.result;

/**
 * Created by dehua.lai on 2017/5/26.
 */
public class WeixinJsConfigResult {
    private String appId; // 必填，公众号的唯一标识
    private long timestamp; // 必填，生成签名的时间戳
    private String nonceStr; // 必填，生成签名的随机串
    private String signature;// 必填，签名，见附录1

    public WeixinJsConfigResult() {
    }

    public WeixinJsConfigResult(String appId, long timestamp, String nonceStr, String signature) {
        this.appId = appId;
        this.timestamp = timestamp;
        this.nonceStr = nonceStr;
        this.signature = signature;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
