package com.example.mylistener.injector.component;

import com.example.mylistener.injector.ano.PerActivity;
import com.example.mylistener.injector.module.ActivityModule;
import com.example.mylistener.injector.module.SongModule;
import com.example.mylistener.ui.fragment.SongsFragment;

import dagger.Component;

/**
 * 歌曲的Component，依赖父类activity
 * Created by YinTao on 2018/3/22.
 */
@PerActivity
@Component(dependencies = {IApplicationComponent.class}, modules = {SongModule.class, ActivityModule.class})
public interface ISongComponent
{
    /**
     * 注入地点 songsFragment
     * @param songsFragment 地点
     */
    void inject(SongsFragment songsFragment);
}
