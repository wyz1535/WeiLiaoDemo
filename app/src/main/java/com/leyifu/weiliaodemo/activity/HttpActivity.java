package com.leyifu.weiliaodemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.leyifu.weiliaodemo.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpActivity extends AppCompatActivity {

    private static final String TAG = "HttpActivity";
    private WebView web_view;
    private Button bt_http_url;
    private TextView tv_http;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);


//        web_view = (WebView) findViewById(R.id.web_view);
//        initWebView();
        Toast.makeText(HttpActivity.this,"haha",Toast.LENGTH_SHORT).show();
        bt_http_url = (Button) findViewById(R.id.bt_http_url);
        tv_http = (TextView) findViewById(R.id.tv_http);

        bt_http_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        BufferedReader reader = null;
                        HttpURLConnection httpURLConnection = null;
                        try {
                            URL url = new URL("http://www.baidu.com");
                            httpURLConnection = ((HttpURLConnection) url.openConnection());
                            httpURLConnection.setRequestMethod("GET");
                            httpURLConnection.setConnectTimeout(8000);
                            httpURLConnection.setReadTimeout(8000);
                            InputStream inputStream = httpURLConnection.getInputStream();
                            reader = new BufferedReader(new InputStreamReader(inputStream));
                            StringBuilder stringBuilder = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                stringBuilder.append(line);
                            }
                            showRead(stringBuilder.toString());
                            Log.e(TAG, "run: stringBuilder="+stringBuilder);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (reader != null) {
                                try {
                                    reader.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                        }
                    }
                }).start();
            }
        });

    }

    private void showRead(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: response="+response);
                Toast.makeText(HttpActivity.this,"response"+response,Toast.LENGTH_SHORT).show();
                tv_http.setText(response);
            }
        });
    }

    private void initWebView() {
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.setWebViewClient(new WebViewClient());
        web_view.loadUrl("http://www.baidu.com");
    }
}
