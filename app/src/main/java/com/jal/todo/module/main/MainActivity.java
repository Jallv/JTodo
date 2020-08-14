package com.jal.todo.module.main;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import jal.dev.common.utils.ScreenUtils;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.internal.Utils;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.jaeger.library.StatusBarUtil;
import com.jal.todo.BR;
import com.jal.todo.R;
import com.jal.todo.TodoApplication;
import com.jal.todo.databinding.ActivityMainBinding;
import com.jal.todo.module.my.MyFragment;
import com.jal.todo.module.record.RecordFragment;
import com.jal.todo.module.task.TaskFragment;
import com.jal.todo.module.timer.TimerFragment;
import com.jal.todo.widget.MainItemView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private NavigationController navigationController;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }

    @Override
    public void initData() {
        super.initData();
        Log.i("classLoader",""+this.getClass().getClassLoader());
        Log.i("classLoader",""+this.getClass().getClassLoader().getParent());
        Log.i("classLoader",""+ TodoApplication.getApplication().getClassLoader());
        StatusBarUtil.setLightMode(this);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.getRoot().getLayoutParams();
        params.topMargin += ScreenUtils.getStatusHeight(this);
        binding.getRoot().setLayoutParams(params);
        navigationController = binding.tab.custom()
                .addItem(newItem(R.drawable.ic_task_editing, getResources().getString(R.string.task)))
                .addItem(newItem(R.drawable.ic_timer, getResources().getString(R.string.tomato_clock)))
                .addItem(newItem(R.drawable.ic_record, getResources().getString(R.string.record)))
                .addItem(newItem(R.drawable.ic_my, getResources().getString(R.string.my)))
                .build();
        navigationController.setupWithViewPager(binding.viewPager);
        binding.viewPager.setOffscreenPageLimit(3);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new TaskFragment());
        fragmentList.add(new TimerFragment());
        fragmentList.add(new RecordFragment());
        fragmentList.add(new MyFragment());
        binding.viewPager.setAdapter(new MainAdapter(getSupportFragmentManager(), fragmentList));
    }

    //创建一个Item
    private BaseTabItem newItem(int drawableRes, String text) {
        MainItemView normalItemView = new MainItemView(this);
        Drawable drawable = ContextCompat.getDrawable(this, drawableRes);
        if (drawable != null) {
            Drawable checkedDrawable = Utils.newDrawable(drawable);
            checkedDrawable = Utils.tinting(checkedDrawable, getResources().getColor(R.color.main_color));
            normalItemView.initialize(drawable, checkedDrawable, text);
            normalItemView.setTextDefaultColor(Color.GRAY);
            normalItemView.setTextCheckedColor(getResources().getColor(R.color.main_color));
        }
        return normalItemView;
    }
}
