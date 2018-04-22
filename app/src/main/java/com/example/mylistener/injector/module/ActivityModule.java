package com.example.mylistener.injector.module;

import android.app.Activity;

import com.example.mylistener.injector.ano.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * ActivityModule
 * Created by YinTao on 2018/3/22.
 */
@Module
public class ActivityModule
{
    private final Activity activity;

    public ActivityModule(Activity activity)
    {
        this.activity = activity;
    }

    @PerActivity
    @Provides
    public Activity provideActivity()
    {
        return activity;
    }
}
