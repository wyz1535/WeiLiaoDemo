package com.leyifu.weiliaodemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.leyifu.weiliaodemo.R;

public class MIUIAnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_text_anim;
    private Button btn_picture_anim01;
    private Button btn_picture_anim02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miuianimation);

        btn_text_anim = ((Button) findViewById(R.id.btn_text_anim));
        btn_picture_anim01 = ((Button) findViewById(R.id.btn_picture_anim01));
        btn_picture_anim02 = ((Button) findViewById(R.id.btn_picture_anim02));

        btn_text_anim.setOnClickListener(this);
        btn_picture_anim01.setOnClickListener(this);
        btn_picture_anim02.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_text_anim:
                startActivity(new Intent(MIUIAnimationActivity.this, ScrollViewActivity.class));
                break;
            case R.id.btn_picture_anim01:
                startActivity(new Intent(MIUIAnimationActivity.this, RecycleGridViewActivity.class));
                break;
            case R.id.btn_picture_anim02:
                startActivity(new Intent(MIUIAnimationActivity.this, RecycleViewActivity.class));
                break;


        }
    }
}
