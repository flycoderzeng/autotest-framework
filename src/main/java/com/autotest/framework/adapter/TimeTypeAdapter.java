package com.autotest.framework.adapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTypeAdapter implements JsonSerializer<Time> {
    public static final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

    @Override
    public JsonElement serialize(Time time, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(format.format(new Date(time.getTime())));
    }
}
