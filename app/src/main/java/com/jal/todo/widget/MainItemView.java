package com.jal.todo.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.jal.todo.R;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import me.majiajie.pagerbottomtabstrip.internal.RoundMessageView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;

public class MainItemView extends BaseTabItem {

    private ImageView mIcon;
    private final TextView mTitle;
    private final RoundMessageView mMessages;

    private Drawable mDefaultDrawable;
    private Drawable mCheckedDrawable;

    private int mDefaultTextColor = 0x56000000;
    private int mCheckedTextColor = 0x56000000;

    private boolean mChecked;

    public MainItemView(Context context) {
        this(context, null);
    }

    public MainItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public MainItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.item_main_tab, this, true);

        mIcon = findViewById(R.id.icon);
        mTitle = findViewById(R.id.title);
        mMessages = findViewById(R.id.messages);
        TypedValue typedValue = new TypedValue();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getContext().getTheme()
                    .resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, typedValue, true);
            int[] attribute = new int[]{android.R.attr.selectableItemBackgroundBorderless};
            TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(typedValue.resourceId, attribute);
            setBackground(typedArray.getDrawable(0));
        } else {
            getContext().getTheme()
                    .resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
            int[] attribute = new int[]{android.R.attr.selectableItemBackground};
            TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(typedValue.resourceId, attribute);
            setBackgroundDrawable(typedArray.getDrawable(0));
        }
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return MainItemView.class.getName();
    }

    /**
     * 方便初始化的方法
     *
     * @param drawableRes        默认状态的图标
     * @param checkedDrawableRes 选中状态的图标
     * @param title              标题
     */
    public void initialize(@DrawableRes int drawableRes, @DrawableRes int checkedDrawableRes, String title) {
        mDefaultDrawable = ContextCompat.getDrawable(getContext(), drawableRes);
        mCheckedDrawable = ContextCompat.getDrawable(getContext(), checkedDrawableRes);
        mTitle.setText(title);
    }

    public void initialize(Drawable drawable, Drawable checkedDrawable, String title) {
        mDefaultDrawable = drawable;
        mCheckedDrawable = checkedDrawable;
        mTitle.setText(title);
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked) {
            mIcon.setImageDrawable(mCheckedDrawable);
            mTitle.setTextColor(mCheckedTextColor);
        } else {
            mIcon.setImageDrawable(mDefaultDrawable);
            mTitle.setTextColor(mDefaultTextColor);
        }
        mChecked = checked;
    }

    @Override
    public void setMessageNumber(int number) {
        mMessages.setMessageNumber(number);
    }

    @Override
    public void setHasMessage(boolean hasMessage) {
        mMessages.setHasMessage(hasMessage);
    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void setDefaultDrawable(Drawable drawable) {
        mDefaultDrawable = drawable;
        if (!mChecked) {
            mIcon.setImageDrawable(drawable);
        }
    }

    @Override
    public void setSelectedDrawable(Drawable drawable) {
        mCheckedDrawable = drawable;
        if (mChecked) {
            mIcon.setImageDrawable(drawable);
        }
    }

    @Override
    public String getTitle() {
        return mTitle.getText().toString();
    }

    public void setTextDefaultColor(@ColorInt int color) {
        mDefaultTextColor = color;
    }

    public void setTextCheckedColor(@ColorInt int color) {
        mCheckedTextColor = color;
    }
}
