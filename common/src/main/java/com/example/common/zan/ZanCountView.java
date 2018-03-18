package com.example.common.zan;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import com.example.common.BaseMeasureView;
import com.example.common.R;

/**
 * Created by LiuJin on 2018-03-14:7:16
 * 显示一个数字,可以上下滚动 增加/减少 数字
 *
 * @author wuxio
 */
public class ZanCountView extends BaseMeasureView {

    private static final String TAG = "TestCanvasIsNew";
    protected Paint         mPaint;
    protected ValueAnimator mAnimator;

    /**
     * 当前显示的数字
     */
    protected int mCurrentInt;

    /**
     * 下一个显示的数字
     */
    protected int mNextInt;

    /**
     * true : 宽度为 wrap_content 模式时,修改了宽度模式,需要根据此标记还原
     */
    protected boolean mResized;

    /**
     * 动画时常
     */
    protected int mDuration = 500;

    public ZanCountView(Context context) {
        this(context, null, 0);
    }

    public ZanCountView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZanCountView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.ZanCountViewV2);
        mDuration = typedArray.getInt(R.styleable.ZanCountViewV2_scroll_duration, 500);
        mNextInt = mCurrentInt = typedArray.getInt(R.styleable.ZanCountViewV2_number, 0);
        float textSize = typedArray.getDimension(R.styleable.ZanCountViewV2_text_size, 24);
        int color = typedArray.getColor(R.styleable.ZanCountViewV2_text_color, Color.BLACK);
        typedArray.recycle();
        mPaint.setTextSize(textSize);
        mPaint.setColor(color);
    }

    /**
     * 预留出一部分宽度,例如:当数字是 99 时,加 1 后变成100,宽度会不够,所以预留出一个数字宽度,
     * 也可以在变更数字时 {@link #requestLayout()} 重新计算宽度,但是影响性能,预留出更好点
     */
    @Override
    protected int getAtMostWidth() {
        return (int) (mPaint.measureText(String.valueOf(mCurrentInt * 10 + 9)) + 1f);
    }

    @Override
    protected int getAtMostHeight() {
        return (int) (mPaint.getFontSpacing() + 1f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mResized) {
            getLayoutParams().width = -2;
            mResized = false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        final float fontSpacing = mPaint.getFontSpacing();
        //显示文本的y坐标
        float lineHeight = fontSpacing + getPaddingTop();
        final int paddingLeft = getPaddingLeft();

        if (isRunning()) {
            float fraction = mAnimator.getAnimatedFraction();
            float dy = fraction * fontSpacing;
            canvas.clipRect(getWidth() / 2, getPaddingTop(), getRight(), lineHeight);
            if (mCurrentInt > mNextInt) {
                canvas.translate(0, dy);
            } else {
                canvas.translate(0, -dy);
            }
            canvas.drawText(
                    String.valueOf(mCurrentInt),
                    paddingLeft,
                    lineHeight,
                    mPaint
            );

            canvas.drawText(
                    String.valueOf(mCurrentInt - 1),
                    paddingLeft,
                    lineHeight - fontSpacing,
                    mPaint
            );

            canvas.drawText(
                    String.valueOf(mCurrentInt + 1),
                    paddingLeft,
                    lineHeight + fontSpacing,
                    mPaint
            );
            //Log.i(TAG, "onDraw:" + "有动画");
            invalidate();
            return;
        }

        //动画完成后会回调一次onDraw,修正当前值为 mNextInt
        mCurrentInt = mNextInt;
        canvas.drawText(
                String.valueOf(mCurrentInt),
                paddingLeft,
                lineHeight,
                mPaint
        );
        //Log.i(TAG, "onDraw:" + "没有动画");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isRunning()) {
            mAnimator.cancel();
        }
    }

    //============================动画相关API============================

    protected void start() {
        if (mAnimator == null) {
            initAnimator();
        }
        if (!mAnimator.isRunning()) {
            mAnimator.setDuration(mDuration).start();
            invalidate();
            //Log.i(TAG, "start:" + "invalidate ");
        }
    }

    protected void initAnimator() {
        if (mAnimator == null) {
            // TODO: 2018-03-14 是否可以将其提取成 static,反正只是使用一个进度值而已
            mAnimator = ValueAnimator.ofInt(0, 12);
            mAnimator.setInterpolator(new AccelerateInterpolator());
        }
    }

    protected boolean isRunning() {
        return mAnimator != null && mAnimator.isRunning();
    }

    //============================对外暴露方法============================

    /**
     * 减少数字
     */
    public void sub() {
        start();
        mNextInt = mCurrentInt - 1;
    }

    /**
     * 增加数字
     */
    public void add() {
        start();
        mNextInt = mCurrentInt + 1;
    }

    /**
     * 当装不下数字时,可以调用该方法,增加一个数字的宽度
     */
    public void resize() {
        ViewGroup.LayoutParams params = getLayoutParams();
        int width = params.width;
        if (width == -2) {
            int add = (int) (mPaint.measureText(String.valueOf(9)) + 1);
            params.width = getWidth() + add;
            mResized = true;
            requestLayout();
        }
    }
}
