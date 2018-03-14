package com.example.zanview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zanview.view.ZanCountViewV2;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void zanClick(View view) {
//        ((ZanCountViewV2) view).add();
//        ((ZanCountViewV2) view).sub();
        ((ZanCountViewV2) view).resize();
    }
}
