package com.example.common.zan;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.Scroller;

import com.example.common.BaseMeasureView;

import java.util.Locale;

/**
 * Created by LiuJin on 2018-03-14:7:16
 * 显示一个数字,可以上下滚动 增加/减少 数字
 *
 * @author wuxio
 */
public class ZanCountViewV3 extends BaseMeasureView {

    private static final String TAG = "TestCanvasIsNew";
    protected Paint           mPaint;
    private   Scroller        mScroller;
    private   VelocityTracker mTracker;

    private float       mScrollDistance;
    private PaintHelper mPaintHelper;

    private int   mText;
    private float mY;

    public ZanCountViewV3(Context context) {
        this(context, null, 0);
    }

    public ZanCountViewV3(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZanCountViewV3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(150);
        float fontSpacing = mPaint.getFontSpacing();
        mPaintHelper = new PaintHelper(0, 500, fontSpacing);
        mY = getPaddingTop() + fontSpacing;

        mScroller = new Scroller(context);
    }

    @Override
    protected int getAtMostWidth() {
        return 500;
    }

    @Override
    protected int getAtMostHeight() {
        return 500;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawText(String.valueOf(mText), 50, mY, mPaint);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if (mScroller.computeScrollOffset()) {
            Log.i(TAG, "computeScroll:" + "fling after");
            int y = mScroller.getCurrY();
            float v = y - lastY;
            lastY = y;
            float v1 = mScrollDistance += v;
            mPaintHelper.update(mScrollDistance);

            invalidate();
        }

        String format = String.format(Locale.CHINA, "%.4f", mScrollDistance);
        Log.i(TAG, "computeScroll:" + format);
    }

    float lastX;
    float lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mTracker == null) {
            mTracker = VelocityTracker.obtain();
        }

        mTracker.addMovement(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if (!mScroller.isFinished()) { //fling
                    mScroller.abortAnimation();
                }
                mTracker.clear();

                lastX = event.getX();
                lastY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                float disX = x - lastX;
                float disY = y - lastY;

                lastX = x;
                lastY = y;

                float v = mScrollDistance += disY;
                mPaintHelper.update(mScrollDistance);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mTracker.computeCurrentVelocity(1000);
                float xVelocity = mTracker.getXVelocity();
                float yVelocity = mTracker.getYVelocity();

                lastX = event.getX();
                lastY = event.getY();

                Log.i(TAG, "onTouchEvent:" + "fling");
                mScroller.fling(
                        (int) lastX,
                        (int) lastY,
                        (int) xVelocity,
                        (int) yVelocity,
                        0,
                        5000,
                        0,
                        5000
                );
                invalidate();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTracker != null) {
            mTracker.recycle();
        }
    }

    //============================内部类============================
    class PaintHelper {

        int   start;
        int   end;
        float totalDistance;
        float fontSpacing;

        public PaintHelper(int start, int end, float fontSpacing) {
            this.start = start;
            this.end = end;
            this.fontSpacing = fontSpacing;
            totalDistance = (end - start) * fontSpacing;
        }

        public void setFontSpacing(float fontSpacing) {
            resetTotal(start, end, fontSpacing);
            this.fontSpacing = fontSpacing;
        }

        public void setStart(int start) {
            this.start = start;
            resetTotal(start, end, fontSpacing);
        }

        public void setEnd(int end) {
            this.end = end;
            resetTotal(start, end, fontSpacing);
        }

        private void resetTotal(int start, int end, float fontSpacing) {
            totalDistance = fontSpacing * (end - start);
        }

        public void update(float distance) {
            float v = distance * 2 / totalDistance;
            float v1 = start + end * v;
            mText = (int) v1;
        }
    }
}
