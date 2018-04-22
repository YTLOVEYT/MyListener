package com.example.mylistener.injector.module;

import com.example.mylistener.mvp.contract.ISongContract;
import com.example.mylistener.mvp.presenter.SongPresenter;
import com.example.mylistener.mvp.usecase.GetSongsCase;
import com.example.mylistener.respository.RepositoryImpl;
import com.example.mylistener.respository.interfaces.IRepository;

import dagger.Module;
import dagger.Provides;

/**
 * 歌曲的module,提供presenter和module
 * Created by YinTao on 2018/3/22.
 */
@Module
public class SongModule
{
    /**
     * 提供SongPresenter
     * @param songsCase 传入model
     * @return Presenter接口
     */
    @Provides
    ISongContract.iSongPresenter provideSongPresenter(GetSongsCase songsCase)
    {
        return new SongPresenter(songsCase);
    }

    /**
     * 提供SongCase的model
     * @param repository 传入参数
     * @return Case model接口
     */
    @Provides
    GetSongsCase provideGetSongsCase(IRepository repository)
    {
        return new GetSongsCase(repository);
    }
}
