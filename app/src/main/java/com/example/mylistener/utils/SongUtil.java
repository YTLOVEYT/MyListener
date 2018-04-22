package com.example.mylistener.utils;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * 歌曲的辅助类
 * Created by YinTao on 2018/4/3.
 */

public class SongUtil
{
    public static final String MUSIC_ONLY_SELECTION = MediaStore.Audio.AudioColumns.IS_MUSIC + "=1"
            + " AND " + MediaStore.Audio.AudioColumns.TITLE + " != ''";

    /** 获取影集 */
    public static Uri getAlbumArtUri(long param)
    {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), param);
    }
}
