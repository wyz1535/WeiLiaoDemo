package com.leyifu.weiliaodemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.leyifu.weiliaodemo.R;

public class BezierActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_ripple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);

        init();
    }

    private void init() {
        btn_ripple = ((Button) findViewById(R.id.btn_ripple));
        btn_ripple.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ripple:
                startActivity(new Intent(this, RippleActivity.class));
                break;
        }
    }
}
