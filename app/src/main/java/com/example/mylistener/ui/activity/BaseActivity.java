package com.example.mylistener.ui.activity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.appthemeengine.ATEActivity;
import com.example.mylistener.R;
import com.example.mylistener.ui.fragment.QuickControlFragment;

/**
 * 父类Activity
 * Created by YinTao on 2018/3/7.
 */
public class BaseActivity extends ATEActivity
{
    @Nullable
    @Override
    protected String getATEKey()
    {
        // FIXME: 2018/3/12 重写父类的一个方法，常量控制
        return "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // FIXME: 2018/3/8 绑定音乐的服务

        if (Build.VERSION.SDK_INT >= 21)
        {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


    }

    @Override
    protected void onStart()
    {
        super.onStart();
        // FIXME: 2018/3/8 注册广播

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // FIXME: 2018/3/12 有一个menu需要去掉
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                super.onBackPressed();//调用父类
                return true;
            case R.id.action_equalizer:
                // FIXME: 2018/3/12 跳转页面
                return true;
            case R.id.action_settings:
                // FIXME: 2018/3/12 跳转页面
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        // FIXME: 2018/3/12 unbind音乐服务

        // FIXME: 2018/3/12 取消注册广播
    }

    // FIXME: 2018/3/12 一个播放状态的内部类

    /** 异步加载底部控制栏 */
    class initQuickControl extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            QuickControlFragment quickControlFragment = new QuickControlFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.quickControl_container, quickControlFragment).commitAllowingStateLoss();
            return "executed";
        }
    }
}
