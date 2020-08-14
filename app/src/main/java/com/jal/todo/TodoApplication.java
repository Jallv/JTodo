package com.jal.todo;

import android.app.Application;

import jal.dev.common.utils.ContextProvider;

public class TodoApplication extends Application {
    private static TodoApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        ContextProvider.init(this);
    }

    public synchronized static TodoApplication getApplication() {
        return mInstance;
    }
}
