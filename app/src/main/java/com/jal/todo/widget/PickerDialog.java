package com.jal.todo.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jal.todo.R;

import org.jaaksi.pickerview.dialog.IPickerDialog;
import org.jaaksi.pickerview.dialog.OnPickerChooseListener;
import org.jaaksi.pickerview.picker.BasePicker;

import java.util.Calendar;

import androidx.annotation.CallSuper;

/**
 * 建议直接继承dialog
 */
public class PickerDialog implements IPickerDialog, View.OnClickListener {
    public static final int MAX_YEAR = 2051;

    public static long getEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(MAX_YEAR, 12, 31);
        return calendar.getTimeInMillis();
    }

    /**
     * Canceled dialog OnTouch Outside
     */
    public static boolean sDefaultCanceledOnTouchOutside = true;

    private BasePicker picker;
    private Dialog dialog;
    protected OnPickerChooseListener mOnPickerChooseListener;
    /**
     * Dialog View
     */
    private TextView mBtnCancel, mBtnConfirm, mTvTitle;

    public void setOnPickerChooseListener(OnPickerChooseListener onPickerChooseListener) {
        this.mOnPickerChooseListener = onPickerChooseListener;
    }

    public Dialog getDialog() {
        return dialog;
    }

    /**
     *
     */
    @Override
    public void onCreate(BasePicker picker) {
        this.picker = picker;
        // 初始化Dialog,将pickerContainer添加到dialog跟布局中
        LinearLayout pickerContainer = picker.view();
        Context context = pickerContainer.getContext();

        LinearLayout rootLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_pickerview_custom, null);
        mBtnCancel = rootLayout.findViewById(R.id.btn_cancel);
        mBtnConfirm = rootLayout.findViewById(R.id.btn_confirm);
        mTvTitle = rootLayout.findViewById(R.id.tv_title);

        mBtnCancel.setOnClickListener(this);
        mBtnConfirm.setOnClickListener(this);
        rootLayout.addView(pickerContainer, 1);

        dialog = new Dialog(context, R.style.dialog_pickerview) {
            // 要在onCreate里设置，否则如果style设置了windowIsFloating=true，会变成-2，-2
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                Window window = dialog.getWindow();
                if (window != null) {
                    window.setWindowAnimations(R.style.picker_dialog_anim);
                    // 默认是match_parent的
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                            WindowManager.LayoutParams.WRAP_CONTENT);
                    window.setGravity(Gravity.BOTTOM);
                }
            }
        };
        dialog.setCanceledOnTouchOutside(sDefaultCanceledOnTouchOutside);
        dialog.setContentView(rootLayout);
    }

    @Override
    public void showDialog() {
        dialog.show();
    }

    @CallSuper
    @Override
    public void onClick(View v) {
        if (!picker.canSelected()) return;//  滑动未停止不响应点击事件
        if (v == getBtnConfirm()) {
            // 给用户拦截
            if (mOnPickerChooseListener == null || mOnPickerChooseListener.onConfirm()) {
                // 抛给picker去处理
                dialog.dismiss();
                picker.onConfirm();
            }
        } else if (v == getBtnCancel()) {
            onCancel();
            if (mOnPickerChooseListener != null) {
                mOnPickerChooseListener.onCancel();
            }
        }
    }

    public View getBtnCancel() {
        return mBtnCancel;
    }

    public View getBtnConfirm() {
        return mBtnConfirm;
    }

    public TextView getTitleView() {
        return mTvTitle;
    }

    public void onCancel() {
        dialog.dismiss();
    }
}
