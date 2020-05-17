package com.base.backend.core.weixin.domain;

import java.io.Serializable;

/**
 * 微信访问凭证
 *
 */
public class WeiXinToken implements Serializable {
    /**
     * 凭证
     */
    private String token;
    /**
     * 有效期
     */
    private int expiresIn;
    /**
     * 生成时间
     */
    private long time;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
