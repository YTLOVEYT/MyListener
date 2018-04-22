package com.example.mylistener.mvp.presenter;

import com.example.mylistener.mvp.view.BaseView;

/**
 * 基础Presenter接口
 * Created by YinTao on 2018/3/20.
 */
public interface IBasePresenter<T extends BaseView>
{
    /** 分发绑定视图 */
    void attachView(T view);

    /** 签署 */
    void subscribe();

    /** 取消签署 */
    void unSubscribe();
}
