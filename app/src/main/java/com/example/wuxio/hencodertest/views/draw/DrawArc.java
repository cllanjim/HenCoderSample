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

public class DrawArc extends View {

    private Paint mPaint;
    private RectF mRectF;

    public DrawArc(Context context) {
        this(context, null, 0);
    }

    public DrawArc(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawArc(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(80);
        mRectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.FILL);
        mRectF.set(50, getHeight() / 2 - 500, getWidth() - 50, getHeight() / 2 + 500);
        canvas.drawArc(mRectF, 10, 160, false, mPaint);
        canvas.drawArc(mRectF, -100, 100, true, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(mRectF, -180, 60, false, mPaint);
        canvas.drawArc(mRectF, -115, 10, true, mPaint);

    }
}
