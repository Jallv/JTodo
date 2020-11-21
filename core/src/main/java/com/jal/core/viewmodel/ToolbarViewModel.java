package com.jal.core.viewmodel;

import com.jal.core.mvvm.binding.command.BindingCommand;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

public class ToolbarViewModel extends ViewModel {
    private IToolbarViewModel iToolbarViewModel;

    public ToolbarViewModel(IToolbarViewModel iToolbarViewModel) {
        this.iToolbarViewModel = iToolbarViewModel;
    }

    private ObservableField<String> title;
    private BindingCommand leftButtonClickCommand, rightButtonClickCommand, searchButtonClickCommand;

    public ObservableField<String> getTitle() {
        if (title == null) {
            title = new ObservableField<>(iToolbarViewModel.getTitle());
        }
        return title;
    }

    public BindingCommand getLeftButtonClickCommand() {
        if (leftButtonClickCommand == null) {
            leftButtonClickCommand = iToolbarViewModel.getLeftButtonClickCommand();
        }
        return leftButtonClickCommand;
    }

    public BindingCommand getRightButtonClickCommand() {
        if (rightButtonClickCommand == null) {
            rightButtonClickCommand = iToolbarViewModel.getRightButtonClickCommand();
        }
        return rightButtonClickCommand;
    }

    public BindingCommand getSearchButtonClickCommand() {
        if (searchButtonClickCommand == null) {
            searchButtonClickCommand = iToolbarViewModel.getSearchButtonClickCommand();
        }
        return searchButtonClickCommand;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
