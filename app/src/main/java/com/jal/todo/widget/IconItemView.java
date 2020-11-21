package com.jal.todo.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jal.todo.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;

public class IconItemView extends BaseTabItem {
    private ImageView mIcon;
    private Drawable mDefaultDrawable;
    private Drawable mCheckedDrawable;
    private boolean mChecked;

    public IconItemView(@NonNull Context context) {
        this(context, null);
    }

    public IconItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mIcon = new AppCompatImageView(context);
        mIcon.setScaleType(ImageView.ScaleType.FIT_XY);
        int size = (int) getResources().getDimension(R.dimen.dp_23);
        FrameLayout.LayoutParams params = new LayoutParams(size, size);
        params.gravity = Gravity.CENTER;
        addView(mIcon, params);
//        <AppCompatImageView
//        android:id="@+id/icon"
//        android:layout_width="@dimen/dp_23"
//        android:layout_height="@dimen/dp_23"
//        android:layout_gravity="center_horizontal"
//        android:layout_marginTop="@dimen/dp_8" />
    }

    public void initialize(Drawable drawable, Drawable checkedDrawable, String title) {
        mDefaultDrawable = drawable;
        mCheckedDrawable = checkedDrawable;
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked) {
            mIcon.setImageDrawable(mCheckedDrawable);
        } else {
            mIcon.setImageDrawable(mDefaultDrawable);
        }
        mChecked = checked;
    }

    @Override
    public void setMessageNumber(int number) {

    }

    @Override
    public void setHasMessage(boolean hasMessage) {

    }

    @Override
    public void setTitle(String title) {

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
        return null;
    }
}
