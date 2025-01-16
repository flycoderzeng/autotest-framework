package com.autotest.framework.adapter;

import cn.hutool.core.date.DateUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime> {

    @Override
    public JsonElement serialize(LocalDateTime time, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(DateUtil.format(time, "yyyy-MM-dd HH:mm:ss"));
    }
}
