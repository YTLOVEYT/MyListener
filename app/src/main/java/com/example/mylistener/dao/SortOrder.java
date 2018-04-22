package com.example.mylistener.dao;

import android.provider.MediaStore;

/**
 * 排序规则
 * Created by YinTao on 2018/3/27.
 */

public class SortOrder
{
    public SortOrder()
    {
    }

    /** 歌手排序规则 */
    public interface ArtistSortOrder
    {
        String ARTIST_AZ = MediaStore.Audio.Artists.DEFAULT_SORT_ORDER;
        String ARTIST_ZA = ARTIST_AZ + "DESC";
        String ARTIST_NUMBER_OF_SONGS = MediaStore.Audio.Artists.NUMBER_OF_TRACKS + "DESC";
        String ARTIST_NUMBER_OF_ALBUMS = MediaStore.Audio.Artists.NUMBER_OF_ALBUMS + "DESC";
    }

    /** 专辑排序规则 */
    public interface albumSortOrder
    {
        String ALBUM_AZ = MediaStore.Audio.Albums.DEFAULT_SORT_ORDER;
        String ALBUM_ZA = ALBUM_AZ + "DESC";
        String ALBUM_NUMBER_OF_SONGS = MediaStore.Audio.Albums.NUMBER_OF_SONGS + "DESC";
    }

    /** 歌曲排序规则 */
    public interface SongSortOrder
    {
        String SONG_AZ = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
        String SONG_ZA = SONG_AZ + "DESC";
        String SONG_ARTIST = MediaStore.Audio.Media.ARTIST;
        String SONG_ALBUM = MediaStore.Audio.Media.ALBUM;
        String SONG_DURATION = MediaStore.Audio.Media.DURATION + "DESC";
        String SONG_DATE = MediaStore.Audio.Media.DATE_ADDED + "DESC";
    }

    /** 专辑歌曲排序规则 */
    public interface AlbumSongSortOrder
    {
        String SONG_AZ = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
        String SONG_Z_A = SONG_AZ + " DESC";
        String SONG_TRACK_LIST = MediaStore.Audio.Media.TRACK + ", " + MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
        String SONG_DURATION = SongSortOrder.SONG_DURATION;
        String SONG_YEAR = MediaStore.Audio.Media.YEAR + " DESC";
    }

    /** 歌手歌曲排序规则 */
    public interface ArtistSongSortOrder
    {
        String SONG_A_Z = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;

    }
}
