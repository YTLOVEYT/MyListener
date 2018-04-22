package com.example.mylistener.mvp.usecase;

/**
 * 待定
 * Created by YinTao on 2018/3/21.
 */

public abstract class ACase<Q extends ACase.RequestValue, P extends ACase.ResponseValue>
{
    private Q requestValues;

    public Q getRequestValues()
    {
        return requestValues;
    }

    public void setRequestValues(Q requestValues)
    {
        this.requestValues = requestValues;
    }

    /** 抽象方法，执行请求获取回应 */
    public abstract P execute(Q requestValue);

    /** 请求接口 */
    public interface RequestValue
    {
    }

    /** 回复接口 */
    public interface ResponseValue
    {

    }


}
