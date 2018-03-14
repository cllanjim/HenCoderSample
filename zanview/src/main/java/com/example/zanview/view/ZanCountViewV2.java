package com.example.zanview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import com.example.common.BaseMeasureView;

/**
 * Created by LiuJin on 2018-03-14:7:16
 *
 * @author wuxio
 */

public class ZanCountViewV2 extends BaseMeasureView {

    private static final String TAG = "TestCanvasIsNew";
    private Paint         mPaint;
    private ValueAnimator mAnimator;

    /**
     * 当前显示的数字
     */
    private int mCurrentInt;

    /**
     * 下一个显示的数字
     */
    private int mNextInt;

    public ZanCountViewV2(Context context) {
        this(context, null, 0);
    }

    public ZanCountViewV2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZanCountViewV2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected int getAtMostWidth() {
        return 0;
    }

    @Override
    protected int getAtMostHeight() {
        return 0;
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
            canvas.clipRect(0, getPaddingTop(), getRight(), lineHeight);
            canvas.translate(0, dy);
            canvas.drawText(
                    String.valueOf(56),
                    getPaddingLeft(),
                    lineHeight,
                    mPaint
            );

            canvas.drawText(
                    String.valueOf(55),
                    getPaddingLeft(),
                    lineHeight - fontSpacing,
                    mPaint
            );

            canvas.drawText(
                    String.valueOf(57),
                    getPaddingLeft(),
                    lineHeight + fontSpacing,
                    mPaint
            );

            invalidate();
        }

        canvas.drawText(
                String.valueOf(56),
                getPaddingLeft(),
                lineHeight,
                mPaint
        );
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
