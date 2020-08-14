package com.jal.core.binding.viewadapter.image;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class ViewAdapter {
    @BindingAdapter({"android:src"})
    public static void setImageSrc(ImageView imageView, int res) {
        if (res == 0) {
            return;
        }
        imageView.setImageResource(res);
    }
}
