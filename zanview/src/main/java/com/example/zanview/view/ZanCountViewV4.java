package com.example.zanview.view;

import android.content.Context;
import android.content.res.TypedArray;
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
import com.example.common.BaseMeasureGravityView;
import com.example.zanview.R;

/**
 * Created by LiuJin on 2018-03-14:7:16
 * 显示一个数字,可以上下滚动 增加/减少 数字
 *
 * @author wuxio
 */
public class ZanCountViewV4 extends BaseMeasureGravityView {

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
     * 将要绘制的数字
     */
    private int         mCurrentInt;
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

    /**
     * 初始化变量,获取属性
     */
    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.ZanCountViewV4);
        float size = typedArray.getDimension(R.styleable.ZanCountViewV4_size, 12);
        mPaint.setTextSize(size);
        int color = typedArray.getColor(R.styleable.ZanCountViewV4_color, Color.BLACK);
        mPaint.setColor(color);
        int min = typedArray.getInteger(R.styleable.ZanCountViewV4_min, 0);
        int max = typedArray.getInteger(R.styleable.ZanCountViewV4_max, 10);
        int current = mCurrentInt = typedArray.getInteger(R.styleable.ZanCountViewV4_start, 0);
        mFontSpacing = mPaint.getFontSpacing();
        mPaintHelper = new PaintHelper(min, max, mFontSpacing, current);
        typedArray.recycle();

        mYDefault = mY = BaseLineUtil.getBaseLine(mPaint);
        mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mMinimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();
    }

    @Override
    protected int getAtMostWidth() {
        return (int) (mPaint.measureText(String.valueOf(mPaintHelper.end)) + 1);
    }

    @Override
    protected int getAtMostHeight() {
        return (int) (mFontSpacing + 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        float fontSpacing = mFontSpacing;
        int current = mCurrentInt;
        float y = mY;
        Paint paint = mPaint;

        //这两个偏移是gravity支持
        int offsetLeft = mGravityLeft;
        int offsetTop = mGravityTop;

        //裁剪出一行的空间
        canvas.clipRect(0, offsetTop, getRight(), offsetTop + fontSpacing);
        //根据偏移量 mY ,绘制数字 mCurrentInt
        canvas.drawText(String.valueOf(current), offsetLeft, offsetTop + y, paint);
        //绘制 +1 的数字
        canvas.drawText(String.valueOf(current + 1), offsetLeft, offsetTop + y - fontSpacing, paint);
        //绘制 -1 的数字
        canvas.drawText(String.valueOf(current - 1), offsetLeft, offsetTop + y + fontSpacing, paint);
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

            isUp = false;
        }
    }

    /**
     * 记录上一次触摸坐标位置
     */
    float lastY;
    //float lastX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mTracker == null) {
            mTracker = VelocityTracker.obtain();
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                //lastX = event.getX();
                lastY = event.getY();

                //滑动时再次按下停止滑动
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                //重新
                mTracker.clear();
                mTracker.addMovement(event);
                break;

            case MotionEvent.ACTION_MOVE:
                //float x = event.getX();
                //float disX = x - lastX;
                //lastX = x;
                float y = event.getY();
                float disY = y - lastY;
                lastY = y;

                mTracker.addMovement(event);

                mPaintHelper.update(disY);
                invalidate();

                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                //float xVelocity = mTracker.getXVelocity();
                //lastX = event.getX();
                //计算加速度
                mTracker.addMovement(event);
                mTracker.computeCurrentVelocity(512);
                float yVelocity = mTracker.getYVelocity();
                lastY = event.getY();

                if (Math.abs(yVelocity) > mMinimumFlingVelocity) {
                    //开始fling
                    mScroller.fling(
                            0,
                            (int) lastY,
                            0,
                            (int) yVelocity,
                            0,
                            0,
                            0,
                            (int) mPaintHelper.totalDistance
                    );

                    //修正 fling 最终位置,使其最终停在一个没有偏移的位置上
                    mPaintHelper.setFinalFlingY(mScroller, lastY);
                } else {
                    //速度不够,开始回滚
                    mPaintHelper.scroll(mScroller, (int) lastY);
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
         * 行间距
         */
        float fontSpacing;
        /**
         * 已经滑动距离
         */
        float mScrollDistance;

        PaintHelper(int start, int end, float fontSpacing, int current) {
            this.start = start;
            this.end = end;
            this.fontSpacing = fontSpacing;
            totalDistance = (end - start) * fontSpacing;
            mScrollDistance = (current - start) * fontSpacing;
        }

        /**
         * @param fontSpacing 设置并更新总滑动距离
         */
        void setFontSpacing(float fontSpacing) {
            resetTotal(start, end, fontSpacing);
            this.fontSpacing = fontSpacing;
        }

        /**
         * @param start 设置并更新总滑动距离
         */
        void setStart(int start) {
            this.start = start;
            resetTotal(start, end, fontSpacing);
        }

        /**
         * @param end 设置并更新总滑动距离
         */
        void setEnd(int end) {
            this.end = end;
            resetTotal(start, end, fontSpacing);
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
                mCurrentInt = i;
                mY = mYDefault + extraMove;
            } else {
                //向上滑动
                mCurrentInt = i + 1;
                mY = mYDefault - (fontSpacing - extraMove);
            }
        }

        /**
         * 在 up 后如果是 fling 需要跟新最终距离,使其最终停在一个数字没有偏移的位置上
         *
         * @param scroller fling
         * @param startY   开始fling位置
         */
        void setFinalFlingY(Scroller scroller, float startY) {
            int finalY = scroller.getFinalY();

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

        /**
         * 当 up 后,如果没有fling操作,使用scroller 滚动
         *
         * @param scroller 滚动的 scroller
         * @param startY   开始滚动位置
         */
        void scroll(Scroller scroller, int startY) {
            float extra = mScrollDistance % fontSpacing;
            float base = fontSpacing / 2;

            if (extra < base) {
                scroller.startScroll(0, startY, 0, (int) -extra);
            } else {
                scroller.startScroll(0, startY, 0, (int) (fontSpacing - extra));
            }
        }
    }
}
