package com.example.wuxio.hencodertest.practiceFlip;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.wuxio.hencodertest.R;

public class Flip1Activity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, Flip1Activity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
    }
}
