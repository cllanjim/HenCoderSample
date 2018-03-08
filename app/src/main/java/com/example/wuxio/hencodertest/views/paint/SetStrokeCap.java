package com.example.wuxio.hencodertest.views.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class SetStrokeCap extends View {

    private Paint mPaint;

    public SetStrokeCap(Context context) {
        this(context, null, 0);
    }

    public SetStrokeCap(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SetStrokeCap(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaint.setStrokeWidth(80);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawLine(80, 80, 480, 80, mPaint);

        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(80, 240, 480, 240, mPaint);

        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(80, 400, 480, 400, mPaint);

        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.RED);
        canvas.drawLine(80, 0, 80, 800, mPaint);
        canvas.drawLine(480, 0, 480, 800, mPaint);
    }
}
