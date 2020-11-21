package com.jal.todo.widget;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.jal.todo.R;
import com.jal.todo.bean.Repeat;
import com.jal.todo.databinding.DialogSelectRepeatBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

public class SelectRepeatDialog extends AlertDialog {
    private DialogSelectRepeatBinding binding;
    private Repeat repeat;

    public SelectRepeatDialog(@NonNull Context context, Repeat repeat) {
        super(context);
        this.repeat = repeat;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_select_repeat, null, false);
        setContentView(binding.getRoot());
        Window window = getWindow();
        if (window != null) {
            // 默认是match_parent的
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
        }
        binding.everyWeekRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.checkboxGroup.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        binding.sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repeat repeat = null;
                if (binding.everyDayRadio.isChecked()) {
                    repeat = new Repeat(Repeat.REPEAT_EVERY_DAY);
                } else if (binding.everyWeekRadio.isChecked()) {
                    repeat = new Repeat(Repeat.REPEAT_EVERY_WEEK);
                    CheckBox[] checkBoxList = new CheckBox[]{binding.checkbox1, binding.checkbox2, binding.checkbox3, binding.checkbox4, binding.checkbox5, binding.checkbox6, binding.checkbox7};
                    List<Integer> weekList = new ArrayList<>();
                    for (int i = 0; i < checkBoxList.length; i++) {
                        if (checkBoxList[i].isChecked()) {
                            weekList.add(i + 1);
                        }
                    }
                    if (weekList.isEmpty()) {
                        weekList = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
                    }
                    repeat.setWeek(weekList);
                }
                if (listener != null) {
                    listener.onRepeatTimeSelected(repeat);
                }
                dismiss();
            }
        });
        if (repeat == null) {
            return;
        }
        binding.everyWeekRadio.setChecked(repeat.getRepeat() == Repeat.REPEAT_EVERY_WEEK);
        binding.everyDayRadio.setChecked(repeat.getRepeat() == Repeat.REPEAT_EVERY_DAY);
        if (repeat.getWeek() != null && repeat.getWeek().size() > 0) {
            CheckBox[] checkBoxList = new CheckBox[]{binding.checkbox1, binding.checkbox2, binding.checkbox3, binding.checkbox4, binding.checkbox5, binding.checkbox6, binding.checkbox7};
            for (int week : repeat.getWeek()) {
                checkBoxList[week - 1].setChecked(true);
            }
        }
    }


    private OnRepeatTimeSelectedListener listener;

    public void setOnRepeatTimeSelectedListener(OnRepeatTimeSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnRepeatTimeSelectedListener {
        void onRepeatTimeSelected(Repeat repeat);
    }
}
