package com.example.wuxio.hencodertest.views.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class DrawOval extends View {

    private Paint mPaint;

    public DrawOval(Context context) {
        this(context, null, 0);
    }

    public DrawOval(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawOval(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        RectF rect = new RectF(50, 50, 350, 200);
        canvas.drawOval(rect, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        rect.set(400, 50, 700, 200);
        canvas.drawOval(rect, mPaint);
    }
}
