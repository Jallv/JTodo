package com.jal.todo.db;

import com.google.gson.Gson;
import com.jal.todo.bean.Repeat;

import androidx.room.TypeConverter;

public class RepeatConverters {
    @TypeConverter
    public Repeat stringToObject(String value) {
        return new Gson().fromJson(value, Repeat.class);
    }

    @TypeConverter
    public String objectToString(Repeat repeat) {
        return new Gson().toJson(repeat);
    }
}
