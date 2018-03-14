package com.example.scrolltouch.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by LiuJin on 2018-03-14:7:16
 *
 * @author wuxio
 */

public class TestCanvasIsNew extends View {

    private static final String TAG = "TestCanvasIsNew";
    private Paint         mPaint;
    private ValueAnimator mAnimator;

    public TestCanvasIsNew(Context context) {
        this(context, null, 0);
    }

    public TestCanvasIsNew(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestCanvasIsNew(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(100);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        final float fontSpacing = mPaint.getFontSpacing();
        float lineHeight = fontSpacing + getPaddingTop();

        if (isRunning()) {
            float fraction = mAnimator.getAnimatedFraction();
            float dy = fraction * fontSpacing;
            canvas.translate(0, dy);
        }

        canvas.drawText(
                String.valueOf(56),
                getPaddingLeft(),
                lineHeight,
                mPaint
        );

        canvas.drawText(
                String.valueOf(57),
                getPaddingLeft(),
                lineHeight + fontSpacing,
                mPaint
        );

        canvas.drawText(
                String.valueOf(55),
                getPaddingLeft(),
                lineHeight - fontSpacing,
                mPaint
        );

        if (isRunning()) {
            invalidate();
        }
    }

    public void start() {
        if (mAnimator == null) {
            getAnimator();
        }
        if (!mAnimator.isRunning()) {
            mAnimator.setDuration(1500).start();
            Log.i(TAG, "start:" + "");
            invalidate();
        }
    }

    private ValueAnimator getAnimator() {
        if (mAnimator == null) {
            mAnimator = ValueAnimator.ofInt(0, 12);
            mAnimator.setInterpolator(new LinearInterpolator());
        }
        return mAnimator;
    }

    private boolean isRunning() {
        return mAnimator != null && mAnimator.isRunning();
    }
}
