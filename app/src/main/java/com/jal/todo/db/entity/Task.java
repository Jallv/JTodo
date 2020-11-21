package com.jal.todo.db.entity;

import com.jal.todo.bean.Repeat;
import com.jal.todo.db.RepeatConverters;
import com.jal.todo.db.TaskConverters;

import java.io.Serializable;
import java.util.List;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "task", indices = {@Index("createTime")})
@TypeConverters({TaskConverters.class, RepeatConverters.class})
public class Task implements Serializable {
    private static final long serialVersionUID = -9143615706117242335L;
    @PrimaryKey()
    public long createTime;
    public String date;
    public int week;
    public boolean isCompleted;
    public String content;
    public String remindTime;
    public List<Task> subTaskList;
    public int priority;

    public Task() {

    }

    @Ignore
    public Task(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return createTime == task.createTime;
    }

    @Override
    public int hashCode() {
        return (int) (createTime ^ (createTime >>> 32));
    }
}
