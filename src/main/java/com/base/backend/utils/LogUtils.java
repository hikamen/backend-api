package com.base.backend.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 */
public class LogUtils {
    private static ThreadLocal<Logger> currentLogger = new ThreadLocal<Logger>();

    private static Logger defaultLogger = LoggerFactory.getLogger("defaultLogger");

    private LogUtils() {
    }

    public static Logger getLogger() {
        Logger logger = currentLogger.get();
        if (null == logger) {
            return defaultLogger;
        } else {
            return logger;
        }
    }

    public static void setLogger(Logger logger) {
        currentLogger.set(logger);
    }

    public static void setDefaultLogger() {
        currentLogger.set(defaultLogger);
    }
}
