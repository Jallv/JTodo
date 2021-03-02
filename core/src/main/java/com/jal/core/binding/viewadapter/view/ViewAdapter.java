package com.jal.core.binding.viewadapter.view;

import android.view.View;

import com.jal.core.mvvm.binding.command.BindingCommand;

import androidx.databinding.BindingAdapter;

public class ViewAdapter {
    @BindingAdapter(value = {"isSelected"}, requireAll = false)
    public static void isSelected(View view, final Boolean isSelected) {
        view.setSelected(isSelected);
    }

    @BindingAdapter(value = {"onClickCommondWithView"}, requireAll = false)
    public static void onClickCommondWithView(View view, final BindingCommand<View> command) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                command.execute(v);
            }
        });
    }
}
