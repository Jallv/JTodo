package jal.dev.common.utils;

import android.content.Context;

public class ContextProvider {
    private static Context context;

    public static void init(Context context) {
        ContextProvider.context = context;
    }

    public static Context getContext() {
        return context;
    }
}
