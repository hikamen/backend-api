package com.base.backend.common.jackson;

import com.base.backend.utils.DateUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Timestamp;

public class TimestampSerializer extends JsonSerializer<Timestamp> {
    @Override
    public void serialize(Timestamp value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (DateUtils.isMinTimestamp(value) || DateUtils.isMaxTimestamp(value)) {
            // 如果为系统默认的最小或者最大时间，则返回空字符串
            // 这里判断只根据年月日做判断，忽略时间部分
            gen.writeString("");
        } else {
            gen.writeString(DateUtils.formatTimestamp(value));
        }
    }
}
