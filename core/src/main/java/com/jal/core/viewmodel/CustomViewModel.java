package com.jal.core.viewmodel;

import android.app.Application;

import com.jal.core.mvvm.base.BaseModel;
import com.jal.core.mvvm.base.BaseViewModel;
import com.jal.core.mvvm.binding.command.BindingAction;
import com.jal.core.mvvm.binding.command.BindingCommand;
import com.jal.core.mvvm.event.SingleLiveEvent;

import androidx.annotation.NonNull;

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
