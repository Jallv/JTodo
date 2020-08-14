package com.jal.todo.module.task;

import android.annotation.SuppressLint;
import android.app.Application;

import com.haibin.calendarview.Calendar;
import com.jal.core.viewmodel.CustomViewModel;
import com.jal.todo.R;
import com.jal.todo.db.AppDatabase;
import com.jal.todo.db.entity.Task;
import com.jal.todo.module.addtask.AddTaskActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableList;
import io.reactivex.functions.Consumer;
import jal.dev.common.utils.DateUtil;
import jal.dev.common.utils.ResourcesUtil;
import jal.dev.common.utils.rxjava.CommonRxTask;
import jal.dev.common.utils.rxjava.RxAdapter;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class TaskViewModel extends CustomViewModel {
    public ObservableBoolean showEmpty = new ObservableBoolean(false);

    public TaskViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableList<TaskItemViewModel> observableList = new ObservableArrayList<>();
    public ItemBinding<TaskItemViewModel> itemBinding = ItemBinding.of(com.jal.todo.BR.viewModel, R.layout.item_task);
    public BindingCommand addTaskCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(AddTaskActivity.class, AddTaskActivity.getAddTaskBundle(currentCalendar));
        }
    });

    @Override
    public String getTitle() {
        return ResourcesUtil.getString(R.string.task);
    }

    private java.util.Calendar currentCalendar;

    public void loadTask() {
        currentCalendar = java.util.Calendar.getInstance();
        loadTask(DateUtil.formatDate(currentCalendar.getTime(), DateUtil.FormatType.yyyyMMdd));
    }

    public void loadTask(Calendar calendar) {
        currentCalendar = java.util.Calendar.getInstance();
        currentCalendar.set(calendar.getYear(), calendar.getMonth() - 1, calendar.getDay());
        loadTask(DateUtil.formatLong(calendar.getTimeInMillis(), DateUtil.FormatType.yyyyMMdd));
    }

    public void onTaskResult(Task task) {

    }

    @SuppressLint("CheckResult")
    private void loadTask(String date) {
        AppDatabase.getInstance(getApplication()).taskDao().getTaskByDate(date)
                .compose(RxAdapter.<List<Task>>bindUntilFragmentEvent(getLifecycleProvider()))
                .compose(RxAdapter.<List<Task>>schedulersSingleTransformer())
                .subscribe(new Consumer<List<Task>>() {
                    @Override
                    public void accept(List<Task> tasks) {
                        if (tasks == null || tasks.size() == 0) {
                            showEmpty.set(true);
                            observableList.clear();
                            return;
                        }
                        observableList.clear();
                        for (Task task : tasks) {
                            observableList.add(new TaskItemViewModel(TaskViewModel.this, task));
                        }
                        showEmpty.set(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.printStackTrace();
                        showEmpty.set(true);
                    }
                });
    }

    public void deleteTask(Task task) {
        RxAdapter.executeRxTask(new CommonRxTask<Task>(task) {
            @Override
            public void doInIOThread() {
                AppDatabase.getInstance(getApplication()).taskDao().delete(t);
            }
        }, RxAdapter.<CommonRxTask<Task>>bindUntilFragmentEvent(getLifecycleProvider()));
    }

    public void onItemClick(Task task) {
        startActivity(AddTaskActivity.class, AddTaskActivity.getAddTaskBundle(task));
    }

    public void saveTask(final Task task) {
        RxAdapter.executeRxTask(new CommonRxTask<Task>(task) {
            @Override
            public void doInIOThread() {
                AppDatabase.getInstance(getApplication()).taskDao().insert(task);
            }
        }, RxAdapter.<CommonRxTask<Task>>bindUntilFragmentEvent(getLifecycleProvider()));
    }

    public void updateTask(Task task, boolean isChecked) {
        if (task != null) {
            task.isCompleted = isChecked;
            if (isChecked && task.subTaskList != null && task.subTaskList.size() > 0) {
                for (Task subTask : task.subTaskList) {
                    subTask.isCompleted = true;
                }
            }
            saveTask(task);
        }
    }
}
