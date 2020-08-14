package com.jal.todo.module.addtask;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jal.todo.R;

import androidx.annotation.Nullable;

public class SubTaskView extends LinearLayout implements CompoundButton.OnCheckedChangeListener {
    private CheckBox checkBox;
    private TextView titleText;
    private int flags;

    public SubTaskView(Context context) {
        this(context, null);
    }

    public SubTaskView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SubTaskView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.item_sub_task, this, true);
        checkBox = findViewById(R.id.subtask_check_box);
        titleText = findViewById(R.id.subtask_content);
        checkBox.setOnCheckedChangeListener(this);
        flags = titleText.getPaint().getFlags();
    }

    public void setChecked(boolean checked) {
        checkBox.setChecked(checked);
        titleText.getPaint().setFlags(checked ? Paint.STRIKE_THRU_TEXT_FLAG : flags);
        titleText.invalidate();
    }

    private CompoundButton.OnCheckedChangeListener changeListener;

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        changeListener = listener;
    }

    public boolean isChecked() {
        return checkBox.isChecked();
    }

    public void setTitleText(String text) {
        titleText.setText(text);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (changeListener != null) {
            changeListener.onCheckedChanged(buttonView, isChecked);
        }
        titleText.getPaint().setFlags(isChecked ? Paint.STRIKE_THRU_TEXT_FLAG : flags);
        titleText.invalidate();
    }
}
