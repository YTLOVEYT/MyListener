package com.example.mylistener.injector.module;

import android.app.Application;

import com.example.mylistener.ListenerApp;
import com.example.mylistener.injector.anotation.PerApplication;

import dagger.Module;
import dagger.Provides;

/**
 * Application的模块，提供全局的application
 * Created by YinTao on 2018/3/20.
 */

@Module
public class ApplicationModule
{
    private final ListenerApp app;

    public ApplicationModule(ListenerApp app)
    {
        this.app = app;
    }

    @PerApplication
    @Provides
    public ListenerApp provideListenerApp()
    {
        return app;
    }

    @PerApplication
    @Provides
    public Application provideApplication()
    {
        return app;
    }
}
