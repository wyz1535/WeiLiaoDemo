package com.leyifu.weiliaodemo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.leyifu.weiliaodemo.R;

import java.io.File;
import java.io.IOException;

public class WebViewActivity01 extends AppCompatActivity {

    private static final String TAG = "WebViewActivity001";
    private static final String userName = "吴小满";
    private static String userId = "15524961535";
    private static final String URL = "http://192.168.0.105:8080/webImg/01.html?userId=" + userId + "&userName=" + userName;
    //    private static final String URL = "http://192.168.0.105:8080/webImg/01.html";  15527961535
    private String loadUrl;
    private WebView webView;
    private SwipeRefreshLayout swipe_refresh;
    private ProgressBar progress_bar;

    Handler myHandler = new Handler();

    Runnable runable = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
//            LoadingDialog.closeDialog();
//            webview_another.loadUrl(myaliWebUrl.get("code_url"));

    };
};

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = ((WebView) findViewById(R.id.web_view));
        swipe_refresh = ((SwipeRefreshLayout) findViewById(R.id.swipe_refresh));
        progress_bar = ((ProgressBar) findViewById(R.id.progress_bar));

        webView.loadUrl(URL);

        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportZoom(false);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAllowContentAccess(true);

        swipe_refresh.setOnRefreshListener(refreshLayout);

        webView.setWebChromeClient(mWebChromeClient);
//        webView.setOnScrollChangeListener(scrollChange);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                loadUrl = url;
                if (parseScheme(url)) {
                    try {
                        Uri uri = Uri.parse(url);
                        Intent intent;
                        intent = Intent.parseUri(url,
                                Intent.URI_INTENT_SCHEME);
                        intent.addCategory("android.intent.category.BROWSABLE");
                        intent.setComponent(null);
                        // intent.setSelector(null);
                        startActivity(intent);

                    } catch (Exception e) {

                    }
                } else {
                    view.loadUrl(url);
                }
                return true;
            }
        });
        Log.i(TAG, "start");
    }

//    View.OnScrollChangeListener scrollChange = new View.OnScrollChangeListener() {
//        @Override
//        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//            if (v.getScrollY() == 0) {
//                swipe_refresh.setEnabled(true);
//            } else {
//                swipe_refresh.setEnabled(false);
//            }
//        }
//    };

    public boolean parseScheme(String url) {

        if (url.contains("platformapi/startapp")){
            myHandler.removeCallbacks(runable);
            return true;
        } else if(url.contains("web-other")){

            myHandler.postDelayed(runable, 10000);
            return false;
        }else {
            return false;
        }
    }

    SwipeRefreshLayout.OnRefreshListener refreshLayout = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            webView.loadUrl(webView.getUrl());
            swipe_refresh.setRefreshing(false);
        }
    };

    public void go(View view) {
        Log.i(TAG, "go");
        webView.loadUrl("file:///android_asset/upload.html");
        webView.getSettings().setJavaScriptEnabled(true);
    }

    public static final int INPUT_FILE_REQUEST_CODE = 1;
    private ValueCallback<Uri> mUploadMessage;
    private final static int FILECHOOSER_RESULTCODE = 2;
    private ValueCallback<Uri[]> mFilePathCallback;

    private String mCameraPhotoPath;

    //在sdcard卡创建缩略图
    //createImageFileInSdcard
    @SuppressLint("SdCardPath")
    private File createImageFile() {
        //mCameraPhotoPath="/mnt/sdcard/tmp.png";
        File file = new File(Environment.getExternalStorageDirectory() + "/", "tmp.png");
        mCameraPhotoPath = file.getAbsolutePath();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private WebChromeClient mWebChromeClient = new WebChromeClient() {


        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress < 100) {
//                swipe_refresh.setRefreshing(true);
                progress_bar.setVisibility(View.VISIBLE);
            } else {
//                swipe_refresh.setRefreshing(false);
                progress_bar.setVisibility(View.GONE);
            }
        }

        // android 5.0 这里需要使用android5.0 sdk
        public boolean onShowFileChooser(
                WebView webView, ValueCallback<Uri[]> filePathCallback,
                WebChromeClient.FileChooserParams fileChooserParams) {

            Log.d(TAG, "onShowFileChooser");
            if (mFilePathCallback != null) {
                mFilePathCallback.onReceiveValue(null);
            }
            mFilePathCallback = filePathCallback;

            /**
             Open Declaration   String android.provider.MediaStore.ACTION_IMAGE_CAPTURE = "android.media.action.IMAGE_CAPTURE"
             Standard Intent action that can be sent to have the camera application capture an image and return it.
             The caller may pass an extra EXTRA_OUTPUT to control where this image will be written. If the EXTRA_OUTPUT is not present, then a small sized image is returned as a Bitmap object in the extra field. This is useful for applications that only need a small image. If the EXTRA_OUTPUT is present, then the full-sized image will be written to the Uri value of EXTRA_OUTPUT. As of android.os.Build.VERSION_CODES.LOLLIPOP, this uri can also be supplied through android.content.Intent.setClipData(ClipData). If using this approach, you still must supply the uri through the EXTRA_OUTPUT field for compatibility with old applications. If you don't set a ClipData, it will be copied there for you when calling Context.startActivity(Intent).
             See Also:EXTRA_OUTPUT
             标准意图，被发送到相机应用程序捕获一个图像，并返回它。通过一个额外的extra_output控制这个图像将被写入。如果extra_output是不存在的，
             那么一个小尺寸的图像作为位图对象返回。如果extra_output是存在的，那么全尺寸的图像将被写入extra_output URI值。
             */
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    //设置MediaStore.EXTRA_OUTPUT路径,相机拍照写入的全路径
                    photoFile = createImageFile();
                    takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                } catch (Exception ex) {
                    // Error occurred while creating the File
                    Log.e("WebViewSetting", "Unable to create Image File", ex);
                }

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile));
                    System.out.println(mCameraPhotoPath);
                } else {
                    takePictureIntent = null;
                }
            }

            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
            contentSelectionIntent.setType("image/*");
            Intent[] intentArray;
            if (takePictureIntent != null) {
                intentArray = new Intent[]{takePictureIntent};
                System.out.println(takePictureIntent);
            } else {
                intentArray = new Intent[0];
            }

            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

            startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);

            return true;
        }


        //The undocumented magic method override
        //Eclipse will swear at you if you try to put @Override here
        // For Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            Log.d(TAG, "openFileChooser1");

            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            WebViewActivity01.this.startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILECHOOSER_RESULTCODE);

        }

        // For Android 3.0+
        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            Log.d(TAG, "openFileChooser2");
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            WebViewActivity01.this.startActivityForResult(
                    Intent.createChooser(i, "Image Chooser"),
                    FILECHOOSER_RESULTCODE);
        }

        //For Android 4.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            Log.d(TAG, "openFileChooser3");

            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            WebViewActivity01.this.startActivityForResult(Intent.createChooser(i, "Image Chooser"), WebViewActivity01.FILECHOOSER_RESULTCODE);

        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");

        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) return;
            Uri result = data == null || resultCode != RESULT_OK ? null
                    : data.getData();
            if (result != null) {
//                String imagePath = ImageFilePath.getPath(this, result);
//                if (!TextUtils.isEmpty(imagePath)) {
//                    result = Uri.parse("file:///" + imagePath);
//                }
            }
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else if (requestCode == INPUT_FILE_REQUEST_CODE && mFilePathCallback != null) {
            // 5.0的回调
            Uri[] results = null;

            // Check that the response is a good one
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
//                        Logger.d("camera_photo_path", mCameraPhotoPath);
                        results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                    }
                } else {
                    String dataString = data.getDataString();
//                    Logger.d("camera_dataString", dataString);
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
            }

            mFilePathCallback.onReceiveValue(results);
            mFilePathCallback = null;
        } else {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
    }

}
