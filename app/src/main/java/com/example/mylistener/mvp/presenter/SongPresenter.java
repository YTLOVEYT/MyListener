package com.example.mylistener.mvp.presenter;

import com.example.mylistener.entity.Song;
import com.example.mylistener.mvp.contract.ISongContract;
import com.example.mylistener.mvp.usecase.GetSongsCase;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 歌曲的presenter 实现presenter和view
 * Created by YinTao on 2018/3/21.
 */

public class SongPresenter implements ISongContract.iSongPresenter
{
    private static final String TAG = "SongPresenter";
    private GetSongsCase songsCase;//model
    private ISongContract.iSongView view;//view
    private CompositeSubscription subscription;//请求管理

    public SongPresenter(GetSongsCase songsCase)
    {
        this.songsCase = songsCase;
    }

    /** 分发视图 */
    @Override
    public void attachView(ISongContract.iSongView view)
    {
        this.view = view;
        subscription = new CompositeSubscription();
    }

    @Override
    public void subscribe()
    {
        // FIXME: 2018/3/22 签署
    }

    @Override
    public void unSubscribe()
    {
        subscription.clear();
    }

    @Override
    public void loadSong(String action)
    {
        subscription.clear();
        Subscription subscribe = songsCase.execute(new GetSongsCase.RequestValue(action))
                .getSongList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ArrayList<Song>>()
                {
                    @Override
                    public void call(ArrayList<Song> songs)
                    {
                        if (songs == null || songs.size() == 0)
                        {
                            view.showEmptyView();
                        }
                        else
                        {
                            view.showSongs(songs);
                        }
                    }
                });
        subscription.add(subscribe);
    }
}
