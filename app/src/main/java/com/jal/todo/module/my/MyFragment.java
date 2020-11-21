package com.jal.todo.module.my;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jal.core.mvvm.base.BaseFragment;
import com.jal.todo.BR;
import com.jal.todo.R;
import com.jal.todo.databinding.FragmentMyBinding;

import androidx.annotation.Nullable;

public class MyFragment extends BaseFragment<FragmentMyBinding,MyViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_my;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
