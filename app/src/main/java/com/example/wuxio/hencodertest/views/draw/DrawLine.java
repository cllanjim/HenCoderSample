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

public class DrawLine extends View {

    private Paint mPaint;

    public DrawLine(Context context) {
        this(context, null, 0);
    }

    public DrawLine(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        mPaint.setStrokeWidth(20);
        canvas.drawLine(200, 200, 800, 500, mPaint);
        float[] points = {20, 20, 120, 20, 70, 20, 70, 120, 20, 120, 120, 120, 150, 20, 250, 20, 150, 20, 150, 120, 250, 20, 250, 120, 150, 120, 250, 120};
        canvas.drawLines(points, mPaint);
    }
}
