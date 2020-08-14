package com.jal.todo.module.task;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.haibin.calendarview.WeekViewPager;
import com.jal.todo.R;
import com.jal.todo.databinding.FragmentRecordBinding;
import com.jal.todo.databinding.FragmentTaskBinding;
import com.jal.todo.module.record.RecordViewModel;

import androidx.annotation.Nullable;
import jal.dev.common.utils.DateUtil;
import jal.dev.common.utils.ToastUtil;
import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseFragment;

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
    }
}
