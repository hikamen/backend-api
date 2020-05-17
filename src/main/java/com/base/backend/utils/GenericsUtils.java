package com.base.backend.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型工具类
 */
public class GenericsUtils {
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getSuperClassGenricType(Class<T> clazz, int index) {
        // 得到泛型父类
        Type genType = clazz.getGenericSuperclass();

        // 如果没有实现ParameterizedType接口，即不支持泛型，直接返回Object.class
        if (!(genType instanceof ParameterizedType)) {
            return null;
        }

        clazz.getAnnotatedInterfaces();
        // 返回表示此类型实际类型参数的Type对象的数组,数组里放的都是对应类型的Class, 如BuyerServiceBean extends
        // DaoSupport<Buyer,Contact>就返回Buyer和Contact类型
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            throw new RuntimeException("invalid index.");
        }

        if (!(params[index] instanceof Class)) {
            return null;
        }
        return (Class) params[index];
    }

    @SuppressWarnings("unchecked")
    public static Class getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }
}