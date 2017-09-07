package com.leyifu.weiliaodemo.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hahaha on 2017/6/15 0015.
 */
public class VersionName {

    private static final String TAG = "VersionName";
    private static String body;

    public static int getVerCode(Context context) {
        int versionCode = -1;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            Log.e("VersionName", "getVerCode: " + context.getPackageName());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getVerName(Context context) {
        String versionName = "";
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static String getServiceVer(Context content) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://10.0.2.2:8080/update/update.json")
//                            .url("http://www.baidu.com")
                            .build();
                    Response response = client.newCall(request).execute();
                    body = response.body().string();
                    Log.e(TAG, "getServiceVer= " + body);
                } catch (IOException e) {

                }
            }
        }).start();
        return body;
    }
}
