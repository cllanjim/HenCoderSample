package com.example.tapeline;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.example.common.BaseLineUtil;
import com.example.common.BaseMeasureView;

import java.util.Locale;

/**
 * Created by LiuJin on 2018-03-14:7:16
 * 显示一个数字,可以上下滚动 增加/减少 数字
 *
 * @author wuxio
 */
public class TapeLineView extends BaseMeasureView {

    private static final String TAG = "TestCanvasIsNew";
    protected Paint mPaintLine;
    protected Paint mPaintLineLong;
    protected Paint mPaintText;
    protected Paint mPaintNum;

    /**
     * 用于抬起时惯性滑动和位置微调
     */
    private Scroller        mScroller;
    private VelocityTracker mTracker;
    /**
     * 最小fling速度
     */
    private int             mMinimumFlingVelocity;

    /**
     * onDraw 中从该类获取绘制的文本,和y坐标偏移
     */
    private PaintHelper mPaintHelper;

    /**
     * 一个标记用于记录是否已经抬起手指,true,手指已经抬起
     */
    private boolean isUp;

    private int mBaseSpace;
    private int mLineTop;
    private int mWidth;
    private int mHeight;

    private int   mCurrentNum;
    private float mStartOffset;
    private float mBaseLineNum;

    public TapeLineView(Context context) {
        this(context, null, 0);
    }

    public TapeLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TapeLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化变量,获取属性
     */
    protected void init(Context context, AttributeSet attrs) {

        int lineColor = Color.parseColor("#d9dcd9");

        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLine.setStyle(Paint.Style.STROKE);
        mPaintLine.setStrokeWidth(2);
        mPaintLine.setColor(lineColor);

        mPaintLineLong = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLineLong.setStyle(Paint.Style.STROKE);
        mPaintLineLong.setStrokeWidth(4);
        mPaintLineLong.setColor(lineColor);

        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setStrokeWidth(6);
        mPaintText.setTextSize(80);
        int textColor = Color.parseColor("#5c8e75");
        mPaintText.setStrokeCap(Paint.Cap.ROUND);
        mPaintText.setColor(textColor);
        mPaintText.setTextAlign(Paint.Align.CENTER);

        mPaintNum = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintNum.setTextSize(50);
        mPaintNum.setColor(Color.BLACK);
        mPaintNum.setTextAlign(Paint.Align.CENTER);
        mBaseLineNum = BaseLineUtil.getBaseLine(mPaintNum);

        mMinimumFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
        mScroller = new Scroller(context);
    }

    @Override
    protected int getAtMostWidth() {
        return -1;
    }

    @Override
    protected int getAtMostHeight() {
        return -1;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBaseSpace = 25;
        mLineTop = h / 2;

        mCurrentNum = 480;
        mPaintHelper = new PaintHelper(0, 5000, mBaseSpace, mCurrentNum);

        mWidth = getWidth();
        mHeight = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        final int min = mPaintHelper.start;
        final int max = mPaintHelper.end;
        final int lineHeight = 40;
        final int lineHeightLong = 80;
        final int space = mBaseSpace;
        final float startY = mLineTop;
        final float textY = mLineTop - 100;
        final float stopY = startY + lineHeight;
        final float stopYLong = startY + lineHeightLong;
        final float baseLimeNum = mBaseLineNum;

        //绘制背景色
        canvas.save();
        canvas.clipRect(0, startY, getWidth(), startY + 160);
        canvas.drawColor(Color.parseColor("#f5f8f5"));
        canvas.restore();

        //计算游标位置
        final int width = mWidth;
        final int cursorX = mWidth >> 1;

        //绘制当前刻度
        int current = mCurrentNum;
        float lineX = cursorX + mStartOffset;
        if (current % 10 == 0) {
            canvas.drawLine(lineX, startY, lineX, stopYLong, mPaintLine);
            canvas.drawText(String.valueOf(current / 10), lineX, stopYLong + baseLimeNum, mPaintNum);
        } else {
            canvas.drawLine(lineX, startY, lineX, stopY, mPaintLine);
        }
        lineX += space;

        //绘制右边的刻度
        while (lineX < width) {
            current += 1;
            if (current > max) {
                break;
            }
            if (current % 10 == 0) {
                canvas.drawLine(lineX, startY, lineX, stopYLong, mPaintLine);
                canvas.drawText(String.valueOf(current / 10), lineX, stopYLong + baseLimeNum, mPaintNum);
            } else {
                canvas.drawLine(lineX, startY, lineX, stopY, mPaintLine);
            }
            lineX += space;
        }

        //绘制左边的刻度
        lineX = cursorX - (space - mStartOffset);
        current = mCurrentNum;
        while (lineX > 0) {
            current -= 1;
            if (current < min) {
                break;
            }
            if (current % 10 == 0) {
                canvas.drawLine(lineX, startY, lineX, stopYLong, mPaintLine);
                canvas.drawText(String.valueOf(current / 10), lineX, stopYLong + baseLimeNum, mPaintNum);
            } else {
                canvas.drawLine(lineX, startY, lineX, stopY, mPaintLine);
            }
            lineX -= space;
        }

        //绘制游标
        canvas.drawLine(cursorX, startY, cursorX, stopYLong, mPaintText);

        //绘制标题
        String s = String.format(Locale.CHINA, "%.1f kg", mCurrentNum * 1f / 10);
        canvas.drawText(s, cursorX, textY, mPaintText);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if (mScroller.computeScrollOffset()) {
            //处理 fling 逻辑
            int x = mScroller.getCurrX();
            float v = lastX - x;
            lastX = x;
            mPaintHelper.update(v);
            invalidate();
        } else if (isUp) {

            isUp = false;
        }
    }

    /**
     * 记录上一次触摸坐标位置
     */
    float lastX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mTracker == null) {
            mTracker = VelocityTracker.obtain();
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                lastX = event.getX();
                //lastY = event.getY();

                //滑动时再次按下停止滑动
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                //重新
                mTracker.clear();
                mTracker.addMovement(event);
                break;

            case MotionEvent.ACTION_MOVE:
                mTracker.addMovement(event);

                float x = event.getX();
                float disX = lastX - x;
                lastX = x;

                mPaintHelper.update(disX);
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:

                //计算加速度
                mTracker.addMovement(event);
                mTracker.computeCurrentVelocity(512);
                float xVelocity = mTracker.getXVelocity();
                lastX = event.getX();

                if (Math.abs(xVelocity) > mMinimumFlingVelocity) {
                    //开始fling
                    mScroller.fling(
                            (int) lastX,
                            0,
                            (int) xVelocity,
                            0,
                            0,
                            (int) mPaintHelper.totalDistance,
                            0,
                            0
                    );

                    //修正 fling 最终位置,使其最终停在一个没有偏移的位置上
                    mPaintHelper.setFinalFlingX(mScroller, lastX);
                } else {
                    //速度不够不 fling
                    mPaintHelper.scroll(mScroller, (int) lastX);
                }
                isUp = true;
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
        //回收
        if (mTracker != null) {
            mTracker.recycle();
        }
    }

    //============================内部类============================

    /**
     * 用于帮助 {@link TapeLineView#onDraw(Canvas)} 获取绘制的文本,文本y方向偏移量
     */
    class PaintHelper {

        /**
         * 最小数字
         */
        int   start;
        /**
         * 最大数字
         */
        int   end;
        /**
         * 最大滑动距离
         */
        float totalDistance;
        /**
         * 基准间距
         */
        float baseSpacing;
        /**
         * 已经滑动距离
         */
        float mScrollDistance;

        PaintHelper(int start, int end, float baseSpacing, int current) {
            this.start = start;
            this.end = end;
            this.baseSpacing = baseSpacing;
            totalDistance = (end - start) * baseSpacing;
            mScrollDistance = (current - start) * baseSpacing;
        }

        /**
         * @param baseSpacing 设置并更新总滑动距离
         */
        void setBaseSpacing(float baseSpacing) {
            resetTotal(start, end, baseSpacing);
            this.baseSpacing = baseSpacing;
        }

        /**
         * @param start 设置并更新总滑动距离
         */
        void setStart(int start) {
            this.start = start;
            resetTotal(start, end, baseSpacing);
        }

        /**
         * @param end 设置并更新总滑动距离
         */
        void setEnd(int end) {
            this.end = end;
            resetTotal(start, end, baseSpacing);
        }

        /**
         * 更新总滑动距离
         */
        private void resetTotal(int start, int end, float fontSpacing) {
            totalDistance = fontSpacing * (end - start);
        }

        /**
         * @param distance 当滑动一小段距离时,使用该方法通知 {@link PaintHelper}
         */
        void update(float distance) {

            mScrollDistance += distance;

            if (mScrollDistance < 0) {
                mScrollDistance = 0;
            } else if (mScrollDistance > totalDistance) {
                mScrollDistance = totalDistance;
            }

            float v = mScrollDistance / baseSpacing;
            int i = (int) v;
            mCurrentNum = i;
            mStartOffset = (i - v) * baseSpacing;
        }

        /**
         * 在 up 后如果是 fling 需要跟新最终距离,使其最终停在一个数字没有偏移的位置上
         *
         * @param scroller fling
         * @param startX   开始fling位置
         */
        void setFinalFlingX(Scroller scroller, float startX) {
            int finalX = scroller.getFinalX();

            float distance = startX - finalX + mScrollDistance;

            if (distance < 0) {
                distance = 0;
            } else if (distance > totalDistance) {
                distance = totalDistance;
            }

            float v = distance / baseSpacing;
            int i = (int) v;
            float v1 = (i - v) * baseSpacing;

            scroller.setFinalX((int) (finalX - v1));
        }

        /**
         * 当 up 后,如果没有fling操作,使用scroller 滚动
         *
         * @param scroller 滚动的 scroller
         * @param startX   开始滚动位置
         */
        void scroll(Scroller scroller, int startX) {

            float v = mScrollDistance / baseSpacing;
            int i = (int) v;
            float v1 = -(i - v) * baseSpacing;
            float base = baseSpacing / 2;

            if (v1 < base) {
                scroller.startScroll(startX, 0, (int) v1, 0);
            } else {
                scroller.startScroll(startX, 0, (int) (v1 - baseSpacing), 0);
            }
        }
    }
}
