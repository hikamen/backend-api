package com.base.backend.common;

/**
 * 全局变量
 */
public class Constants {

    // 系统默认编码
    public final static String ENCODING = "UTF-8";

    // 默认分页参数
    public final static int DEFAULT_PAGE = 1;
    public final static int DEFAULT_PAGE_SIZE = 12;
    // 排序方式
    public final static String ASC = "asc";
    public final static String DESC = "desc";


    // 字符串分隔符（页面请求如果参数是多个值，那默认用逗号分隔）
    public final static String STR_DELIMITER = ",";
    // 默认的分隔符（用于数据库数据存储）
    public final static String DELIMITER = "~|~";
    public static final String DELIMITER2 = "~~";
    public static final String DELIMITER3 = "~";

    public static final String DOT = ".";


    public static final String AUTHORIZATION = "x-access-token";
    public static final int TOKEN_EXPIRATION_TIME = 60 * 24 * 1;
    public static final String JWT_KEY = "Warehouse";
    public static final String LOGIN_USER_ID = "LOGIN_USER_ID";
}