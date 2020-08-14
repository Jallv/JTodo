package com.jal.todo.module.main;

import android.app.Application;

import com.tbruyelle.rxpermissions2.RxPermissions;

import androidx.annotation.NonNull;
import me.goldze.mvvmhabit.base.BaseViewModel;

public class MainViewModel extends BaseViewModel {
    public MainViewModel(@NonNull Application application) {
        super(application);
    }
}
