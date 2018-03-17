package com.example.tapeline;

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

    /**
     * onDraw 中从该类获取绘制的文本,和y坐标偏移
     */
    private PaintHelper mPaintHelper;


    /**
     * 一个标记用于记录是否已经抬起手指,true,手指已经抬起
     */
    private boolean isUp;
    private int     mBaseSpace;
    private int     mLineTop;

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

    /**
     * 初始化变量,获取属性
     */
    protected void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
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

        mBaseSpace = 50;
        mLineTop = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        final int lineHeight = 300;
        final int width = getWidth();
        final int space = mBaseSpace;
        final float startY = mLineTop;
        final float startOffSet = 24;

        float lineX = startOffSet;

        while (lineX < width) {
            lineX += space;
            canvas.drawLine(lineX, startY, lineX, startY + lineHeight, mPaint);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if (mScroller.computeScrollOffset()) {
            //处理 fling 逻辑
            int x = mScroller.getCurrX();
            float v = x - lastX;
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
    //float lastY;
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
                float x = event.getX();
                float disX = x - lastX;
                lastX = x;

                //float y = event.getY();
                //float disY = y - lastY;
                //lastY = y;

                mTracker.addMovement(event);
                mPaintHelper.update(disX);
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                //float yVelocity = mTracker.getYVelocity();
                //lastY = event.getY();

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
                    //速度不够,开始回滚
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
     * 用于帮助 {@link ZanCountViewV4#onDraw(Canvas)} 获取绘制的文本,文本y方向偏移量
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

        }

        /**
         * 在 up 后如果是 fling 需要跟新最终距离,使其最终停在一个数字没有偏移的位置上
         *
         * @param scroller fling
         * @param startY   开始fling位置
         */
        void setFinalFlingX(Scroller scroller, float startY) {
            int finalY = scroller.getFinalY();

            float distance = finalY - startY;
            float finalScroll = (distance + mScrollDistance);

            //finalScroll是 baseSpacing 倍数
            if (finalScroll < 0 || finalScroll > totalDistance) {
                return;
            }

            //finalScroll不是 baseSpacing 倍数
            // 1. 先计算余数
            float extra = finalScroll % baseSpacing;
            // 2. 将余数刨除
            if (distance < 0) {
                scroller.setFinalY((int) (finalY + (baseSpacing - extra)));
            } else {
                scroller.setFinalY((int) (finalY - extra));
            }
        }

        /**
         * 当 up 后,如果没有fling操作,使用scroller 滚动
         *
         * @param scroller 滚动的 scroller
         * @param startY   开始滚动位置
         */
        void scroll(Scroller scroller, int startY) {
            float extra = mScrollDistance % baseSpacing;
            float base = baseSpacing / 2;

            if (extra < base) {
                scroller.startScroll(0, startY, 0, (int) -extra);
            } else {
                scroller.startScroll(0, startY, 0, (int) (baseSpacing - extra));
            }
        }
    }
}
