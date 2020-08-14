package com.jal.core.binding.viewadapter.view;

import android.view.View;

import androidx.databinding.BindingAdapter;

public class ViewAdapter {
    @BindingAdapter(value = {"isSelected"}, requireAll = false)
    public static void isSelected(View view, final Boolean isSelected) {
        view.setSelected(isSelected);
    }
}
