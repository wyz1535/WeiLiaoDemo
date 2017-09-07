package com.leyifu.weiliaodemo.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;

import com.leyifu.weiliaodemo.interf.OnDownloadListeren;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hahaha on 2017/6/16 0016.
 */
public class UtilsDown {


    private static final String TAG = "UtilsDown";
    private static final int DOWNLOAD_SUCCESS = 1;
    private static final int DOWNLOAD_FAILED = 2;

    public static boolean utilsCheckNetWork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return false;
        }
        int type = activeNetworkInfo.getType();
        if (type == ConnectivityManager.TYPE_MOBILE || type == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    public static void downUpdate(Context context, final String url, final File downloadFile, final OnDownloadListeren listeren) {

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case DOWNLOAD_SUCCESS:
                        File dlFile= (File) msg.obj;
                        if (listeren != null) {
                            listeren.onDownloadSuccessed(dlFile);
                        }
                        break;
                    case DOWNLOAD_FAILED:
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            private HttpURLConnection httpURLConnection;

            @Override
            public void run() {
                InputStream input = null;
                FileOutputStream output = null;
                try {
                    URL mUrl = new URL(url);
                    httpURLConnection = ((HttpURLConnection) mUrl.openConnection());
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setReadTimeout(3000);
                    httpURLConnection.setConnectTimeout(3000);
                    InputStream inputStream = httpURLConnection.getInputStream();
                    byte[] bytes = new byte[1024];
                    output = new FileOutputStream(downloadFile);
                    int len = -1;
                    while ((len = inputStream.read(bytes))!=-1) {
                        output.write(bytes,0,len );
                    }
                    output.flush();
                    output.close();
                    Message message = handler.obtainMessage();
                    message.what=DOWNLOAD_SUCCESS;
                    message.obj =downloadFile;
                    message.sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(DOWNLOAD_FAILED);
                }
            }
        }).start();

    }


}
