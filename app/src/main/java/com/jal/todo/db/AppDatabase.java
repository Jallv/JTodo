package com.jal.todo.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jal.todo.db.dao.TaskDao;
import com.jal.todo.db.entity.Task;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
// 注释
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "todo_db";

    public abstract TaskDao taskDao();

    private static AppDatabase mInstance;

    public static AppDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (AppDatabase.class) {
                if (mInstance == null) {
                    mInstance = Room
                            .databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                            .build();
                }

            }
        }
        return mInstance;
    }
}