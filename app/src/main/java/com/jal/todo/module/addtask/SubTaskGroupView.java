package com.jal.todo.module.addtask;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.jal.core.mvvm.binding.command.BindingCommand;
import com.jal.todo.db.entity.Task;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableList;

public class SubTaskGroupView extends LinearLayout {
    public SubTaskGroupView(Context context) {
        super(context);
        init(context);
    }

    public SubTaskGroupView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SubTaskGroupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
    }

    private ObservableList<Task> mSubTaskList;

    public void setSubTaskList(ObservableList<Task> list) {
        if (list == null) {
            return;
        }
        if (mSubTaskList != null) {
            mSubTaskList.removeOnListChangedCallback(onListChangedCallback);
        }
        mSubTaskList = list;
        mSubTaskList.addOnListChangedCallback(onListChangedCallback);
        removeAllViews();
        for (Task task : list) {
            addTaskView(task);
        }
    }

    private void addTaskView(final Task task) {
        SubTaskView subTaskView = new SubTaskView(getContext());
        subTaskView.setChecked(task.isCompleted);
        subTaskView.setTitleText(task.content);
        subTaskView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                task.isCompleted = isChecked;
                if (onTaskCheckChangeListener != null) {
                    onTaskCheckChangeListener.onTaskCheckedChange(task);
                }
            }
        });
        addView(subTaskView);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mSubTaskList != null) {
            mSubTaskList.removeOnListChangedCallback(onListChangedCallback);
        }
    }

    private ObservableList.OnListChangedCallback<ObservableList<Task>> onListChangedCallback = new ObservableList.OnListChangedCallback<ObservableList<Task>>() {
        @Override
        public void onChanged(ObservableList<Task> sender) {

        }

        @Override
        public void onItemRangeChanged(ObservableList<Task> sender, int positionStart, int itemCount) {
            for (int i = positionStart; i < positionStart + itemCount; i++) {
                View child = getChildAt(i);
                if (child == null) {
                    continue;
                }
                SubTaskView subTaskView = (SubTaskView) child;
                subTaskView.setChecked(sender.get(i).isCompleted);
                subTaskView.setTitleText(sender.get(i).content);
            }
        }

        @Override
        public void onItemRangeInserted(ObservableList<Task> sender, int positionStart, int itemCount) {
            addTaskView(sender.get(positionStart));
        }

        @Override
        public void onItemRangeMoved(ObservableList<Task> sender, int fromPosition, int toPosition, int itemCount) {

        }

        @Override
        public void onItemRangeRemoved(ObservableList<Task> sender, int positionStart, int itemCount) {

        }
    };
    private OnTaskCheckChangeListener onTaskCheckChangeListener;

    public void setOnTaskCheckChangeListener(OnTaskCheckChangeListener listener) {
        onTaskCheckChangeListener = listener;
    }

    public interface OnTaskCheckChangeListener {
        void onTaskCheckedChange(Task task);
    }

    @BindingAdapter({"subTaskList"})
    public static void setSubTaskList(SubTaskGroupView view, ObservableList<Task> list) {
        view.setSubTaskList(list);
    }

    @BindingAdapter({"onTaskCheckChange"})
    public static void setOnTaskCheckChangeListener(SubTaskGroupView view, final BindingCommand<Task> bindingCommand) {
        view.setOnTaskCheckChangeListener(new OnTaskCheckChangeListener() {
            @Override
            public void onTaskCheckedChange(Task task) {
                bindingCommand.execute(task);
            }
        });
    }
}
