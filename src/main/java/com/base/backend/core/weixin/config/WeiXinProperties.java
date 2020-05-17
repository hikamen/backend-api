package com.base.backend.core.weixin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信相关配置项
 */
@ConfigurationProperties(prefix = "weixin")
public class WeiXinProperties {
    /**
     *
     */
    private String requestAccessTokenUrl = "";
    /**
     *
     */
    private String requestJsapiTicketUrl = "";
    /**
     *
     */
    private String requestMpQrCodeUrl = "";
    /**
     *
     */
    private String requestDownloadMediaUrl = "";

    private String minaAppId = "";

    private String minaAppSecret = "";

    private String minaCodeToSessionUrl = "";

    public String getRequestAccessTokenUrl() {
        return requestAccessTokenUrl;
    }

    public void setRequestAccessTokenUrl(String requestAccessTokenUrl) {
        this.requestAccessTokenUrl = requestAccessTokenUrl;
    }

    public String getRequestJsapiTicketUrl() {
        return requestJsapiTicketUrl;
    }

    public void setRequestJsapiTicketUrl(String requestJsapiTicketUrl) {
        this.requestJsapiTicketUrl = requestJsapiTicketUrl;
    }

    public String getRequestMpQrCodeUrl() {
        return requestMpQrCodeUrl;
    }

    public void setRequestMpQrCodeUrl(String requestMpQrCodeUrl) {
        this.requestMpQrCodeUrl = requestMpQrCodeUrl;
    }

    public String getRequestDownloadMediaUrl() {
        return requestDownloadMediaUrl;
    }

    public void setRequestDownloadMediaUrl(String requestDownloadMediaUrl) {
        this.requestDownloadMediaUrl = requestDownloadMediaUrl;
    }

    public String getMinaAppId() {
        return minaAppId;
    }

    public void setMinaAppId(String minaAppId) {
        this.minaAppId = minaAppId;
    }

    public String getMinaAppSecret() {
        return minaAppSecret;
    }

    public void setMinaAppSecret(String minaAppSecret) {
        this.minaAppSecret = minaAppSecret;
    }

    public String getMinaCodeToSessionUrl() {
        return minaCodeToSessionUrl;
    }

    public void setMinaCodeToSessionUrl(String minaCodeToSessionUrl) {
        this.minaCodeToSessionUrl = minaCodeToSessionUrl;
    }
}
