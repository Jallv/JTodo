package com.jal.core.viewmodel;

import me.goldze.mvvmhabit.binding.command.BindingCommand;

public interface IToolbarViewModel {
    String getTitle();

    BindingCommand getLeftButtonClickCommand();

    BindingCommand getRightButtonClickCommand();

    BindingCommand getSearchButtonClickCommand();

    BindingCommand getMenuButtonClickCommand();
}
