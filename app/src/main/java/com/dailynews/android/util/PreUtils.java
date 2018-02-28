package com.dailynews.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by sunfeiswag on 2018/2/28.
 */


public class PreUtils {

    //将请求返回的数据保存到缓存中
    public static void putStringToDefault(Context context,String key, String value){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(key, value).commit();
    }

    //从缓存中得到数据
    public static String getStringFromDefault(Context context,String key, String defvValue){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(key,defvValue);

    }
}
