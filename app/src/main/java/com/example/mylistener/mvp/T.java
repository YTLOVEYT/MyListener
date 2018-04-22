package com.example.mylistener.mvp;

/**
 * delete
 * Created by YinTao on 2018/3/20.
 */

public class T
{
    private static T t;

    private MyListener myLister;

    public static T getInstance()
    {
        if (t == null)
        {
            synchronized (T.class)
            {
                if (t == null)
                {
                    t = new T();
                }
            }
        }
        return t;
    }

    public void setMyLister(MyListener myLister)
    {
        this.myLister = myLister;
    }
}
