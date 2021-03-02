package com.jal.todo.module.task;

import android.view.View;

import com.jal.core.mvvm.base.ItemViewModel;
import com.jal.core.mvvm.binding.command.BindingAction;
import com.jal.core.mvvm.binding.command.BindingCommand;
import com.jal.core.mvvm.binding.command.BindingConsumer;
import com.jal.todo.MyObservableArrayList;
import com.jal.todo.bean.Repeat;
import com.jal.todo.db.entity.Task;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

public class TaskItemViewModel extends ItemViewModel<TaskViewModel> {
    public ObservableField<Task> taskObservable;
    public MyObservableArrayList<Task> subTaskObservable;
    public ObservableBoolean deleteLine;
    public ObservableField<Repeat> repeatObservable;

    public TaskItemViewModel(@NonNull TaskViewModel viewModel, Task task) {
        super(viewModel);
        taskObservable = new ObservableField<>(task);
        repeatObservable = new ObservableField<>(new Repeat(task));
        if (task.subTaskList != null) {
            subTaskObservable = new MyObservableArrayList<>();
            subTaskObservable.addAll(task.subTaskList);
        }
        deleteLine = new ObservableBoolean(task.isCompleted);
    }

    public void setTask(Task task) {
        taskObservable.set(task);
        taskObservable.notifyChange();
        repeatObservable.set(new Repeat(task));
        if (task.subTaskList != null) {
            if (subTaskObservable == null) {
                subTaskObservable = new MyObservableArrayList<>();
            }
            subTaskObservable.clear();
            subTaskObservable.addAll(task.subTaskList);
        }
        deleteLine.set(task.isCompleted);
    }

    public BindingCommand itemLongClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.deleteTask(taskObservable.get());
        }
    });
    public BindingCommand<Boolean> onCheckedChangedCommand = new BindingCommand<>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean aBoolean) {
            Task task = taskObservable.get();
            if (task == null) {
                return;
            }
            task.isCompleted = aBoolean;
            if (aBoolean && subTaskObservable != null && subTaskObservable.size() > 0) {
                for (Task subTask : subTaskObservable) {
                    subTask.isCompleted = true;
                }
                subTaskObservable.notifyChanged(0, subTaskObservable.size());
            }
            viewModel.saveTask(task);
            deleteLine.set(task.isCompleted);
        }
    });
    public BindingCommand<View> itemClickCommand = new BindingCommand<>(new BindingConsumer<View>() {
        @Override
        public void call(View view) {
            viewModel.onItemClick(taskObservable.get(), view);
        }
    });

    public BindingCommand<Task> onTaskCheckChange = new BindingCommand<>(new BindingConsumer<Task>() {
        @Override
        public void call(Task task) {
            viewModel.saveTask(taskObservable.get());
        }
    });
}
