package com.base.backend.common.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;

import java.time.LocalDateTime;

/**
 * @author kamen
 */
public class CustomJsonModule extends SimpleModule {
    public CustomJsonModule() {
        this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
    }
}
