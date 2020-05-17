package com.base.backend.utils;

import org.apache.commons.text.StringEscapeUtils;

/**
 * 通用的字符串工具类
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static String toHtml(String txt) {
        if (txt == null) {
            return "";
        }
        return replace(replace(StringEscapeUtils.escapeHtml4(txt), "\n", "<br/>"), "\t", "&nbsp; &nbsp; ");
    }
}
