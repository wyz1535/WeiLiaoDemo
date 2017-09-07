package com.leyifu.weiliaodemo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.leyifu.weiliaodemo.R;

public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = "WebViewActivity";
    private WebView web_view;


    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调
    private Uri imageUri;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        web_view = ((WebView) findViewById(R.id.web_view));

        //设置在app中打开网页  默认会使用浏览器打开网页
//        web_view.setWebViewClient(new WebViewClient());
//        web_view.loadUrl("http://www.baidu.com");
//        web_view.loadUrl("file:///android_asset/test01.html");
        web_view.loadUrl("http://192.168.0.105:8080/webImg/01.html");

        //设置可以使用js的方法
        web_view.getSettings().setJavaScriptEnabled(true);
        //调用本地的方法
        web_view.addJavascriptInterface(new JsInteration(), "android");

//        web_view.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Log.e(TAG, " url=" + url);
//                //判断url拦截事件
//                if (url.equals("file:///android_asset/test2.html")) {
//                    Log.e(TAG, "shouldOverrideUrlLoading: " + url);
//                    startActivity(new Intent(WebViewActivity.this, MainActivity.class));
//                    return true;
//                } else {
//                    web_view.loadUrl(url);
//                    return false;
//                }
//
//                if (url.startsWith("tel:")) {
////                    startActivity();
//                    startActivity(new Intent(Intent.ACTION_DIAL,Uri.parse(url)));
//                    return true;
//                }
//            }
//        });

        web_view.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }


    public class JsInteration {

        @JavascriptInterface
        public void back() {
            Intent intent = getPackageManager().getLaunchIntentForPackage("com.leyifu.makefriend");
            startActivity(intent);
        }

        @JavascriptInterface
        public String back01() {
            return "helloworld";
        }
    }
}
