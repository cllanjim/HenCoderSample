package com.example.scrolltouch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.scrolltouch.views.TestCanvasIsNew;

public class MainActivity extends AppCompatActivity {

    private TestCanvasIsNew mTestCanvas;

    private void assignViews() {
        mTestCanvas = findViewById(R.id.testCanvas);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
    }

    public void click00(View view) {
        mTestCanvas.start();
    }
}
