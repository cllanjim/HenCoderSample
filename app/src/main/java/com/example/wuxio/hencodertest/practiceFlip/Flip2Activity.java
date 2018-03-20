package com.example.wuxio.hencodertest.practiceFlip;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.common.flip.FlipViewSample;
import com.example.wuxio.hencodertest.R;

public class Flip2Activity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, Flip2Activity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip2);
        final FlipViewSample sample = findViewById(R.id.sample);

        sample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sample.change();
            }
        });
    }
}
