package com.jal.todo.module.task;

import com.jal.todo.MyObservableArrayList;
import com.jal.todo.db.entity.Task;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import me.goldze.mvvmhabit.base.ItemViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;

public class TaskItemViewModel extends ItemViewModel<TaskViewModel> {
    public ObservableField<Task> taskObservable;
    public MyObservableArrayList<Task> subTaskObservable;
    public ObservableBoolean deleteLine;

    public TaskItemViewModel(@NonNull TaskViewModel viewModel, Task task) {
        super(viewModel);
        taskObservable = new ObservableField<>(task);
        if (task.subTaskList != null) {
            subTaskObservable = new MyObservableArrayList<>();
            subTaskObservable.addAll(task.subTaskList);
        }
        deleteLine = new ObservableBoolean(task.isCompleted);
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
    public BindingCommand itemClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            viewModel.onItemClick(taskObservable.get());
        }
    });

    public BindingCommand<Task> onTaskCheckChange = new BindingCommand<>(new BindingConsumer<Task>() {
        @Override
        public void call(Task task) {
            viewModel.saveTask(taskObservable.get());
        }
    });
}
