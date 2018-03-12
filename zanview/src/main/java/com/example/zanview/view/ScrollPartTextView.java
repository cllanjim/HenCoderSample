package com.example.zanview.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.AttributeSet;

/**
 * Created by LiuJin on 2018-03-10:14:25
 *
 * @author wuxio
 */
public class ScrollPartTextView extends android.support.v7.widget.AppCompatTextView {

    private static final String TAG = "ScrollPartTextView";
    private Rect                  mBound;
    private RectF                 mBoundF;
    private ValueAnimator         mAnimator;
    private int                   mDuration;
    private AnimateUpdateListener mUpdateListener;
    private AnimateEndListener    mEndListener;
    private float                 mFraction;

    public ScrollPartTextView(Context context) {
        this(context, null, 0);
    }

    public ScrollPartTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollPartTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mBound = new Rect();
        mBoundF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (mBoundF.isEmpty()) {
            super.onDraw(canvas);
        } else {
            CharSequence text = getText();
            setText(text.subSequence(0, text.length() - 1));
            super.onDraw(canvas);

            float maxDy = mBoundF.bottom - mBoundF.top;
            float dy = maxDy * mFraction;

            canvas.save();
            canvas.clipRect(mBoundF);
            canvas.translate(0, dy);
            setText(text);
            super.onDraw(canvas);
            canvas.translate(0, dy - maxDy);
            canvas.restore();
        }
    }

    private ValueAnimator getAnimator() {
        if (mAnimator == null) {
            resetAnimator(400);
        }
        return mAnimator;
    }

    private void resetAnimator(int duration) {
        if (mDuration != duration) {
            mDuration = duration;
            mAnimator = ValueAnimator.ofInt(0, mDuration);
            if (mUpdateListener == null) {
                mUpdateListener = new AnimateUpdateListener();
            }
            mAnimator.addUpdateListener(mUpdateListener);
            if (mEndListener == null) {
                mEndListener = new AnimateEndListener();
            }
            mAnimator.addListener(mEndListener);
        }
    }

    /**
     * @param position 返回指定位置字符的坐标
     * @return 包含坐标信息
     */
    private RectF getTextCoordinate(int position) {

        Layout layout = getLayout();

        mBoundF.setEmpty();
        mBound.setEmpty();

        int line = layout.getLineForOffset(position);
        layout.getLineBounds(line, mBound);
        //字符顶部y坐标
        int yAxisTop = mBound.top;
        //字符底部y坐标
        int yAxisBottom = mBound.bottom;
        //字符左边x坐标
        float xAxisLeft = layout.getPrimaryHorizontal(position);
        //字符右边x坐标
        float xAxisRight;
        if (position == getText().length() - 1) {
            xAxisRight = getWidth();
        } else {
            xAxisRight = layout.getPrimaryHorizontal(position + 1);
        }
        mBoundF.set(xAxisLeft, yAxisTop, xAxisRight, yAxisBottom);
        return mBoundF;
    }

    public void setDuration(int duration) {
        resetAnimator(duration);
    }

    public void animateScroll() {
        getTextCoordinate(getText().length() - 1);
        getAnimator().start();
    }

    //============================内部类============================

    class AnimateUpdateListener implements ValueAnimator.AnimatorUpdateListener {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mFraction = animation.getAnimatedFraction();
            invalidate((int) mBoundF.left, (int) mBoundF.top, (int) mBoundF.right, (int) mBoundF.bottom);
        }
    }


    class AnimateEndListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mBoundF.setEmpty();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
}
