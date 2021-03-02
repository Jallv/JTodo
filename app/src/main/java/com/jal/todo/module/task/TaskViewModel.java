package com.jal.todo.module.task;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.view.View;

import com.haibin.calendarview.Calendar;
import com.jal.core.mvvm.binding.command.BindingAction;
import com.jal.core.mvvm.binding.command.BindingCommand;
import com.jal.core.viewmodel.CustomViewModel;
import com.jal.todo.BR;
import com.jal.todo.R;
import com.jal.todo.db.AppDatabase;
import com.jal.todo.db.entity.Task;
import com.jal.todo.module.addtask.AddTaskActivity;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableList;
import io.reactivex.functions.Consumer;
import jal.dev.common.utils.DateUtil;
import jal.dev.common.utils.ResourcesUtil;
import jal.dev.common.utils.rxjava.CommonRxTask;
import jal.dev.common.utils.rxjava.RxAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class TaskViewModel extends CustomViewModel {
    private final static int ADD_REQUEST_CODE = 1001;
    public final static String ADD_TASK = "Task";
    public ObservableBoolean showEmpty = new ObservableBoolean(false);

    public TaskViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableList<TaskItemViewModel> observableList = new ObservableArrayList<>();
    public ItemBinding<TaskItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_task);
    public BindingCommand addTaskCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(AddTaskActivity.class, AddTaskActivity.getAddTaskBundle(currentCalendar), ADD_REQUEST_CODE);
        }
    });

    @Override
    public String getTitle() {
        return ResourcesUtil.getString(R.string.task);
    }

    private java.util.Calendar currentCalendar;

    public void loadTask() {
        currentCalendar = java.util.Calendar.getInstance();
        loadTask(DateUtil.formatDate(currentCalendar.getTime(), DateUtil.FormatType.yyyyMMdd), currentCalendar.get(java.util.Calendar.DAY_OF_WEEK));
    }

    public void loadTask(Calendar calendar) {
        currentCalendar = java.util.Calendar.getInstance();
        currentCalendar.set(calendar.getYear(), calendar.getMonth() - 1, calendar.getDay());
        loadTask(DateUtil.formatLong(calendar.getTimeInMillis(), DateUtil.FormatType.yyyyMMdd), currentCalendar.get(java.util.Calendar.DAY_OF_WEEK));
    }

    @SuppressLint("CheckResult")
    private void loadTask(String date, int week) {
        AppDatabase.getInstance(getApplication()).taskDao().getTaskByDate(date, week)
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

            @Override
            public void doInUIThread() {
                removeTask(t);
            }
        }, RxAdapter.<CommonRxTask<Task>>bindUntilFragmentEvent(getLifecycleProvider()));
    }

    public void onItemClick(Task task, View view) {
        startActivity(AddTaskActivity.class, view, AddTaskActivity.getAddTaskBundle(task), ADD_REQUEST_CODE);
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

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ADD_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            Serializable serializable = data.getSerializableExtra(ADD_TASK);
            if (serializable == null) {
                return;
            }
            Task task = (Task) serializable;
            if (!task.date.equals(DateUtil.formatDate(currentCalendar.getTime(), DateUtil.FormatType.yyyyMMdd))) {
                return;
            }
            addTask(task);
            showEmpty.set(false);
        }
    }

    private void addTask(Task task) {
        for (TaskItemViewModel itemViewModel : observableList) {
            if (task.equals(itemViewModel.taskObservable.get())) {
                itemViewModel.setTask(task);
                return;
            }
        }
        observableList.add(0, new TaskItemViewModel(this, task));
    }

    private void removeTask(Task task) {
        Iterator<TaskItemViewModel> iterator = observableList.iterator();
        while (iterator.hasNext()) {
            TaskItemViewModel itemViewModel = iterator.next();
            if (task.equals(itemViewModel.taskObservable.get())) {
                iterator.remove();
                break;
            }
        }
        if (observableList.size() == 0) {
            showEmpty.set(true);
        }
    }
}
