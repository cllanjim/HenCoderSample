package com.example.zanview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.zanview.view.ScrollPartTextView;
import com.example.zanview.view.TintImageView;
import com.example.zanview.view.ZanImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private TintImageView      mZanView;
    private ZanImageView       mZanImageView;
    private ScrollPartTextView mTextView;

    private void assignViews() {
        mZanView = findViewById(R.id.zanView);
        mZanImageView = findViewById(R.id.zanImageView);
        mZanView.setOnClickListener(this);
        //        mZanImageView.setOnClickListener(this);
        mTextView = findViewById(R.id.scrollPart);
        mTextView.setOnClickListener(this);
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
            case R.id.zanView:
                changeImageColor();
                break;

            //            case R.id.zanImageView:
            //                animateZan();
            //                break;

            case R.id.scrollPart:
                changeTextView();
                break;
            default:
                break;
        }
    }

    private void animateZan() {
        mZanImageView.animateZan();
        //        mZanImageView.reverseAnimateZan();

        //        if (mZanImageView.isZan()) {
        //        } else {
        //        }
    }

    private void changeImageColor() {
        Log.i(TAG, "changeImageColor:" + "");
        mZanView.setColor(Color.RED);
    }

    private void changeTextView() {
        mTextView.animateScroll();
    }
}
