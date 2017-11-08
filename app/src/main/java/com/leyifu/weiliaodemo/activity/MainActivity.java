package com.leyifu.weiliaodemo.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.leyifu.weiliaodemo.R;
import com.leyifu.weiliaodemo.adapter.ConversationListAdapterEx;
import com.leyifu.weiliaodemo.bean.ServiceVerBean;
import com.leyifu.weiliaodemo.interf.OnDownloadListeren;
import com.leyifu.weiliaodemo.util.UtilsDown;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongContext;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final int SERVICE_VERSION_DATA = 1;
    private Button bt_rong_could;
    private Button bt_http, btn_rx_java, btn_retrofit_rx_java, btn_video_play, btn_view_canvas;
    private Button bt_ok_http, btn_selector_address, btn_tab_layout, btn_web_view, btn_bear_line, btn_draw_arc, webView01;
    private Button btn_open_native_app, btn_Green_dao, btn_constrain_layout, btn_immersive, btn_view, btn_recycler_view_card, btn_scroll_event;
    private int verCode;

    Handler handler = new Handler() {
        private ServiceVerBean serviceVerBean;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SERVICE_VERSION_DATA:
                    serviceVerBean = ((ServiceVerBean) msg.obj);
                    int versionCode = serviceVerBean.getVersionCode();
                    if (verCode == versionCode) {
                        Toast.makeText(MainActivity.this, "暂不需更新", Toast.LENGTH_SHORT).show();
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("更新提示")
                                .setMessage(serviceVerBean.getContent())
                                .setNegativeButton("不更新", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(MainActivity.this, "更新已取消", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String url = serviceVerBean.getUrl();
                                        downLoad(url);
                                    }
                                });
                        dialog.show();

                    }
                    break;
                default:
                    break;
            }
        }

    };
    private Conversation.ConversationType[] mConversationsTypes;
    private Button btn_app_togo_app;
    private View viewById;
    private static final int PREMISSIONCODE = 1;
    String[] premissionArray = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    List<String> premissionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_rong_could = ((Button) findViewById(R.id.bt_rong_could));
        bt_http = ((Button) findViewById(R.id.bt_http));
        bt_ok_http = ((Button) findViewById(R.id.bt_ok_http));
        btn_open_native_app = ((Button) findViewById(R.id.btn_open_native_app));
        btn_constrain_layout = ((Button) findViewById(R.id.btn_constrain_layout));
        btn_immersive = ((Button) findViewById(R.id.btn_immersive));
        btn_view = ((Button) findViewById(R.id.btn_view));
        btn_Green_dao = ((Button) findViewById(R.id.btn_Green_dao));
        btn_recycler_view_card = ((Button) findViewById(R.id.btn_recycler_view_card));
        btn_app_togo_app = ((Button) findViewById(R.id.btn_app_togo_app));
        btn_scroll_event = ((Button) findViewById(R.id.btn_scroll_event));
        btn_selector_address = ((Button) findViewById(R.id.btn_selector_address));
        btn_tab_layout = ((Button) findViewById(R.id.btn_tab_layout));
        btn_web_view = ((Button) findViewById(R.id.btn_web_view));
        btn_bear_line = ((Button) findViewById(R.id.btn_bear_line));
        btn_draw_arc = ((Button) findViewById(R.id.btn_draw_arc));
        webView01 = ((Button) findViewById(R.id.webView01));
        btn_rx_java = ((Button) findViewById(R.id.btn_rx_java));
        btn_retrofit_rx_java = ((Button) findViewById(R.id.btn_retrofit_rx_java));
        btn_video_play = ((Button) findViewById(R.id.btn_video_play));
        btn_view_canvas = ((Button) findViewById(R.id.btn_view_canvas));

        bt_rong_could.setOnClickListener(this);
        bt_http.setOnClickListener(this);
        bt_ok_http.setOnClickListener(this);
        btn_open_native_app.setOnClickListener(this);
        btn_constrain_layout.setOnClickListener(this);
        btn_immersive.setOnClickListener(this);
        btn_view.setOnClickListener(this);
        btn_Green_dao.setOnClickListener(this);
        btn_recycler_view_card.setOnClickListener(this);
        btn_app_togo_app.setOnClickListener(this);
        btn_scroll_event.setOnClickListener(this);
        btn_selector_address.setOnClickListener(this);
        btn_tab_layout.setOnClickListener(this);
        btn_web_view.setOnClickListener(this);
        btn_bear_line.setOnClickListener(this);
        btn_draw_arc.setOnClickListener(this);
        webView01.setOnClickListener(this);
        btn_rx_java.setOnClickListener(this);
        btn_retrofit_rx_java.setOnClickListener(this);
        btn_video_play.setOnClickListener(this);
        btn_view_canvas.setOnClickListener(this);

//        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri data = intent.getData();
            String id = data.getQueryParameter("id");
            Log.e("MainActivity", "onCreate: id" + id);
        }


        for (String premission : premissionArray) {
            if (ContextCompat.checkSelfPermission(this, premission) != PackageManager.PERMISSION_GRANTED) {
//                premissionList.add(premission);
                ActivityCompat.requestPermissions(this, new String[]{premission}, PREMISSIONCODE);
            }
        }
//        ActivityCompat.requestPermissions(this,premissionList.toArray(new String[premissionList.size()]),PREMISSIONCODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PREMISSIONCODE:
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(MainActivity.this, "申请成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "申请失败", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void installApk(File file) {
        Toast.makeText(this, "下载完成", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivityForResult(intent, 200);
    }

    private void downLoad(String url) {
        File downloadFile = null;

        String fileName = url.substring(url.lastIndexOf("/") + 1);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            downloadFile = new File(Environment.getExternalStorageDirectory(), fileName);
        } else {
            downloadFile = new File(getFilesDir(), fileName);
        }
        UtilsDown.downUpdate(MainActivity.this, url, downloadFile, new OnDownloadListeren() {
            @Override
            public void onDownloadSuccessed(File file) {
                installApk(file);
            }

            @Override
            public void onDownloadFailed() {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED && requestCode == 200) {
            Toast.makeText(this, "安装完成", Toast.LENGTH_SHORT).show();
//            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_rong_could:
                startActivity(new Intent(this, RongCouldAcitivity.class));
                break;
            case R.id.bt_http:
                startActivity(new Intent(this, HttpActivity.class));
                break;
            case R.id.bt_ok_http:
                startActivity(new Intent(this, OKHttpActivity.class));
                break;
            case R.id.btn_open_native_app:
                verCode = VersionName.getVerCode(this);
                if (UtilsDown.utilsCheckNetWork(this)) {
                    checkUpdate();
                } else {
                    Toast.makeText(this, "没有连接网络", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_constrain_layout:
                startActivity(new Intent(this, ConstrainLayoutActivity.class));
                break;
            case R.id.btn_immersive:
                startActivity(new Intent(this, ImmersiveActivity.class));
                break;
            case R.id.btn_view:
                startActivity(new Intent(this, ViewActivity.class));
                break;
            case R.id.btn_Green_dao:
                startActivity(new Intent(this, MIUIAnimationActivity.class));
                break;
            case R.id.btn_recycler_view_card:
                startActivity(new Intent(this, RecyclerViewCard.class));
                break;
            case R.id.btn_app_togo_app:
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.leyifu.makefriend");
                if (intent != null) {
//                    ComponentName component = new ComponentName("com.leyifu.makefriend", "com.leyifu.makefriend.activity.WelcomeActivity");
//                    intent.setComponent(component);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "app没有下载，赶紧去下载吧", Toast.LENGTH_SHORT).show();
//                    downLoad("http://www.otooman.com/app/comic1/apk/airplane_mm.apk");
                    Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.otooman.com/app/comic1/apk/airplane_mm.apk"));
//                    intent.setAction("android.intent.action.VIEW");
//                    intent.setData(Uri.parse("http://www.otooman.com/app/comic1/apk/airplane_mm.apk"));
                    startActivity(viewIntent);
                }
                break;
            case R.id.btn_scroll_event:
                startActivity(new Intent(MainActivity.this, RecyclerAndRecyclerViewActivity.class));
                break;
            case R.id.btn_selector_address:
                startActivity(new Intent(MainActivity.this, SelectorAddressActivity.class));
                break;
            case R.id.btn_tab_layout:
                startActivity(new Intent(MainActivity.this, TabLayoutActivity.class));
                break;
            case R.id.btn_web_view:
                startActivity(new Intent(MainActivity.this, WebViewActivity.class));
                break;
            case R.id.webView01:
                startActivity(new Intent(MainActivity.this, WebViewActivity01.class));
                break;
            case R.id.btn_bear_line:
                startActivity(new Intent(MainActivity.this, BezierActivity.class));
                break;
            case R.id.btn_draw_arc:
                startActivity(new Intent(MainActivity.this, DrawArcActivity.class));
                break;
            case R.id.btn_rx_java:
                startActivity(new Intent(MainActivity.this, RxJavaActivity.class));
                break;
            case R.id.btn_retrofit_rx_java:
                startActivity(new Intent(MainActivity.this, RetrofitRxJavaActivity.class));
                break;
            case R.id.btn_video_play:
                startActivity(new Intent(MainActivity.this, VideosPlayActivity.class));
                break;
            case R.id.btn_view_canvas:
//                startActivity(new Intent(MainActivity.this,ViewCanvasActivity.class));
                break;
            default:
                break;
        }
    }

    private void checkUpdate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient httpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .get()
                        .url("http://192.168.0.105:8080/update/update.json")
                        .build();
                try {
                    Response response = httpClient.newCall(request).execute();
                    String body = response.body().string();
                    Log.e(TAG, "checkUpdate: " + body);
                    ServiceVerBean serviceVerBean = new Gson().fromJson(body, ServiceVerBean.class);
                    Message message = handler.obtainMessage();
                    message.what = SERVICE_VERSION_DATA;
                    message.obj = serviceVerBean;
                    message.sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private ConversationListFragment mConversationListFragment = null;
    private boolean isDebug;

    private Fragment initConversationList() {
        if (mConversationListFragment == null) {
            ConversationListFragment listFragment = new ConversationListFragment();
            listFragment.setAdapter(new ConversationListAdapterEx(RongContext.getInstance()));
            Uri uri;
            if (isDebug) {
                uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                        .appendPath("conversationlist")
                        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "true") //设置私聊会话是否聚合显示
                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//群组
                        .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                        .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                        .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "true")
                        .build();
                mConversationsTypes = new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
                        Conversation.ConversationType.GROUP,
                        Conversation.ConversationType.PUBLIC_SERVICE,
                        Conversation.ConversationType.APP_PUBLIC_SERVICE,
                        Conversation.ConversationType.SYSTEM,
                        Conversation.ConversationType.DISCUSSION
                };

            } else {
                uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                        .appendPath("conversationlist")
                        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                        .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                        .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                        .build();
                mConversationsTypes = new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
                        Conversation.ConversationType.GROUP,
                        Conversation.ConversationType.PUBLIC_SERVICE,
                        Conversation.ConversationType.APP_PUBLIC_SERVICE,
                        Conversation.ConversationType.SYSTEM
                };
            }
            listFragment.setUri(uri);
            mConversationListFragment = listFragment;
            return listFragment;
        } else {
            return mConversationListFragment;
        }
    }
}
