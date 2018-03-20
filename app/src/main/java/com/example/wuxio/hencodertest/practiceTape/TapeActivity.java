package com.example.wuxio.hencodertest.practiceTape;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.wuxio.hencodertest.R;

/**
 * @author wuxio
 */
public class TapeActivity extends AppCompatActivity {


    public static void start(Context context) {
        Intent starter = new Intent(context, TapeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tape);
    }
}
