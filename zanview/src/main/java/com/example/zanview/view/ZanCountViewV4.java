package com.example.zanview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.example.common.BaseMeasureView;

/**
 * Created by LiuJin on 2018-03-14:7:16
 * 显示一个数字,可以上下滚动 增加/减少 数字
 *
 * @author wuxio
 */
public class ZanCountViewV4 extends BaseMeasureView {

    private static final String TAG = "TestCanvasIsNew";
    protected Paint mPaint;

    /**
     * 用于抬起时惯性滑动和位置微调
     */
    private Scroller        mScroller;
    private VelocityTracker mTracker;
    /**
     * 最小fling速度
     */
    private int             mMinimumFlingVelocity;
    private int             mTouchSlop;

    /**
     * onDraw 中从该类获取绘制的文本,和y坐标偏移
     */
    private PaintHelper mPaintHelper;
    /**
     * 将要绘制的数字
     */
    private int         mText;
    /**
     * 绘制文字的y方向坐标
     */
    private float       mY;

    /**
     * 初始时坐标,用于还原y值
     */
    private float mYDefault;
    /**
     * 行间距
     */
    private float mFontSpacing;

    /**
     * 一个标记用于记录是否已经抬起手指,true,手指已经抬起
     */
    private boolean isUp;

    public ZanCountViewV4(Context context) {
        this(context, null, 0);
    }

    public ZanCountViewV4(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZanCountViewV4(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(150);
        mFontSpacing = mPaint.getFontSpacing();
        mPaintHelper = new PaintHelper(0, 10, mFontSpacing);
        mYDefault = mY = getPaddingTop() + mFontSpacing;

        mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mMinimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();
        mTouchSlop = configuration.getScaledTouchSlop();
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
        canvas.clipRect(0, getPaddingTop(), getRight(), mYDefault);
        canvas.drawText(String.valueOf(mText), 50, mY, mPaint);
        canvas.drawText(String.valueOf(mText + 1), 50, mY - mFontSpacing, mPaint);
        canvas.drawText(String.valueOf(mText - 1), 50, mY + mFontSpacing, mPaint);
        //mY = mYDefault;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if (mScroller.computeScrollOffset()) {
            //处理 fling 逻辑
            int y = mScroller.getCurrY();
            float v = y - lastY;
            lastY = y;
            mPaintHelper.update(v);
            invalidate();
        } else if (isUp) {
            //手指抬起之后,fling
            //Log.i(TAG, "computeScroll:" + "up fling finish, final:" + mScroller.getFinalY() + " end:" + mPaintHelper.mScrollDistance);

            isUp = false;
        }
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

                mPaintHelper.update(disY);
                invalidate();

                break;

            case MotionEvent.ACTION_UP:

                //计算加速度
                mTracker.computeCurrentVelocity(512);
                float xVelocity = mTracker.getXVelocity();
                float yVelocity = mTracker.getYVelocity();

                lastX = event.getX();
                lastY = event.getY();

                if (yVelocity > mMinimumFlingVelocity) {

                    //开始fling
                    mScroller.fling(
                            (int) lastX,
                            (int) lastY,
                            (int) xVelocity,
                            (int) yVelocity,
                            0,
                            0,
                            0,
                            (int) mPaintHelper.totalDistance
                    );

                    //Log.i(TAG, "onTouchEvent:startY: " + lastY);
                    //Log.i(TAG, "onTouchEvent:" + "up fling, final:" + mScroller.getFinalY() + " start:" + mPaintHelper.mScrollDistance);

                    //修正 fling 最终位置,使其最终停在一个没有偏移的位置上
                    mPaintHelper.setFinalFlingY(mScroller, lastY);
                    invalidate();
                } else {
                    mPaintHelper.scroll(mScroller, (int) lastY);
                    invalidate();
                }
                isUp = true;

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
        float mScrollDistance;

        PaintHelper(int start, int end, float fontSpacing) {
            this.start = start;
            this.end = end;
            this.fontSpacing = fontSpacing;
            totalDistance = (end - start) * fontSpacing;
        }


        void setFontSpacing(float fontSpacing) {
            resetTotal(start, end, fontSpacing);
            this.fontSpacing = fontSpacing;
        }

        void setStart(int start) {
            this.start = start;
            resetTotal(start, end, fontSpacing);
        }

        void setEnd(int end) {
            this.end = end;
            resetTotal(start, end, fontSpacing);
        }

        private void resetTotal(int start, int end, float fontSpacing) {
            totalDistance = fontSpacing * (end - start);
        }


        void update(float distance) {

            mScrollDistance += distance;

            if (mScrollDistance < 0) {
                mScrollDistance = 0;
            } else if (mScrollDistance > totalDistance) {
                mScrollDistance = totalDistance;
            }

            //滑动总进度
            float fraction = mScrollDistance / totalDistance;
            //根据进度计算数字
            float scrollFloat = start + (end - start) * fraction;
            //强转为int,舍弃小数位
            int i = (int) scrollFloat;
            //小数位决定位移
            float extraMove = (scrollFloat - i) * fontSpacing;

            if (distance > 0) {
                //向下滑动
                mText = i;
                mY = mYDefault + extraMove;
            } else {
                //向上滑动
                mText = i + 1;
                mY = mYDefault - (fontSpacing - extraMove);
            }

            //            Log.i(TAG, "update:mText " + i + " extra:" + String.format(Locale.CHINA, "%.4f", extraMove));
        }

        void setFinalFlingY(Scroller scroller, float startY) {
            int finalY = scroller.getFinalY();

            //Log.i(TAG, "setFinalFlingY:" + finalY);
            float distance = finalY - startY;
            float finalScroll = (distance + mScrollDistance);

            //finalScroll是 fontSpacing 倍数
            if (finalScroll < 0 || finalScroll > totalDistance) {
                return;
            }

            //finalScroll不是 fontSpacing 倍数
            // 1. 先计算余数
            float extra = finalScroll % fontSpacing;
            // 2. 将余数刨除
            if (distance < 0) {
                scroller.setFinalY((int) (finalY + (fontSpacing - extra)));
            } else {
                scroller.setFinalY((int) (finalY - extra));
            }
        }

        void scroll(Scroller scroller, int startY) {
            float extra = mScrollDistance % fontSpacing;
            float base = fontSpacing / 2;

            //Log.i(TAG, "scroll:" + extra);
            if (extra < base) {
                scroller.startScroll(0, startY, 0, (int) -extra);
            } else {
                scroller.startScroll(0, startY, 0, (int) (fontSpacing - extra));
            }
        }
    }
}
