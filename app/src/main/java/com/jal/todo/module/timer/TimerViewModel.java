package com.jal.todo.module.timer;

import android.app.Application;

import com.jal.core.mvvm.base.BaseViewModel;
import com.jal.core.mvvm.binding.command.BindingAction;
import com.jal.core.mvvm.binding.command.BindingCommand;
import com.jal.core.viewmodel.CustomViewModel;
import com.jal.todo.R;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import jal.dev.common.utils.ResourcesUtil;

public class TimerViewModel extends CustomViewModel {
    public ObservableBoolean startTime = new ObservableBoolean();
    public ObservableField<String> buttonText = new ObservableField<>(ResourcesUtil.getString(R.string.start_focus));

    public TimerViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public String getTitle() {
        return ResourcesUtil.getString(R.string.tomato_clock);
    }

    public BindingCommand recordCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(FocusActivity.class);
        }
    });
    public BindingCommand startCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startTime.set(!startTime.get());
            buttonText.set(ResourcesUtil.getString(startTime.get() ? R.string.give_up_focus : R.string.start_focus));
        }
    });
}
