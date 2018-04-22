package com.example.mylistener.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * MusicDb
 * Created by YinTao on 2018/4/18.
 */
public class MusicDb extends SQLiteOpenHelper
{
    private static final String MUSIC_DB = "music.db";
    private static final int DB_VERSION = 1;
    private static MusicDb instance = null;
    private Context context;

    /** 单列获取数据库连接实例 */
    public static MusicDb getInstance(final Context context)
    {
        if (instance == null)
        {
            synchronized (MusicDb.class)
            {
                if (instance == null)
                {
                    instance = new MusicDb(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public MusicDb(Context context)
    {
        super(context, MUSIC_DB, null, DB_VERSION);
        this.context = context;
    }

    /** 创建数据库 */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //创建5个表结构

    }

    /** 升级数据库 */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //删除5个表结构

    }
}
