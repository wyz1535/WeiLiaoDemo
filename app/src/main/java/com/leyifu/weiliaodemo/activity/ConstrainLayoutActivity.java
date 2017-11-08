package com.leyifu.weiliaodemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.leyifu.weiliaodemo.R;

import java.util.ArrayList;
import java.util.List;

public class ConstrainLayoutActivity extends AppCompatActivity {

    private static final String TAG = "ConstrainLayoutActivity";
    private List<String> list = new ArrayList<>();
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constrain_layout);

        list.add("001");
        list.add("002");
        list.add("003");
        list.add("004");

        button = ((Button) findViewById(R.id.button));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < list.size(); i++) {
                    Log.e(TAG, "onClick: " );
                }
            }
        });
    }
}
