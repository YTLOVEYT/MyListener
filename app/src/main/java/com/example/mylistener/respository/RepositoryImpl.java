package com.example.mylistener.respository;

import android.content.Context;

import com.example.mylistener.dao.loader.SongLoader;
import com.example.mylistener.entity.Song;
import com.example.mylistener.respository.interfaces.IRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * 资源实现 向数据库插叙数据的地址
 * Created by YinTao on 2018/3/21.
 */
public class RepositoryImpl implements IRepository
{
    private Context mContext;

    public RepositoryImpl(Context context)
    {
        // FIXME: 2018/3/26
        mContext = context;
    }

    @Override
    public Observable getArtistInfo(String artist)
    {
        return null;
    }

    @Override
    public Observable<File> getLrcFile(String title, String artist, long duration)
    {
        return null;
    }

    @Override
    public Observable<ArrayList<Song>> getAllSongs()
    {
        Observable<ArrayList<Song>> allSongs = SongLoader.getAllSongs(mContext);
        return  allSongs;
    }

    @Override
    public Observable<List<Song>> getFavoriteSongs()
    {
        return null;
    }

    @Override
    public Observable<List<Song>> getRecentlyAddedSongs()
    {
        return null;
    }

    @Override
    public Observable<List<Song>> getRecentlyPlayedSongs()
    {
        return null;
    }

    @Override
    public Observable<List<Song>> getTopPlaySongs()
    {
        return null;
    }
}
