package com.jal.todo.module.timer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jal.core.mvvm.base.BaseFragment;
import com.jal.todo.BR;
import com.jal.todo.R;
import com.jal.todo.databinding.FragmentTaskBinding;
import com.jal.todo.databinding.FragmentTimerBinding;
import com.jal.todo.module.task.TaskViewModel;

import androidx.annotation.Nullable;

public class TimerFragment extends BaseFragment<FragmentTimerBinding, TimerViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_timer;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        binding.countDownView.setTime(5 * 60 * 1000);
    }
}
