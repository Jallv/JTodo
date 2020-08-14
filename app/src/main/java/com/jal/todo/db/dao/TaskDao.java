package com.jal.todo.db.dao;

import com.jal.todo.db.entity.Task;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface TaskDao {
    @Query("SELECT * from task")
    Flowable<List<Task>> getAllTask();

    @Query("SELECT * from task WHERE date=:date ORDER BY createTime DESC")
    Single<List<Task>> getTaskByDate(String date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Task... tasks);

    @Delete
    void delete(Task... tasks);
}
