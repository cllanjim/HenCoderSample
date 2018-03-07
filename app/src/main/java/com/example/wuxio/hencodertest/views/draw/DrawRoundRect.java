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

public class DrawRoundRect extends View {

    private Paint mPaint;
    private RectF mRectF;

    public DrawRoundRect(Context context) {
        this(context, null, 0);
    }

    public DrawRoundRect(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawRoundRect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setTextSize(80);
        mRectF = new RectF(100, 100, 500, 300);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(mRectF, 50, 50, mPaint);
    }
}
