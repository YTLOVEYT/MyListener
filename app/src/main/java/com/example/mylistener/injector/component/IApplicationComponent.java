package com.example.mylistener.injector.component;

import android.app.Application;

import com.example.mylistener.ListenerApp;
import com.example.mylistener.injector.anotation.PerApplication;
import com.example.mylistener.injector.module.ApplicationModule;
import com.example.mylistener.injector.module.NetworkModule;
import com.example.mylistener.respository.interfaces.IRepository;

import dagger.Component;

/**
 * application组件
 * Created by YinTao on 2018/3/20.
 */
@PerApplication
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface IApplicationComponent
{
    Application application();

    ListenerApp listenerApplication();

    IRepository repository();
}
