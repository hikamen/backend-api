package com.base.backend.core.weixin.utils;

import com.base.backend.common.Constants;
import com.base.backend.core.weixin.domain.WeiXinToken;
import com.base.backend.utils.HttpUtils;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Map;
import java.util.UUID;

/**
 * 微信相关公用方法
 *
 *
 */
@Component
public class WeiXinUtils {
    private final static Logger logger = LoggerFactory.getLogger(WeiXinUtils.class);

    public final static String WX_ACCESS_TOKEN_CACHE = "WX_ACCESS_TOKEN_CACHE";

    public final static String WX_ACCESS_TOKEN = "WX_ACCESS_TOKEN";

    public final static String WX_JSAPI_TICKET = "WX_JSAPI_TICKET";

    public final static String WX_MP_ACCESS_TOKEN = "WX_MP_ACCESS_TOKEN";

    /**
     * 获取临时票据
     * <p>
     * 因为临时票据有效期是7200秒，而且频繁刷新票据会导致调用受限影响自身业务，所以需要在服务器
     * 全局缓存临时票据。
     *
     * @return String
     */
    public WeiXinToken getAccessToken(String url) throws Exception {
        logger.debug("get access token......");
        long time = System.currentTimeMillis();

        // 调用微信接口
        String responseString = HttpUtils.getStringResult(url);
        logger.debug("response string : [{}]", responseString);

        Gson gson = new Gson();
        Map map = gson.fromJson(responseString, Map.class);
        if (MapUtils.isNotEmpty(map) &&
                MapUtils.getString(map, "access_token") != null) {
            // 凭证
            String accessToken = MapUtils.getString(map, "access_token");
            // 有效期
            int expiresIn = MapUtils.getInteger(map, "expires_in", 0);

            // 生成WeiXinAccessToken实例保存凭证相关信息
            WeiXinToken token = new WeiXinToken();
            token.setTime(time);
            token.setToken(accessToken);
            token.setExpiresIn(expiresIn);
            logger.debug("get access token - access_token [{}]", accessToken);
            logger.debug("get access token - expires_in [{}]", expiresIn);
            return token;
        }
        return null;
    }

    /**
     * 获取临时票据
     * <p>
     * 因为临时票据有效期是7200秒，而且频繁刷新票据会导致调用受限影响自身业务，所以需要在服务器
     * 全局缓存临时票据。
     *
     * @return String
     */
    public WeiXinToken getJsapiTicket(String url) throws Exception {
        logger.debug("get jsapi ticket......");
        long time = System.currentTimeMillis();

        // 调用微信接口
        String responseString = HttpUtils.getStringResult(url);
        logger.debug("response string : [{}]", responseString);

        Gson gson = new Gson();
        Map map = gson.fromJson(responseString, Map.class);
        if (MapUtils.isNotEmpty(map) &&
                MapUtils.getString(map, "ticket") != null) {
            // 凭证
            String ticket = MapUtils.getString(map, "ticket");
            // 有效期
            int expiresIn = MapUtils.getInteger(map, "expires_in", 0);

            // 生成WeiXinAccessToken实例保存凭证相关信息
            WeiXinToken token = new WeiXinToken();
            token.setTime(time);
            token.setToken(ticket);
            token.setExpiresIn(expiresIn);
            logger.debug("get jsapi ticket - ticket [{}]", ticket);
            logger.debug("get jsapi ticket - expires_in [{}]", expiresIn);
            return token;
        }
        return null;
    }

    /**
     * 获取使用权限签名
     *
     * @param jsapiTicket 临时票据
     * @param url         当前网页的URL，不包含#及其后面部分
     * @return Map
     */
    public Map<String, String> getSignature(String jsapiTicket, String url) {
        logger.debug("get signature [{}]......", url);

        // 随机字符串
        String nonceStr = createNonceStr();
        // 时间戳
        String timestamp = createTimestamp();

        String signature = "";

        // 对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）后，
        // 使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串string1。
        // 这里需要注意的是所有参数名均为小写字符。
        // 对string1作sha1加密，字段名和字段值都采用原始值，不进行URL 转义。

        // 步骤1.
        // 对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）后，
        // 使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串
        String str = "jsapi_ticket=" + jsapiTicket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + url;

        logger.debug("jsapi ticket [{}]", jsapiTicket);
        logger.debug("url [{}]", url);
        logger.debug("timestamp [{}]", timestamp);
        logger.debug("nonceStr [{}]", nonceStr);
        logger.debug("signature [{}]", str);

        // 步骤2.
        // 对string1进行sha1签名，得到signature
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(str.getBytes(Constants.ENCODING));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.error("sign failed.", e);
            e.printStackTrace();
        }

        Map<String, String> ret = Maps.newHashMap();
        ret.put("url", url);
        ret.put("jsapi_ticket", jsapiTicket);
        ret.put("nonceStr", nonceStr);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String createNonceStr() {
        return UUID.randomUUID().toString();
    }

    private static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

}
