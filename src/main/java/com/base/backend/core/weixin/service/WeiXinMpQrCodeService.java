package com.base.backend.core.weixin.service;

import java.io.IOException;

/**
 * 微信小程序生成页面二维码服务
 * https://developers.weixin.qq.com/miniprogram/dev/api/qrcode.html
 *
 */
public interface WeiXinMpQrCodeService {
    /**
     * 生成小程序任意页面的二维码
     *
     * @param page     String
     * @param scene    String
     * @param filename String
     * @return String
     * @throws IOException IOException
     */
    String generateMpPageQrCode(String page, String scene, String filename) throws Exception;
}
