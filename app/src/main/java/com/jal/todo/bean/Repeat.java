package com.jal.todo.bean;

import com.jal.todo.db.entity.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Repeat implements Serializable {
    private static final long serialVersionUID = 6701650426777370126L;
    public final static int REPEAT_EVERY_DAY = 1;
    public final static int REPEAT_EVERY_WEEK = 2;
    public final static String REPEAT_DATE = "-1";

    private int repeat = 0;
    private List<Integer> week;

    public Repeat(Task task) {
        if ("-1".equals(task.date)) {
            repeat = REPEAT_EVERY_DAY;
        } else if (task.week != 0) {
            repeat = REPEAT_EVERY_WEEK;
            week = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                if ((task.week & 1 << i) != 0) {
                    week.add(i + 1);
                }
            }
        }
    }

    public void addRepeat(Task task) {
        if (repeat == REPEAT_EVERY_DAY) {
            task.week = 0;
            task.date = REPEAT_DATE;
        } else if (repeat == REPEAT_EVERY_WEEK) {
            task.week = 0;
            for (int item : week) {
                task.week = task.week | 1 << (item - 1);
            }
        }
    }

    public Repeat(int repeat) {
        this.repeat = repeat;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public List<Integer> getWeek() {
        return week;
    }

    public void setWeek(List<Integer> week) {
        this.week = week;
    }
}
