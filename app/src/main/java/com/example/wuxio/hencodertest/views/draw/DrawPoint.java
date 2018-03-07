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

public class DrawPoint extends View {

    private Paint mPaint;

    public DrawPoint(Context context) {
        this(context, null, 0);
    }

    public DrawPoint(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawPoint(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        mPaint.setStrokeWidth(80);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(150, 150, mPaint);

        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(300, 150, mPaint);

        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawPoint(450, 150, mPaint);
    }
}
