package com.example.mylistener.dao.loader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.MediaStore;
import android.util.Log;

import com.example.mylistener.entity.Song;
import com.example.mylistener.utils.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * 歌曲加载类
 * Created by YinTao on 2018/3/27.
 */

public class SongLoader
{
    private static final String TAG = "SongLoader";

    public static Observable<ArrayList<Song>> getAllSongs(Context context)
    {
        Observable<ArrayList<Song>> songsForCursor = getSongsForCursor(getSongCursor(context, null, null));
        return songsForCursor;
    }

    /** 根据游标获取数据库的歌曲（Observable） */
    public static Observable<ArrayList<Song>> getSongsForCursor(final Cursor cursor)
    {
        return Observable.create(new Observable.OnSubscribe<ArrayList<Song>>()
        {
            @Override
            public void call(Subscriber<? super ArrayList<Song>> subscriber)
            {
                ArrayList<Song> songs = new ArrayList<Song>();
                try
                {
                    if (cursor != null && cursor.moveToFirst())
                    {
                        do
                        {
                            long id = cursor.getLong(0);
                            String title = cursor.getString(1);
                            String artist = cursor.getString(2);
                            String album = cursor.getString(3);
                            int duration = cursor.getInt(4);
                            int trackNumber = cursor.getInt(5);
                            long artistId = cursor.getInt(6);
                            long albumId = cursor.getLong(7);
                            String path = cursor.getString(8);
                            songs.add(new Song(id, albumId, artistId, title, artist, album, duration, trackNumber, path));
                        } while (cursor.moveToNext());
                        Log.e(TAG, "call: " + songs);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    if (cursor != null)
                    {
                        cursor.close();
                    }
                }
                subscriber.onNext(songs); //要下发下去
                subscriber.onCompleted();
            }
        });

    }

    /** 使用ContentProvider获取游标 */
    public static Cursor getSongCursor(Context context, String selection, String[] params)
    {
        String songSortOder = PreferenceUtil.getInstance(context).getSongSortOder();
        return getSongCursor(context, selection, params, songSortOder);
    }

    /** 使用ContentProvider获取游标 */
    public static Cursor getSongCursor(Context context, String selection, String[] params, String sortOrder)
    {
        String selectionStatement = "is_music=1 AND title != ''";
        return context.getContentResolver()
                .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        new String[]{"_id", "title", "artist", "album", "duration", "track", "artist_id", "album_id", MediaStore.Audio.Media.DATA},
                        selectionStatement,
                        params,
                        sortOrder);
    }

}
