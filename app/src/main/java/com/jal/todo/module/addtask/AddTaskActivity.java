package com.jal.todo.module.addtask;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.jaeger.library.StatusBarUtil;
import com.jal.core.mvvm.base.BaseActivity;
import com.jal.todo.BR;
import com.jal.todo.R;
import com.jal.todo.bean.Repeat;
import com.jal.todo.databinding.ActivityAddTaskBinding;
import com.jal.todo.db.entity.Task;
import com.jal.todo.module.task.TaskViewModel;
import com.jal.todo.widget.SelectRepeatDialog;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import androidx.lifecycle.Observer;
import jal.dev.common.utils.ScreenUtils;

public class AddTaskActivity extends BaseActivity<ActivityAddTaskBinding, AddTaskViewModel> {
    public static final String CALENDAR = "calendar";
    public static final String TASK = "task";

    public static Bundle getAddTaskBundle(Calendar calendar) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CALENDAR, calendar);
        return bundle;
    }

    public static Bundle getAddTaskBundle(Task task) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TASK, task);
        return bundle;
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_add_task;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        viewModel.showDatePicker.observe(this, new Observer<Calendar>() {
            @Override
            public void onChanged(Calendar calendar) {
                DatePickerDialog dialog = new DatePickerDialog(AddTaskActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                viewModel.selectDate(calendar);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
//                PickerDialog dialog = new PickerDialog();
//                new TimePicker.Builder(AddTaskActivity.this, TimePicker.TYPE_YEAR | TimePicker.TYPE_MONTH | TimePicker.TYPE_DAY, new TimePicker.OnTimeSelectListener() {
//                    @Override
//                    public void onTimeSelect(TimePicker picker, Date date) {
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTime(date);
//                        viewModel.selectDate(calendar);
//                    }
//                }).setRangDate(System.currentTimeMillis(), PickerDialog.getEndTime())
//                        .dialog(dialog)
//                        .setSelectedDate(calendar == null ? System.currentTimeMillis() : calendar.getTimeInMillis())
//                        .setColor(getResources().getColor(R.color.picker_dialog_text_selected_color), getResources().getColor(R.color.picker_dialog_text_color))
//                        .create();
//                dialog.showDialog();
            }
        });
        viewModel.showSelectRemindTimePicker.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                Calendar calendar = Calendar.getInstance();
                TimePickerDialog dialog = new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Date date = new Date();
                        date.setHours(hourOfDay);
                        date.setMinutes(minute);
                        viewModel.selectRemindTime(date);
                    }
                },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true);
                dialog.show();
//                PickerDialog dialog = new PickerDialog();
//                Calendar calendar = Calendar.getInstance();
//                calendar.set(Calendar.HOUR_OF_DAY, 0);
//                calendar.set(Calendar.MINUTE, 0);
//                long start = calendar.getTimeInMillis();
//                calendar.set(Calendar.HOUR_OF_DAY, 23);
//                calendar.set(Calendar.MINUTE, 59);
//                long end = calendar.getTimeInMillis();
//                new TimePicker.Builder(AddTaskActivity.this, TimePicker.TYPE_TIME, new TimePicker.OnTimeSelectListener() {
//                    @Override
//                    public void onTimeSelect(TimePicker picker, Date date) {
//                        viewModel.selectRemindTime(date);
//                    }
//                }).setRangDate(start, end)
//                        .dialog(dialog)
//                        .setSelectedDate(System.currentTimeMillis())
//                        .setColor(getResources().getColor(R.color.picker_dialog_text_selected_color), getResources().getColor(R.color.picker_dialog_text_color))
//                        .create();
//                dialog.showDialog();
            }
        });
        viewModel.showSelectRepeatPicker.observe(this, new Observer<Repeat>() {
            @Override
            public void onChanged(Repeat repeat) {
                SelectRepeatDialog repeatDialog = new SelectRepeatDialog( AddTaskActivity.this,repeat);
                repeatDialog.setOnRepeatTimeSelectedListener(new SelectRepeatDialog.OnRepeatTimeSelectedListener() {
                    @Override
                    public void onRepeatTimeSelected(Repeat repeat) {
                        viewModel.setRepeat(repeat);
                    }
                });
                repeatDialog.show();
            }
        });
        viewModel.backWithTask.observe(this, new Observer<Task>() {
            @Override
            public void onChanged(Task task) {
                Intent intent = new Intent();
                intent.putExtra(TaskViewModel.ADD_TASK, task);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void initData() {
        StatusBarUtil.setTranslucentForImageView(this, 0, null);
        StatusBarUtil.setLightMode(this);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.getRoot().getLayoutParams();
        params.topMargin += ScreenUtils.getStatusHeight(this);
        binding.getRoot().setLayoutParams(params);
        binding.toolbarLayout.rightButton.setText(R.string.save);
        binding.dateSelectGroup.clearCheck();
        Intent intent = getIntent();
        Serializable serializable = intent.getSerializableExtra(CALENDAR);
        if (serializable != null) {
            viewModel.selectDate((Calendar) serializable);
        }
        Serializable taskSerializable = getIntent().getSerializableExtra(TASK);
        if (taskSerializable != null) {
            viewModel.setTask((Task) taskSerializable);
        }
    }
}
