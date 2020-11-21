package com.jal.core.viewmodel;


import com.jal.core.mvvm.binding.command.BindingCommand;

public interface IToolbarViewModel {
    String getTitle();

    BindingCommand getLeftButtonClickCommand();

    BindingCommand getRightButtonClickCommand();

    BindingCommand getSearchButtonClickCommand();

    BindingCommand getMenuButtonClickCommand();
}
