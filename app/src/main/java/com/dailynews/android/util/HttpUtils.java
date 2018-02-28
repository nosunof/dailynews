package com.dailynews.android.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by sunfeiswag on 2018/2/28.
 */
//网络请求类
public class HttpUtils {

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, ResponseHandlerInterface responseHandler){
        client.get(Constant.BASEURL + url, responseHandler);
    }

    public static void sendOkHttpRequest(String url, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Constant.BASEURL + url).build();
        client.newCall(request).enqueue(callback);
    }


    //判断当前上下文网络是否可用
    public static boolean isNetWorkConnected(Context context){
        if (context != null){
            ConnectivityManager mConnectivityManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetWorkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetWorkInfo != null){
                return mNetWorkInfo.isAvailable();
            }
        }
        return false;
    }
}
