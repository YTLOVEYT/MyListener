package com.example.mylistener;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;

import com.example.mylistener.mvp.model.MusicPlaybackTrack;
import com.example.mylistener.utils.LogUtil;

import java.lang.ref.WeakReference;

/**
 * MusicService
 * Created by YinTao on 2018/4/8.
 */
public class MusicService extends Service
{
    private static boolean D = true;
    private final IBinder serviceSub = new ServiceSub(this);
    private boolean serviceInUse = false;
    private NotificationManagerCompat notificationManagerCompat;

    @Override
    public void onCreate()
    {
        super.onCreate();
        LogUtil.e("ServiceCreate");
        //获取所有实例
        notificationManagerCompat = NotificationManagerCompat.from(this);

    }

    /** 绑定服务 */
    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        LogUtil.e("ServiceOnBind");
        // FIXME: 2018/4/18 设置延时 对service 的处理 mAlarmManager
        serviceInUse = true;
        return serviceSub;  //返回绑定的binder
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, flags, startId);
    }

    /** 解绑服务 */
    @Override
    public boolean onUnbind(Intent intent)
    {
        LogUtil.e("ServiceUnbind");
        serviceInUse = false;
        //fixme 音乐相关服务关闭，需要保存相关信息
        // FIXME: 2018/4/18 服务退帮定前操作

        return super.onUnbind(intent);
    }

    /** 重绑定服务 防止服务意外解绑 */
    @Override
    public void onRebind(Intent intent)
    {
        // FIXME: 2018/4/18 重新绑定时 取消关闭的定时
        serviceInUse = true;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    /** 服务项目 外接AIDL */
    public static final class ServiceSub extends IMyListenerInterface.Stub
    {
        private final WeakReference<MusicService> mService;

        /** 使用stub前必须传递service */
        public ServiceSub(MusicService mService)
        {
            this.mService = new WeakReference<MusicService>(mService);//建立弱应用 元数据
        }

        @Override
        public void openFile(String path) throws RemoteException
        {

        }

        @Override
        public void open(long[] list, int position, long sourceId, int sourceType) throws RemoteException
        {

        }

        @Override
        public void stop() throws RemoteException
        {

        }

        @Override
        public void pause() throws RemoteException
        {

        }

        @Override
        public void play() throws RemoteException
        {

        }

        @Override
        public void prev(boolean forcePrevious) throws RemoteException
        {

        }

        @Override
        public void next() throws RemoteException
        {

        }

        @Override
        public void enqueue(long[] list, int action, long sourceId, int sourceType) throws RemoteException
        {

        }

        @Override
        public void setQueuePosition(int index) throws RemoteException
        {

        }

        @Override
        public void setShuffleMode(int shufflemode) throws RemoteException
        {

        }

        @Override
        public void setRepeatMode(int repeatmode) throws RemoteException
        {

        }

        @Override
        public void moveQueueItem(int from, int to) throws RemoteException
        {

        }

        @Override
        public void refresh() throws RemoteException
        {

        }

        @Override
        public void playlistChanged() throws RemoteException
        {

        }

        @Override
        public boolean isPlaying() throws RemoteException
        {
            return false;
        }

        @Override
        public long[] getQueue() throws RemoteException
        {
            return new long[0];
        }

        @Override
        public long getQueueItemAtPosition(int position) throws RemoteException
        {
            return 0;
        }

        @Override
        public int getQueueSize() throws RemoteException
        {
            return 0;
        }

        @Override
        public int getQueuePosition() throws RemoteException
        {
            return 0;
        }

        @Override
        public int getQueueHistoryPosition(int position) throws RemoteException
        {
            return 0;
        }

        @Override
        public int getQueueHistorySize() throws RemoteException
        {
            return 0;
        }

        @Override
        public int[] getQueueHistoryList() throws RemoteException
        {
            return new int[0];
        }

        @Override
        public long duration() throws RemoteException
        {
            return 0;
        }

        @Override
        public long position() throws RemoteException
        {
            return 0;
        }

        @Override
        public long seek(long pos) throws RemoteException
        {
            return 0;
        }

        @Override
        public void seekRelative(long deltaInMs) throws RemoteException
        {

        }

        @Override
        public long getAudioId() throws RemoteException
        {
            return 0;
        }

        @Override
        public MusicPlaybackTrack getCurrentTrack() throws RemoteException
        {
            return null;
        }

        @Override
        public MusicPlaybackTrack getTrack(int index) throws RemoteException
        {
            return null;
        }

        @Override
        public long getNextAudioId() throws RemoteException
        {
            return 0;
        }

        @Override
        public long getPreviousAudioId() throws RemoteException
        {
            return 0;
        }

        @Override
        public long getArtistId() throws RemoteException
        {
            return 0;
        }

        @Override
        public long getAlbumId() throws RemoteException
        {
            return 0;
        }

        @Override
        public String getArtistName() throws RemoteException
        {
            return null;
        }

        @Override
        public String getTrackName() throws RemoteException
        {
            return null;
        }

        @Override
        public String getAlbumName() throws RemoteException
        {
            return null;
        }

        @Override
        public String getPath() throws RemoteException
        {
            return null;
        }

        @Override
        public int getShuffleMode() throws RemoteException
        {
            return 0;
        }

        @Override
        public int removeTracks(int first, int last) throws RemoteException
        {
            return 0;
        }

        @Override
        public int removeTrack(long id) throws RemoteException
        {
            return 0;
        }

        @Override
        public boolean removeTrackAtPosition(long id, int position) throws RemoteException
        {
            return false;
        }

        @Override
        public int getRepeatMode() throws RemoteException
        {
            return 0;
        }

        @Override
        public int getMediaMountedCount() throws RemoteException
        {
            return 0;
        }

        @Override
        public int getAudioSessionId() throws RemoteException
        {
            return 0;
        }

        @Override
        public void setLockscreenAlbumArt(boolean enabled) throws RemoteException
        {

        }

        @Override
        public IBinder asBinder()
        {
            return null;
        }
    }
}
