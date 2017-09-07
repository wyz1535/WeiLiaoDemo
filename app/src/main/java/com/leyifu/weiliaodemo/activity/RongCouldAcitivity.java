package com.leyifu.weiliaodemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.leyifu.weiliaodemo.R;

import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class RongCouldAcitivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RongCouldAcitivity.class.getSimpleName();
    //    String Token = "govlbpf+cQFdQyI3QapZpuGUvoQ8oXg2dfx9x7S7PYNVG60dYvPG0GqLwDtlLUUzL6g+PgY0FPhwOIZLjQXnXYOQPVWgUFZL";//admin01
    String Token = "KCxiV6Svstv5u9NEWMrGFuGUvoQ8oXg2dfx9x7S7PYP15OMV7V4MjjNogO2+zCLm4wEhEcxE6nrHrJT+BzK07YOQPVWgUFZL";//ygkvT5Vgp
    //        String Token = "zsDF+40V+DCmgzt1E1RpgDeAshMZQIDRljbJ80X/ZTs5qVvPeU7lCSindSN4RhFdvrjF77uACuhYs+45rtcd9A==";//NnlkM1cnI
    private Button bt_alone_chat;
    private Button btn_constrain_list;
    private Map<String, Boolean> supportedConversation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rong_could_acitivity);

        bt_alone_chat = ((Button) findViewById(R.id.bt_alone_chat));
        btn_constrain_list = ((Button) findViewById(R.id.btn_constrain_list));
        bt_alone_chat.setOnClickListener(this);
        btn_constrain_list.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_alone_chat:
                initConnet();
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startPrivateChat(this, "NnlkM1cnI", "helloworld");
                }
                break;
            case R.id.btn_constrain_list:
                Log.e(TAG, "onClick: ");
                reconnect();
//                supportedConversation = new HashMap<>();
//                supportedConversation.put(Conversation.ConversationType.PRIVATE.getName(), false);
                break;
        }
    }

    private void reconnect() {

        RongIM.connect(Token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {

            }

            @Override
            public void onSuccess(String s) {
                HashMap<String, Boolean> hashMap = new HashMap<>();
                //会话类型 以及是否聚合显示
                hashMap.put(Conversation.ConversationType.PRIVATE.getName(), false);
                hashMap.put(Conversation.ConversationType.PUSH_SERVICE.getName(), true);
                hashMap.put(Conversation.ConversationType.SYSTEM.getName(), true);
                RongIM.getInstance().startConversationList(RongCouldAcitivity.this, hashMap);
//                startActivity(new Intent(RongCouldAcitivity.this, SubConversationListActivtiy.class));
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    private void initConnet() {
        /**
         * IMKit SDK调用第二步
         * 建立与服务器的连接
         */
        RongIM.connect(Token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                //Connect Token 失效的状态处理，需要重新获取 Token
                Log.e("MainActivity", "——onTokenIncorrect—-");
            }

            @Override
            public void onSuccess(String userId) {
                Log.e("MainActivity", "——onSuccess—-" + userId);
                Toast.makeText(RongCouldAcitivity.this, "connet is successd", Toast.LENGTH_SHORT).show();

                /**
                 * 启动单聊
                 * context - 应用上下文。
                 * targetUserId - 要与之聊天的用户 Id。
                 * title - 聊天的标题，如果传入空值，则默认显示与之聊天的用户名称。
                 */

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("MainActivity", "——onError—-" + errorCode);
            }
        });
    }
}
