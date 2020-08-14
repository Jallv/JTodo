package com.jal.core.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

public class CustomViewModel<M extends BaseModel> extends BaseViewModel<M> implements IToolbarViewModel {
    private CustomUIChangeLiveData customUI;

    public CustomViewModel(@NonNull Application application) {
        super(application);
    }

    public CustomViewModel(@NonNull Application application, M model) {
        super(application, model);
    }

    public void onClick(Object o) {

    }


    private ToolbarViewModel toolbarViewModel;

    public ToolbarViewModel getToolbarViewModel() {
        if (toolbarViewModel == null) {
            toolbarViewModel = new ToolbarViewModel(this);
        }
        return toolbarViewModel;
    }

    @Override
    public String getTitle() {
        return "";
    }

    public CustomUIChangeLiveData getCustomUI() {
        if (customUI == null) {
            customUI = new CustomUIChangeLiveData();
        }
        return customUI;
    }

    public static class CustomUIChangeLiveData {
        private SingleLiveEvent showMenuEvent;
        private SingleLiveEvent showSearchEvent;

        public SingleLiveEvent getShowMenuEvent() {
            showMenuEvent = createLiveData(showMenuEvent);
            return showMenuEvent;
        }

        public SingleLiveEvent getShowSearchEvent() {
            showSearchEvent = createLiveData(showSearchEvent);
            return showSearchEvent;
        }

        private SingleLiveEvent createLiveData(SingleLiveEvent liveData) {
            if (liveData == null) {
                liveData = new SingleLiveEvent();
            }
            return liveData;
        }
    }


    @Override
    public BindingCommand getLeftButtonClickCommand() {
        return new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                onBackPressed();
            }
        });
    }

    @Override
    public BindingCommand getRightButtonClickCommand() {
        return new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                getCustomUI().getShowMenuEvent().call();
            }
        });
    }

    @Override
    public BindingCommand getSearchButtonClickCommand() {
        return new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                getCustomUI().getShowSearchEvent().call();
            }
        });
    }

    @Override
    public BindingCommand getMenuButtonClickCommand() {
        return new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                getCustomUI().getShowMenuEvent().call();
            }
        });
    }
}
