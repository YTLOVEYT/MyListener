package com.example.mylistener;

import android.app.Application;

import com.example.mylistener.injector.component.DaggerIApplicationComponent;
import com.example.mylistener.injector.component.IApplicationComponent;
import com.example.mylistener.injector.module.ApplicationModule;
import com.example.mylistener.injector.module.NetworkModule;
import com.example.mylistener.utils.LogUtil;

/**
 * 全局App
 * Created by YinTao on 2018/3/7.
 */

public class ListenerApp extends Application
{
    private IApplicationComponent iApplicationComponent;

    @Override
    public void onCreate()
    {
        super.onCreate();
        setupInjector();//dagger注入器
        // FIXME: 2018/3/21 还有事情做哦
        LogUtil.initLog(true, "----", false);

    }

    /** dagger注入器 */
    private void setupInjector()
    {
        iApplicationComponent = DaggerIApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule(this))
                .build();
    }

    public IApplicationComponent getIApplicationComponent()
    {
        return iApplicationComponent;
    }
}
