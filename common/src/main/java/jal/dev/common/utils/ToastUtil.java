package jal.dev.common.utils;

import android.widget.Toast;


/**
 * Description: <吐司工具类><br>
 * Author: mxdl<br>
 * Date: 2018/6/11<br>
 * Version: V1.0.0<br>
 * Update: <br>
 */
public class ToastUtil {

    public static void showToast(String message) {
        Toast.makeText(ContextProvider.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(int resid) {
        Toast.makeText(ContextProvider.getContext(), ContextProvider.getContext().getString(resid), Toast.LENGTH_SHORT)
                .show();
    }
}