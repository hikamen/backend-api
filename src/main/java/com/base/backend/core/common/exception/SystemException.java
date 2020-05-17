package com.base.backend.core.common.exception;

/**
 * 系统异常
 * <p>
 * 通常在控制层或者服务层直接抛出系统异常出来，交由Spring自己处理异常并返回给用户。
 */
public class SystemException extends RuntimeException {
    public SystemException(Throwable ex) {
        super(ex);
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable ex) {
        super(message, ex);
    }
}
