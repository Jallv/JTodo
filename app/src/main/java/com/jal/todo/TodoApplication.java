package com.jal.todo;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
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
