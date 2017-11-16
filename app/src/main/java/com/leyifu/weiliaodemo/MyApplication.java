package com.leyifu.weiliaodemo;

import android.app.Application;

import io.rong.imkit.RongIM;

/**
 * Created by hahaha on 2017/5/5 0005.
 */
public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
//        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
//                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

        /**
         * IMKit SDK调用第一步 初始化
         */
        RongIM.init(this);
//        }
    }

}
