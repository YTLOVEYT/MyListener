package com.example.mylistener.permission;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 权限管理
 * Created by YinTao on 2018/3/12.
 */

public class PermissionManager
{
    private static Context context;
    private static SharedPreferences sharedPreferences;

    /** 使用该类必须初始化 */
    public static void init(Context context)
    {
        sharedPreferences = context.getSharedPreferences(context.getPackageName() + ".permission", Context.MODE_PRIVATE);
        PermissionManager.context = context;
    }

    /**
     * 请求权限
     * @param activity           activity
     * @param permissions        权限数组
     * @param IPermissionCallBack 请求结果回调
     */
    public static void askForPermission(Activity activity, String[] permissions, IPermissionCallBack IPermissionCallBack)
    {
        if (IPermissionCallBack == null)
        {
            return;
        }
        if (checkPermissions(activity, permissions))
        {
            IPermissionCallBack.permissionGranted();
            return;
        }
        PermissionRequest permissionRequest = new PermissionRequest(new ArrayList<String>(Arrays.asList(permissions)), IPermissionCallBack);



        ActivityCompat.requestPermissions(activity, permissions, 0);


    }



    /**
     * 检查权限是否被授予
     * @param activity    activity
     * @param permissions 权限数组
     * @return T/F
     */
    public static boolean checkPermissions(Activity activity, String[] permissions)
    {
        for (String permission : permissions)
        {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED)
            {
                return false;
            }
        }
        return true;
    }

}
