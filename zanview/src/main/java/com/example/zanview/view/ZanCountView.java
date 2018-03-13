package com.example.zanview.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;

import com.example.zanview.R;

import java.util.Arrays;

/**
 * Created by LiuJin on 2018-03-10:14:25
 *
 * @author wuxio
 */
public class ZanCountView extends android.support.v7.widget.AppCompatTextView implements
        ValueAnimator.AnimatorUpdateListener,
        Animator.AnimatorListener {

    private static final String TAG = ZanCountView.class.getSimpleName();

    //记录位置信息
    private Rect          mBound;
    private int[]         mPoints;
    //点赞数量
    private int           mCount;
    private int           mNextCount;
    //从该位置截断
    private int           mStartIndex;
    // 动画引擎
    private ValueAnimator mAnimator;
    private int mDuration = 500;

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

    private void init(Context context, AttributeSet attrs) {

        //设置默认值
        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.ZanCountView);
        mNextCount = mCount = typedArray.getInt(R.styleable.ZanCountView_zan_count, 0);
        typedArray.recycle();
        setText(String.valueOf(mCount));
    }

    @Override
    protected void onDraw(Canvas canvas) {


        if (isNeedAnimate()) {
            float fraction = mAnimator.getAnimatedFraction();
            final int maxDy = mPoints[3] - mPoints[1];
            final float dy = fraction * maxDy;

            CharSequence text = getText();
            //绘制没有变化的文本
            CharSequence subSequence = text.subSequence(0, mStartIndex);
            setText(subSequence);
            super.onDraw(canvas);

            canvas.clipRect(mPoints[0], mPoints[1] - maxDy, getRight(), mPoints[3] + maxDy);
            //绘制下一个变化的文本
            if (mNextCount - mCount > 0) {
                //如果是增加向上滚动,绘制到下边
                canvas.translate(0, -dy);
                setText(String.valueOf(mCount));
                super.onDraw(canvas);
                canvas.translate(0, maxDy);
                setText(String.valueOf(mNextCount));
                super.onDraw(canvas);

            } else {
                //如果是减少向下滚动,绘制到上边
                canvas.translate(0, dy);
                setText(String.valueOf(mCount));
                super.onDraw(canvas);
                canvas.translate(0, -maxDy);
                setText(String.valueOf(mNextCount));
                super.onDraw(canvas);
            }

        } else {
            super.onDraw(canvas);
        }
    }

    public ValueAnimator getAnimator() {
        if (mAnimator == null) {
            //获取一个动画引擎,数字不重要只是要进度值
            mAnimator = ValueAnimator.ofInt(0, 12);
            mAnimator.addUpdateListener(this);
            mAnimator.addListener(this);
        }
        return mAnimator;
    }

    private void start() {
        if (!getAnimator().isRunning()) {
            getAnimator().setDuration(mDuration).start();
        }
    }

    /**
     * @return true: 需要更新界面
     */
    private boolean isNeedAnimate() {
        return mAnimator != null && mAnimator.isRunning();
    }

    /**
     * 赞加1
     */
    public void addZanCount() {
        String s1 = String.valueOf(mCount);
        mNextCount = mCount + 1;
        String s2 = String.valueOf(mNextCount);

        //计算需要切断的位置
        final int length = s2.length();

        for (int i = 0; i < length; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                mStartIndex = i;
            }
        }

        getTextCoordinate(mStartIndex);
        start();
    }

    /**
     * 赞减1
     */
    public void subZanCount() {
        String s1 = String.valueOf(mCount);
        mNextCount = mCount - 1;
        String s2 = String.valueOf(mNextCount);

        //计算需要切断的位置
        final int length = s2.length();
        for (int i = 0; i < length; i++) {
            int i1 = s1.charAt(i) - s2.charAt(i);
            if (i1 != 0) {
                mStartIndex = i;
            }
        }
        Log.i(TAG, "subZanCount:当前: " + mCount + " 下一个:" + mNextCount + " index: " + mStartIndex);
        getTextCoordinate(mStartIndex);
        start();
    }

    /**
     * @param position 返回指定位置字符的坐标
     */
    private void getTextCoordinate(int position) {

        Layout layout = getLayout();

        if (mBound == null) {
            mBound = new Rect();
        }
        if (mPoints == null) {
            mPoints = new int[4];
        }

        Arrays.fill(mPoints, 0);
        mBound.setEmpty();

        int line = layout.getLineForOffset(position);
        layout.getLineBounds(line, mBound);
        //字符顶部y坐标
        mPoints[1] = mBound.top;
        //字符底部y坐标
        mPoints[3] = mBound.bottom;
        //字符左边x坐标
        mPoints[0] = (int) layout.getPrimaryHorizontal(position);
        //字符右边x坐标
        if (position == getText().length() - 1) {
            mPoints[2] = getRight();
        } else {
            mPoints[2] = (int) layout.getPrimaryHorizontal(position + 1);
        }
        Log.i(TAG, "getTextCoordinate:" + "坐标:" + Arrays.toString(mPoints));
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        invalidate();
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        mCount = mNextCount;
        setText(String.valueOf(mNextCount));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.removeUpdateListener(this);
            mAnimator.removeListener(this);
            mAnimator.cancel();
        }
    }

    //============================接口空实现============================
    @Override
    public void onAnimationStart(Animator animation) {
    }

    @Override
    public void onAnimationCancel(Animator animation) {
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
    }

    //============================内部类============================


}
