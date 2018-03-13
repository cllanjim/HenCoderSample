package com.example.zanview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zanview.view.ZanCountView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";


    private ZanCountView mZanCount;

    private void assignViews() {
        mZanCount = findViewById(R.id.zanCount);
        mZanCount.setOnClickListener(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }

        mZanCount.subZanCount();
    }
}
