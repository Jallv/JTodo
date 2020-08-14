package com.jal.todo.module.record;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jal.todo.R;
import com.jal.todo.databinding.FragmentMyBinding;
import com.jal.todo.databinding.FragmentRecordBinding;

import androidx.annotation.Nullable;
import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseFragment;

public class RecordFragment extends BaseFragment<FragmentRecordBinding, RecordViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_record;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
