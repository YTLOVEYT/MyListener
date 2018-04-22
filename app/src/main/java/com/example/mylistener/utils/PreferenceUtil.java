package com.example.mylistener.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.mylistener.dao.SortOrder;

/**
 * 操作偏好设置
 * Created by YinTao on 2018/3/15.
 */

public class PreferenceUtil
{
    /** 歌手排列顺序 */
    private static final String ARTIST_SORT_ORDER = "artist_sort_order";
    /** 歌手歌的分类顺序 */
    private static final String ARTIST_SONG_SORT_ORDER = "artist_song_sort_order";
    /** 歌手头像url */
    private static final String ARTIST_ART_URL = "artist_art_url_";
    /** 专辑分类顺序 */
    private static final String ALBUM_SORT_ORDER = "album_sort_order";
    /** 专辑歌曲分类顺序 */
    private static final String ALBUM_SONG_SORT_ORDER = "album_song_sort_order";
    /** 歌曲分类顺序 */
    private static final String SONG_SORT_ORDER = "song_sort_order";
    /** 触发歌手列表 */
    private static final String TOGGLE_ARTIST_GRID = "toggle_artist_grid";
    /** 触发播放列表 */
    private static final String TOGGLE_PLAYLIST_VIEW = "toggle_playlist_view";
    /** 开启页面 */
    private static final String START_PAGE_INDEX = "start_page_index";

    private static PreferenceUtil instance;
    private final SharedPreferences preferences;

    private PreferenceUtil(Context context)
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /** 单例实例化 获取偏好设置的管理工具类 */
    public static PreferenceUtil getInstance(Context context)
    {
        if (instance == null)
        {
            synchronized (PreferenceUtil.class)
            {
                if (instance == null)
                {
                    instance = new PreferenceUtil(context);
                }
            }
        }
        return instance;
    }

    /** 获取启动开启页 */
    public int getStartPageIndex()
    {
        return preferences.getInt(START_PAGE_INDEX, 0);
    }

    /** 设置并保存歌曲的排序规则 */
    public void setSongSortOder(String value)
    {
        preferences.edit().putString(SONG_SORT_ORDER, value).apply();
    }

    /** 获取歌曲的排序规则 默认a-z */
    public String getSongSortOder()
    {
        return preferences.getString(SONG_SORT_ORDER, SortOrder.SongSortOrder.SONG_AZ);
    }


}
