package com.jal.todo.viewadapter.countdownview;


import com.jal.todo.widget.CountDownView;

import androidx.databinding.BindingAdapter;

public class ViewAdapter {
    @BindingAdapter({"start"})
    public static void setImageSrc(CountDownView countDownView, boolean start) {
        if (start) {
            countDownView.start();
        } else {
            countDownView.reset();
        }
    }
}
