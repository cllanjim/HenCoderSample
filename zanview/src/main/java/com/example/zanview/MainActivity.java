package com.example.zanview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zanview.view.ZanCountView;
import com.example.zanview.view.ZanImageView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ZanImageView mZanImageView;
    private ZanCountView mZanCount;

    private void assignViews() {
        mZanImageView = findViewById(R.id.zanImage);
        mZanCount = findViewById(R.id.zanCount);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
    }


    public void zanClick(View view) {

        if (mZanImageView.isZan()) {
            mZanCount.subZanCount();
            mZanImageView.reverseAnimateZan();
        } else {
            mZanCount.addZanCount();
            mZanImageView.animateZan();
        }
    }
}
