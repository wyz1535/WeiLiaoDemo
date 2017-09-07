package com.leyifu.weiliaodemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.leyifu.weiliaodemo.R;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OKHttpActivity extends AppCompatActivity {

    private static final String TAG = "OKHttpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        initGETOKHttp();

//        initPOST();
    }

    private void initGETOKHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://www.baidu.com")
                        .get()
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String body = response.body().string();
                    Log.e(TAG, "initOKHttp: body=" + body);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initPOST() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("name","admin")
                            .add("password","123456")
                            .build();
                    Request request=new Request.Builder()
                            .url("http://www.baidu.com")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String data = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
