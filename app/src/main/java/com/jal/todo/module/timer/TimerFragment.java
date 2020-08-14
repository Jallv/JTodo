package com.jal.todo.module.timer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jal.todo.R;
import com.jal.todo.databinding.FragmentTaskBinding;
import com.jal.todo.databinding.FragmentTimerBinding;
import com.jal.todo.module.task.TaskViewModel;

import androidx.annotation.Nullable;
import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseFragment;

public class TimerFragment extends BaseFragment<FragmentTimerBinding, TimerViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_timer;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
