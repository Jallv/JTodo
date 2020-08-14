package com.jal.core.binding.viewadapter.textview;

import android.graphics.Paint;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class ViewAdapter {
    @BindingAdapter({"android:text"})
    public static void setTextRes(TextView textView, int res) {
        if (res == 0) {
            return;
        }
        textView.setText(res);
    }

    @BindingAdapter({"deleteLine"})
    public static void deleteLine(TextView textView, boolean delete) {
        textView.getPaint().setFlags(delete ? Paint.STRIKE_THRU_TEXT_FLAG : 0);
        textView.invalidate();
    }
}
