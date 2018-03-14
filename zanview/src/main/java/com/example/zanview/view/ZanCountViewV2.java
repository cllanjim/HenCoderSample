package com.example.zanview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by LiuJin on 2018-03-14:7:16
 *
 * @author wuxio
 */

public class ZanCountViewV2 extends ZanCountView {

    private static final String TAG = "ZanCountViewV2";
    private int   mCutPosition;
    private float mBaseOffset;
    private int   mLeftInt;

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
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        mBaseOffset = mPaint.measureText(String.valueOf(9));
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        final float fontSpacing = mPaint.getFontSpacing();
        //显示文本的y坐标
        final int paddingTop = getPaddingTop();
        final int paddingLeft = getPaddingLeft();
        final float textHeight = fontSpacing + paddingTop;

        final int current = mCurrentInt;
        final int next = mNextInt;
        final int position = mCutPosition;
        final float offset = mBaseOffset;

        if (isRunning()) {
            float fraction = mAnimator.getAnimatedFraction();
            final float dy = fraction * fontSpacing;
            final float right = paddingLeft + position * offset;

            if (mCurrentInt > mNextInt) {
                //此处是减小时调用,向下滚动
                canvas.translate(0, dy);
                if (position > 0) {
                    //先绘制左边不跳动的文本
                    canvas.drawText(String.valueOf(mLeftInt), 0, textHeight - dy, mPaint);
                    //裁剪一下,将左边已经绘制的部分排除出去
                    canvas.clipRect(right, paddingTop - dy, getRight(), textHeight - dy);
                    //绘制文本,因为左边已经裁减,只会绘制文本的右边部分
                    //先绘制当前的右边部分
                    canvas.drawText(String.valueOf(mCurrentInt), 0, textHeight, mPaint);
                    //后绘制下一个显示数字的右边部分,高度相差一个文本行高
                    canvas.drawText(String.valueOf(mNextInt), 0, textHeight - fontSpacing, mPaint);
                } else {
                    //先裁切一下,否则如果 宽度/高度 大于 内容高度/宽度,绘制的两行文本都会显示,裁剪之后,只会在裁减区绘制
                    canvas.clipRect(right, paddingTop - dy, getRight(), textHeight - dy);
                    canvas.drawText(String.valueOf(current), 0, textHeight, mPaint);
                    canvas.drawText(String.valueOf(next), 0, textHeight - fontSpacing, mPaint);
                }

            } else {
                //此处是增大时调用,向上滚动
                canvas.translate(0, -dy);
                if (position > 0) {
                    canvas.drawText(String.valueOf(mLeftInt), 0, textHeight + dy, mPaint);
                    canvas.clipRect(right, paddingTop + dy, getRight(), textHeight + dy);
                    canvas.drawText(String.valueOf(mCurrentInt), 0, textHeight, mPaint);
                    canvas.drawText(String.valueOf(mNextInt), 0, textHeight + fontSpacing, mPaint);
                } else {
                    canvas.clipRect(0, paddingTop + dy, getRight(), textHeight + dy);
                    canvas.drawText(String.valueOf(current), 0, textHeight, mPaint);
                    canvas.drawText(String.valueOf(next), 0, textHeight + fontSpacing, mPaint);
                }
            }

            invalidate();
            return;
        }

        //动画完成后会回调一次onDraw,修正当前值为 mNextInt
        mCurrentInt = mNextInt;
        canvas.drawText(
                String.valueOf(mCurrentInt),
                paddingLeft,
                textHeight,
                mPaint
        );
    }

    //============================计算断点============================

    /**
     * 此方法由类自己调用,辅助计算文字断点位置
     *
     * @return 断点位置
     */
    protected void findOutCutPosition() {
        String s = String.valueOf(mCurrentInt);
        String s1 = String.valueOf(mNextInt);

        mCutPosition = 0;
        int j = 0;
        int length = s.length();
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            char c1 = s1.charAt(i);
            if (c - c1 != 0) {
                mCutPosition = i;
                j = i;
                break;
            }
        }

        if (j > 0) {
            mLeftInt = mCurrentInt / (int) (Math.pow(10, (length - j)));
        }
    }

    //============================对外暴露方法============================

    /**
     * 减少数字
     */
    @Override
    public void sub() {
        start();
        mNextInt = mCurrentInt - 1;
        findOutCutPosition();

    }

    /**
     * 增加数字
     */
    @Override
    public void add() {
        start();
        mNextInt = mCurrentInt + 1;
        findOutCutPosition();
    }
}
