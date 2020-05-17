package com.base.backend.utils;

import com.base.backend.common.Constants;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

import java.util.Iterator;
import java.util.List;

/**
 * 字符串转换成其他类型的工具类
 */
public class ConvertUtils {
    private static ConversionService conversionService = DefaultConversionService.getSharedInstance();

    public static <T> T convert(String value, Class<T> targetType) {
        return convert(value, targetType, null);
    }

    public static <T> T convert(String value, Class<T> targetType, T defaultValue) {
        T t = conversionService.convert(value, targetType);
        return t == null ? defaultValue : t;
    }

    public static <T> List<T> stringToList(String str, Class<T> targetType) {
        return stringToList(str, Constants.DELIMITER, targetType);
    }

    public static <T> List<T> stringToList(String str, String delimiter, Class<T> targetType) {
        if (Strings.isNullOrEmpty(str)) {
            return Lists.newArrayList();
        }

        Iterable<String> ia = Splitter.on(delimiter).split(str);

        List<T> lst = Lists.newArrayList();
        Iterator<String> it = ia.iterator();
        while (it.hasNext()) {
            lst.add(ConvertUtils.convert(it.next(), targetType));
        }
        return lst;
    }

    public static <T> String listToString(List<T> list) {
        return listToString(list, Constants.DELIMITER);
    }

    public static <T> String listToString(List<T> list, String delimiter) {
        String str = null;
        if (list != null && !list.isEmpty()) {
            str = "";
            for (int i = 0; i < list.size(); i++) {
                str += list.get(i);
                if (i != (list.size() - 1)) {
                    str += delimiter;
                }
            }
        }
        return str;
    }
}
