package com.base.backend.common.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.base.backend.common.jackson.LocalDateTimeDeserializer.FORMATTER;

/**
 * @author kamen
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (LocalDateTime.MIN.isEqual(value) || LocalDateTime.MAX.isEqual(value)) {
            // 如果为系统默认的最小或者最大时间，则返回空字符串
            // 这里判断只根据年月日做判断，忽略时间部分
            gen.writeString("");
        } else {
            gen.writeString(value.format(FORMATTER));
        }
    }
}
