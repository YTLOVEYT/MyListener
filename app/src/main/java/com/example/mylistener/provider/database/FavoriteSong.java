package com.example.mylistener.provider.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import com.example.mylistener.provider.MusicDb;
import com.example.mylistener.utils.LogUtil;

/**
 * 最喜欢歌曲数据库
 * Created by YinTao on 2018/4/18.
 */
public class FavoriteSong implements CommonDb
{
    private MusicDb musicDb;

    private static FavoriteSong instance;

    private FavoriteSong(Context context)
    {
        musicDb = MusicDb.getInstance(context);
    }

    /** 开放使用#FavoriteSong单例模式 */
    public static FavoriteSong getInstance(final Context context)
    {
        if (instance == null)
        {
            synchronized (FavoriteSong.class)
            {
                if (instance == null)
                {
                    instance = new FavoriteSong(context.getApplicationContext());
                }
            }
        }
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase sqlDb)
    {
        sqlDb.execSQL("CREATE TABLE IF NOT EXISTS " + FavoriteSongColumns.NAME + "("
                + FavoriteSongColumns.SONGID + "  LONG NOT NULL,"
                + FavoriteSongColumns.TIMEADDED + "  LONG NOT NULL"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDb, int oldV, int newV)
    {
        // FIXME: 2018/4/18 升级数据库 数据保存怎么做
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqlDb, int oldV, int newV)
    {
        sqlDb.execSQL("DROP TABLE IF EXISTS " + FavoriteSongColumns.NAME);//先删表
        onCreate(sqlDb);//再创表
    }

    /**
     * 添加喜欢的歌曲 接入数据库事务处理
     * @param ids 歌曲的IDS
     * @return 添加的数据
     */
    public int addFavoriteSongs(long[] ids)
    {
        SQLiteDatabase database = musicDb.getWritableDatabase();
        database.beginTransaction();//开启事务
        Cursor cursor = null;
        int insert = 0;
        try
        {
            for (long songId : ids)
            {
                cursor = database.query(FavoriteSongColumns.NAME, new String[]{}, FavoriteSongColumns.SONGID + "=?", new String[]{String.valueOf(songId)}, null, null, null);
                if (cursor != null && cursor.getCount() == 0) //数据库添加
                {
                    ContentValues values = new ContentValues();
                    values.put(FavoriteSongColumns.SONGID, songId);
                    values.put(FavoriteSongColumns.TIMEADDED, System.currentTimeMillis());
                    database.insert(FavoriteSongColumns.NAME, null, values);
                    insert++;
                }
            }
            database.setTransactionSuccessful(); //设置事务处理成功
            return insert;
        }
        catch (Exception e)
        {
            LogUtil.e("添加喜欢歌曲到数据库失败");
            e.printStackTrace();
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
            database.endTransaction();//关闭事务
        }
        return 0;
    }

    /**
     * 移除喜欢的歌曲
     * @param ids 歌曲ids
     * @return 移除的数量
     */
    public int removeFavoriteSongs(long[] ids)
    {
        SQLiteDatabase database = musicDb.getWritableDatabase();
        database.beginTransaction();//开启事务
        Cursor cursor = null;
        int delete = 0;
        try
        {
            for (long songId : ids)
            {
                cursor = database.query(FavoriteSongColumns.NAME, new String[]{}, FavoriteSongColumns.SONGID + "=?", new String[]{String.valueOf(songId)}, null, null, null);
                if (cursor != null && cursor.getCount() == 0) //数据库添加
                {
                    database.delete(FavoriteSongColumns.NAME, FavoriteSongColumns.SONGID + "=?", new String[]{String.valueOf(songId)});
                    delete++;
                }
            }
            database.setTransactionSuccessful(); //设置事务处理成功
            return delete;
        }
        catch (Exception e)
        {
            LogUtil.e("删除喜欢歌曲从数据库失败");
            e.printStackTrace();
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
            database.endTransaction();//关闭事务
        }
        return 0;
    }

    /** 获取所有喜欢的歌曲 */
    public Cursor getAllFavoriteSongs()
    {
        SQLiteDatabase database = musicDb.getReadableDatabase();
        return database.query(FavoriteSongColumns.NAME, new String[]{FavoriteSongColumns.SONGID}, null, null, null, null, FavoriteSongColumns.TIMEADDED + " DESC", null);
    }

    public boolean isFavoriteSong(long songId)
    {
        SQLiteDatabase database = musicDb.getReadableDatabase();
        database.beginTransaction();
        try
        {
            Cursor cursor = database.query(FavoriteSongColumns.NAME, new String[]{FavoriteSongColumns.SONGID}, FavoriteSongColumns.SONGID + "=?", new String[]{String.valueOf(songId)}, null, null, null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {

        }
        return false;
    }

    public interface FavoriteSongColumns
    {
        /** 表名 */
        String NAME = "favorite_song";

        /** 字段1 名歌曲ID */
        String SONGID = "song_id";

        /** 字段2 添加时间 */
        String TIMEADDED = "time_added";
    }
}
