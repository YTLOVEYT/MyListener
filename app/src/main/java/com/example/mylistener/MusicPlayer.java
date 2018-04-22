package com.example.mylistener;

import android.app.Activity;
import android.content.*;
import android.os.IBinder;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * 音乐播放器 控制音乐播放的服务
 * Created by YinTao on 2018/4/3.
 */
public class MusicPlayer
{
    private static WeakHashMap<Context, ServiceBinder> weakHashMap;//弱引用 存储元数据

    static
    {
        weakHashMap = new WeakHashMap<>();

    }

    /** 绑定服务 */
    public static ServiceToken bind2Service(Context context, ServiceConnection callback)
    {
        Activity activity = ((Activity) context).getParent();
        if (activity == null)
        {
            activity = (Activity) context;
        }
        ContextWrapper wrapper = new ContextWrapper(activity);//装饰模式
        ComponentName componentName = wrapper.startService(new Intent(wrapper, MusicService.class));//开启服务
        ServiceBinder binder = new ServiceBinder(callback, wrapper.getApplicationContext());//回调
        //绑定服务，设置回调
        if (wrapper.bindService(new Intent().setClass(wrapper, MusicService.class), binder, 0))
        {
            weakHashMap.put(wrapper, binder); //保存元数据
            return new ServiceToken(wrapper);
        }
        return null;
    }

    /** 解绑服务 */
    public static void unbind2Service(final ServiceToken token)
    {
        if (token == null)
        {
            return;
        }
        ContextWrapper wrapper = token.wrapper;
        ServiceBinder binder = weakHashMap.remove(wrapper);
        if (binder == null)
        {
            return;
        }
        wrapper.unbindService(binder);//取消绑定
        if (weakHashMap.isEmpty())
        {
            // FIXME: 2018/4/17 服务置为空
        }

    }

    /** 服务的身份 */
    private static final class ServiceToken
    {
        private ContextWrapper wrapper;

        private ServiceToken(final ContextWrapper wrapper)
        {
            this.wrapper = wrapper;
        }
    }

    /** 服务连接回调 */
    private static final class ServiceBinder implements ServiceConnection
    {
        private final ServiceConnection callBack; //回调
        private final Context context;//上下文
        private static IMyListenerInterface mService = null;

        private ServiceBinder(ServiceConnection callBack, Context context)
        {
            this.callBack = callBack;
            this.context = context;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            mService = IMyListenerInterface.Stub.asInterface(service);
            if (callBack != null)
            {
                callBack.onServiceConnected(name, service);
            }
            // FIXME: 2018/4/17 初始化设置

        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            if (callBack != null)
            {
                callBack.onServiceDisconnected(name);
            }
            mService = null;
        }
    }


}
