package com.jal.todo.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jal.todo.db.entity.Task;

import java.util.List;

import androidx.room.TypeConverter;

public class TaskConverters {
    @TypeConverter
    public List<Task> stringToObject(String value) {
        return new Gson().fromJson(value, new TypeToken<List<Task>>() {
        }.getType());
    }

    @TypeConverter
    public String objectToString(List<Task> tasks) {
        return new Gson().toJson(tasks);
    }
}
