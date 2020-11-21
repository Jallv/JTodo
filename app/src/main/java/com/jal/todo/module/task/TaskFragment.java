package com.jal.todo.module.task;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.jal.core.mvvm.base.BaseFragment;
import com.jal.todo.BR;
import com.jal.todo.R;
import com.jal.todo.databinding.FragmentTaskBinding;

import androidx.annotation.Nullable;

public class TaskFragment extends BaseFragment<FragmentTaskBinding, TaskViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_task;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        binding.setAdapter(new TaskRecyclerViewAdapter());
        binding.calendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                viewModel.loadTask(calendar);
            }
        });
        viewModel.loadTask();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        viewModel.onActivityResult(requestCode, resultCode, data);
    }
}
