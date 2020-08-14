package com.jal.todo.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jal.todo.bean.RepeatTime;
import com.jal.todo.db.entity.Task;

import java.util.List;

import androidx.room.TypeConverter;

public class RepeatConverters {
    @TypeConverter
    public RepeatTime stringToObject(String value) {
        return new Gson().fromJson(value, RepeatTime.class);
    }

    @TypeConverter
    public String objectToString(RepeatTime repeatTime) {
        return new Gson().toJson(repeatTime);
    }
}
