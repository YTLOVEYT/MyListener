package com.example.mylistener.provider.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * MyListener
 * Created by YinTao on 2018/4/18.
 */
public interface CommonDb
{
    /**
     * 创建数据库
     * @param sqlDb SQLiteDatabase
     */
    public void onCreate(SQLiteDatabase sqlDb);

    /**
     * 升级数据库
     * @param sqlDb SQLiteDatabase
     * @param oldV  旧版本
     * @param newV  新版本
     */
    public void onUpgrade(SQLiteDatabase sqlDb, int oldV, int newV);

    /**
     * 降级数据库
     * @param sqlDb SQLiteDatabase
     * @param oldV  旧版本
     * @param newV  新版本
     */
    public void onDowngrade(SQLiteDatabase sqlDb, int oldV, int newV);


}
