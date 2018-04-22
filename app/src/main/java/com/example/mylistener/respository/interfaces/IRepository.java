package com.example.mylistener.respository.interfaces;

import com.example.mylistener.entity.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * 地址接口
 * Created by YinTao on 2018/3/21.
 */

public interface IRepository
{
    /** 获取歌曲家的信息 */
    Observable getArtistInfo(String artist);

    /** 获取歌曲的歌词 */
    Observable<File> getLrcFile(String title, String artist, long duration);

    /** 获取所有的歌曲 */
    Observable<ArrayList<Song>> getAllSongs();

    /** 获取喜欢的歌曲 */
    Observable<List<Song>> getFavoriteSongs();

    /** 获取最近添加的歌曲 */
    Observable<List<Song>> getRecentlyAddedSongs();

    /** 获取最近播放的歌曲 */
    Observable<List<Song>> getRecentlyPlayedSongs();

    /** 获取最高量播放的歌曲 */
    Observable<List<Song>> getTopPlaySongs();


}
