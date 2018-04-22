package com.example.mylistener.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.util.TypedValue;

import com.afollestad.appthemeengine.Config;
import com.example.mylistener.R;

/**
 * 主题工具类
 * Created by YinTao on 2018/3/7.
 */

public class ATEUtil
{
    /** 获取ATE的状态 */
    public static String getATEKey(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean("dark_theme", false) ? "dark_theme" : "light_theme";
    }

    /** 获取Primary颜色 */
    public static int getThemePrimaryColor(Context context)
    {
        return Config.primaryColor(context, getATEKey(context));
    }

    /** 获取accent颜色 */
    public static int getThemeAccentColor(Context context)
    {
        return Config.accentColor(context, getATEKey(context));
    }



    /** 获取默认的专辑图片 */
    public static Drawable getDefaultAlbumDrawable(Context context)
    {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.default_album_drawable, typedValue, true);
        return context.getResources().getDrawable(typedValue.resourceId);
    }

    /** 获取默认的歌手图片 */
    public static Drawable getDefaultSingerDrawable(Context context)
    {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.default_singer_drawable, typedValue, true);
        return ActivityCompat.getDrawable(context, typedValue.resourceId);
    }
}
