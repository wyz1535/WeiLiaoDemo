package com.leyifu.weiliaodemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.leyifu.weiliaodemo.R;
import com.leyifu.weiliaodemo.interf.OnToggleStateListener;
import com.leyifu.weiliaodemo.view.MyToggle;

public class SelectorAddressActivity extends AppCompatActivity {

    private static final String TAG = SelectorAddressActivity.class.getSimpleName();
    private MyToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_address);

        init();
    }

    private void init() {
        toggle = (MyToggle) findViewById(R.id.my_toggle);
        //设置开关显示所用的图片
        toggle.setImageRes(R.drawable.bkg_switch, R.drawable.bkg_switch, R.drawable.btn_slip);

        //设置开关的默认状态    true开启状态
        toggle.setToggleState(true);

        //设置开关的监听
        toggle.setOnToggleStateListener(new OnToggleStateListener() {

            @Override
            public void onToggleState(boolean state) {
                // TODO Auto-generated method stub
                if(state){
                    Toast.makeText(getApplicationContext(), "开关开启", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "开关关闭", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
