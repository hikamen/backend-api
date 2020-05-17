package com.base.backend.core.weixin.service;

import com.base.backend.core.common.exception.SystemException;
import com.base.backend.core.weixin.domain.WeiXinConfig;
import com.base.backend.core.weixin.domain.WeiXinToken;

import java.util.Map;

/**
 * 微信服务
 *
 */
public interface WeiXinService {
    /**
     * 全局唯一接口调用凭据
     * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183
     */
    String getAccessToken() throws SystemException;

    /**
     * 全局唯一接口调用凭据
     */
    String getJsapiTicket() throws SystemException;

    /**
     * 获取微信签名
     *
     * @param url 当前页面URL
     * @return Map
     */
    Map<String, String> getSignature(String url) throws SystemException;

    /**
     * 全局唯一接口调用凭据
     * <p>
     * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183
     */
    String getMpAccessToken() throws SystemException;

    /**
     * 从缓存获取访问凭证
     *
     * @param key   凭证标识
     * @param orgId 机构ID
     * @return WeiXinToken
     */
    WeiXinToken getWeiXinToken(String key, Long orgId);

    /**
     * 保存访问凭证到缓存
     *
     * @param key   凭证标识
     * @param orgId 机构ID
     * @param token 凭证
     * @return WeiXinToken
     */
    WeiXinToken putWeiXinToken(String key, Long orgId, WeiXinToken token);

    /**
     * 从缓存移除访问凭证
     *
     * @param key   凭证标识
     * @param orgId 机构ID
     */
    void deleteWeiXinToken(String key, Long orgId);

    /**
     * 获取指定机构的微信相关配置
     *
     * @param orgId 机构ID
     * @return WeiXinConfig
     */
    WeiXinConfig getWeiXinConfig(Long orgId);
}
