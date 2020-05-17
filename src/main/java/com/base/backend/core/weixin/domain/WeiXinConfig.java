package com.base.backend.core.weixin.domain;

import java.io.Serializable;

/**
 * 当前机构微信相关配置
 *
 */
public class WeiXinConfig implements Serializable {
    /**
     * 是否启用多机构多公众号
     */
    private Boolean multiEnable;
    /**
     * 微信公众号
     * APP_ID
     */
    private String appId;
    /**
     * 微信公众号
     * APP_SECRET
     */
    private String appSecret;
    /**
     * 微信主体小程序
     * APP_ID
     */
    private String mpAppId;
    /**
     * 微信主体小程序
     * APP_SECRET
     */
    private String mpAppSecret;
    /**
     * 当前机构微信端访问域名
     */
    private String weixinDomain;

    public WeiXinConfig() {
    }

    public WeiXinConfig(Boolean multiEnable) {
        this.multiEnable = multiEnable;
    }

    public Boolean getMultiEnable() {
        return multiEnable;
    }

    public void setMultiEnable(Boolean multiEnable) {
        this.multiEnable = multiEnable;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getMpAppId() {
        return mpAppId;
    }

    public void setMpAppId(String mpAppId) {
        this.mpAppId = mpAppId;
    }

    public String getMpAppSecret() {
        return mpAppSecret;
    }

    public void setMpAppSecret(String mpAppSecret) {
        this.mpAppSecret = mpAppSecret;
    }

    public String getWeixinDomain() {
        return weixinDomain;
    }

    public void setWeixinDomain(String weixinDomain) {
        this.weixinDomain = weixinDomain;
    }
}
