package com.example.mylistener;

import java.util.HashMap;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * 基于RxJava的RxBus事件总线
 * Created by YinTao on 2018/3/19.
 */

public class RxBus
{
    private static volatile RxBus rbInstance;
    private SerializedSubject<Object, Object> subject;//并发处理消息，只允许一个线程发送消息
    private HashMap<String, CompositeSubscription> subscriptionHashMap;

    private RxBus()
    {
        subject = new SerializedSubject<>(PublishSubject.create());
    }

    /** 单例RxBus */
    public static RxBus getRbInstance()
    {
        if (rbInstance == null)
        {
            synchronized (RxBus.class)
            {
                if (rbInstance == null)
                {
                    rbInstance = new RxBus();
                }
            }
        }
        return rbInstance;
    }

    /**
     * 发送事件
     * @param o 事件源
     */
    public void post(Object o)
    {
        subject.onNext(o);
    }

    /** 返回指定的Observable实例 */
    public <T> Observable<T> toObservable(Class<T> type)
    {
        return subject.ofType(type);
    }

    /** 订阅 */
    public <T> Subscription doSubscribe(Class<T> type, Action1<T> next, Action1<Throwable> error)
    {
        return toObservable(type)
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next, error);
    }

    /** 保存订阅信息 */
    public void addSubscription(Object o, Subscription subscription)
    {
        if (subscriptionHashMap == null)
        {
            subscriptionHashMap = new HashMap<>();
        }
        String key = o.getClass().getName();
        if (subscriptionHashMap.get(key) != null)
        {
            subscriptionHashMap.get(key).add(subscription);
        }
        else
        {
            CompositeSubscription compositeSubscription = new CompositeSubscription();
            compositeSubscription.add(subscription);
            subscriptionHashMap.put(key, compositeSubscription);
        }
    }

    /** 取消订阅 */
    public void unSubscribe(Object o)
    {
        if (subscriptionHashMap == null)
        {
            return;
        }
        String key = o.getClass().getName();
        if (!subscriptionHashMap.containsKey(key))
        {
            return;
        }
        if (subscriptionHashMap.get(key) != null)
        {
            subscriptionHashMap.get(key).unsubscribe();
        }
        subscriptionHashMap.remove(key);
    }


}
