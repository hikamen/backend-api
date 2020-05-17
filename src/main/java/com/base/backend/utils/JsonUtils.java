package com.base.backend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * Json相关的工具类
 */
@SuppressWarnings({"unchecked"})
public class JsonUtils {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtils() {
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static String toJson(Object obj) throws Exception {
        return getObjectMapper().writeValueAsString(obj);
    }

    public static <T> T toObject(String json, Class<T> clazz) throws Exception {
        return getObjectMapper().readValue(json, clazz);
    }

    public static Map<String, Object> toMap(String json) throws Exception {
        return getObjectMapper().readValue(json, Map.class);
    }

    public static <T> T mapToObject(Map map, Class<T> clazz) {
        return getObjectMapper().convertValue(map, clazz);
    }
}
