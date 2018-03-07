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

public class SetColor extends View {

    private Paint mPaint;

    public SetColor(Context context) {
        this(context, null, 0);
    }

    public SetColor(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SetColor(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.parseColor("#009688"));
        canvas.drawRect(50, 50, 430, 280, mPaint);

        mPaint.setColor(Color.parseColor("#FF9800"));
        canvas.drawLine(500, 50, 950, 200, mPaint);

        mPaint.setTextSize(50);
        mPaint.setColor(Color.parseColor("#E91E63"));
        canvas.drawText("HenCoder", 1000, 130, mPaint);
    }
}
