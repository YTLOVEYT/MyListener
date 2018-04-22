package com.example.mylistener.mvp.contract;

import com.example.mylistener.entity.Song;
import com.example.mylistener.mvp.presenter.IBasePresenter;
import com.example.mylistener.mvp.view.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * SongContact(SongView+SongPresenter)
 * Created by YinTao on 2018/3/21.
 */

public interface ISongContract
{
    interface iSongView extends BaseView
    {
        /** 显示歌曲 */
        void showSongs(ArrayList<Song> songs);

        /** 显示空页面 */
        void showEmptyView();
    }

    interface iSongPresenter extends IBasePresenter<iSongView>
    {
        /** 加载歌曲 */
        void loadSong(String action);
    }
}
