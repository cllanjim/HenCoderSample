package com.example.wuxio.hencodertest.views.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class DrawRect extends View {

    private Paint mPaint;

    public DrawRect(Context context) {
        this(context, null, 0);
    }

    public DrawRect(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawRect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setTextSize(80);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(100, 100, 600, 600, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(700, 100, 1200, 600, mPaint);


    }
}
