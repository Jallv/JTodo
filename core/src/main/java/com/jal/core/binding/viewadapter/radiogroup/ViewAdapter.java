package com.jal.core.binding.viewadapter.radiogroup;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.databinding.BindingAdapter;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class ViewAdapter {
    @BindingAdapter(value = "onCheckedChangeCommand")
    public static void setOnCheckedChangeListener(RadioGroup radioGroup, final BindingCommand<Integer> command) {
        if (command != null) {
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton radioButton = group.findViewById(checkedId);
                    if (radioButton != null && radioButton.isChecked()) {
                        command.execute(checkedId);
                    }
                }
            });
        }
    }

    @BindingAdapter(value = "checkChild")
    public static void checkChild(RadioGroup radioGroup, Integer id) {
        if (id == -1) {
            radioGroup.clearCheck();
        } else {
            radioGroup.check(id);
        }
    }
}
