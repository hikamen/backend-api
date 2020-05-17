package com.base.backend.core.web;


import java.io.Serializable;

/**
 * 接口返回数据
 *
 * @author kamen
 * @param <E>
 */
public class WebApiResponse<E> implements Serializable {

    public final static int SUCCESS = 0;
    public final static int ERROR = 1;

    /**
     * 状态编码
     */
    private int code;

    /**
     * 错误码
     */
    private String errorCode;
    /**
     * 信息
     */
    private String message;
    /**
     * 数据
     */
    private E data;

    public WebApiResponse() {
    }

    public static WebApiResponse<?> success() {
        return new WebApiResponse<>(SUCCESS);
    }

    public static <T> WebApiResponse<T> success(T data) {
        WebApiResponse<T> result = new WebApiResponse<T>(SUCCESS);
        result.setData(data);
        return result;
    }

    public static WebApiResponse<?> error() {
        return new WebApiResponse<>(ERROR);
    }

    public static WebApiResponse<?> error(String errorCode, String message) {
        return new WebApiResponse<>(ERROR, errorCode, message);
    }

    public WebApiResponse(int code) {
        this.code = code;
    }

    public WebApiResponse(int code, String errorCode, String message) {
        this.code = code;
        this.errorCode = errorCode;
        this.message = message;
    }

    public WebApiResponse(int code,String errorCode, String message, E data) {
        this.code = code;
        this.errorCode = errorCode;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
