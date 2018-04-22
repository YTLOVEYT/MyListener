package com.example.mylistener.permission;

/**
 * 权限申请回调函数
 * Created by YinTao on 2018/3/12.
 */
public interface IPermissionCallBack
{
    /** 权限通过 */
    void permissionGranted();

    /** 权限拒绝 */
    void permissionRefused();
}
