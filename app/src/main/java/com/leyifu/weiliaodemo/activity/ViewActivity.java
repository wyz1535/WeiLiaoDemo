package com.leyifu.weiliaodemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.leyifu.weiliaodemo.R;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_view_canvas,btn_view_canvas_text,btn_view_canvas_clip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        initView();

    }

    private void initView() {
        btn_view_canvas = ((Button) findViewById(R.id.btn_view_canvas));
        btn_view_canvas_text = ((Button) findViewById(R.id.btn_view_canvas_text));
        btn_view_canvas_clip = ((Button) findViewById(R.id.btn_view_canvas_clip));

        btn_view_canvas.setOnClickListener(this);
        btn_view_canvas_text.setOnClickListener(this);
        btn_view_canvas_clip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_view_canvas:
                startActivity(new Intent(ViewActivity.this,ViewCanvasActivity.class));
                break;
            case R.id.btn_view_canvas_text:
                startActivity(new Intent(ViewActivity.this,ViewCanvasTextActivity.class));
                break;
            case R.id.btn_view_canvas_clip:
                startActivity(new Intent(ViewActivity.this,ViewCanvasClipActivity.class));
                break;
            default:
                break;
        }
    }
}
