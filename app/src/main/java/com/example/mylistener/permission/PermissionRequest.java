package com.example.mylistener.permission;

import java.util.ArrayList;
import java.util.Random;

/**
 * 权限请求封装
 * Created by YinTao on 2018/3/12.
 */
public class PermissionRequest
{
    private int requestCode;
    private IPermissionCallBack IPermissionCallBack;
    private ArrayList<String> permissions;
    private Random random;

    public PermissionRequest(int requestCode)
    {
        this.requestCode = requestCode;
    }

    public PermissionRequest(ArrayList<String> permissions, IPermissionCallBack IPermissionCallBack)
    {
        this.IPermissionCallBack = IPermissionCallBack;
        this.permissions = permissions;
        if (random == null)
        {
            random = new Random();
        }
        requestCode = random.nextInt(32768);
    }

    public int getRequestCode()
    {
        return requestCode;
    }

    public void setRequestCode(int requestCode)
    {
        this.requestCode = requestCode;
    }

    public IPermissionCallBack getIPermissionCallBack()
    {
        return IPermissionCallBack;
    }

    public void setIPermissionCallBack(IPermissionCallBack IPermissionCallBack)
    {
        this.IPermissionCallBack = IPermissionCallBack;
    }

    public ArrayList<String> getPermissions()
    {
        return permissions;
    }

    public void setPermissions(ArrayList<String> permissions)
    {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (obj instanceof PermissionRequest)
        {
            return ((PermissionRequest) obj).requestCode == this.requestCode;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return requestCode;
    }
}
