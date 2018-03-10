package com.example.zanview.view;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.zanview.R;

/**
 * Created by LiuJin on 2018-03-09:20:29
 */

public class ZanImageView extends TintImageView {

    private static final String TAG = "ZanImageView";

    private int            mNoZanColor;
    private int            mZanColor;
    private int            mDuration;
    private boolean        isZan;
    private ObjectAnimator mZanAnimator;
    private ObjectAnimator mNoZanAnimator;
    private ArgbEvaluator  mEvaluator;
    private ClickEvent     mClickEvent;
    private boolean        mIsUseSelfColor;

    public ZanImageView(Context context) {
        this(context, null, 0);
    }

    public ZanImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZanImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initFields();
    }

    /**
     * @param attrs 读取需要的属性
     */
    private void initAttrs(@Nullable AttributeSet attrs) {

        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.ZanImageView);
        TypedArray typedThemeArray = getContext().getTheme().obtainStyledAttributes(R.style.AppTheme, R.styleable.ZanColors);

        //是否使用自己定义的颜色,以为theme中可以指定全局颜色,默认使用全局颜色
        mIsUseSelfColor = typedArray.getBoolean(R.styleable.ZanImageView_self_color, false);

        //从theme读取
        if (!mIsUseSelfColor && typedThemeArray.hasValue(R.styleable.ZanColors_noZanColor)) {
            mNoZanColor = typedThemeArray.getColor(R.styleable.ZanColors_noZanColor, Color.TRANSPARENT);
            PropertyValuesHolderFactory.noZanColor = mNoZanColor;
        } else {
            //从自定义属性读取
            mNoZanColor = typedArray.getColor(R.styleable.ZanImageView_no_zan_color, Color.GRAY);
        }
        //从theme读取
        if (!mIsUseSelfColor && typedThemeArray.hasValue(R.styleable.ZanColors_zanColor)) {
            mZanColor = typedThemeArray.getColor(R.styleable.ZanColors_zanColor, Color.TRANSPARENT);
            PropertyValuesHolderFactory.zanColor = mZanColor;
        } else {
            //从自定义属性读取
            mZanColor = typedArray.getColor(R.styleable.ZanImageView_zan_color, Color.RED);
        }

        mDuration = typedArray.getInteger(R.styleable.ZanImageView_duration, 400);

        //释放资源
        typedArray.recycle();
        typedThemeArray.recycle();
    }

    /**
     * 初始化成员变量
     */
    private void initFields() {
        setColor(mNoZanColor);
        mClickEvent = new ClickEvent();
        super.setOnClickListener(mClickEvent);
    }

    /**
     * @return true:点赞状态,false:没点赞状态
     */
    public boolean isZan() {
        return isZan;
    }

    /**
     * 点赞动画
     */
    public void animateZan() {

        if (mZanAnimator == null) {
            PropertyValuesHolder holderColor;
            if (!mIsUseSelfColor) {
                holderColor = PropertyValuesHolderFactory.getZanHolderColor();
            } else {
                holderColor = PropertyValuesHolder.ofInt("color", mNoZanColor, mZanColor);
            }
            holderColor.setEvaluator(getEvaluator());
            PropertyValuesHolder holderScaleX = PropertyValuesHolderFactory.holderScaleX;
            PropertyValuesHolder holderScaleY = PropertyValuesHolderFactory.holderScaleY;
            mZanAnimator = ObjectAnimator.ofPropertyValuesHolder(this, holderColor, holderScaleX, holderScaleY);
            mZanAnimator.setDuration(mDuration);
        }

        if (!mZanAnimator.isRunning()) {
            mZanAnimator.start();
        }
        isZan = true;
    }

    /**
     * 取消点赞动画
     */
    public void reverseAnimateZan() {
        if (mNoZanAnimator == null) {
            PropertyValuesHolder holderColor;
            if (!mIsUseSelfColor) {
                holderColor = PropertyValuesHolderFactory.getNoZanHolderColor();
            } else {
                holderColor = PropertyValuesHolder.ofInt("color", mZanColor, mNoZanColor);
            }
            holderColor.setEvaluator(getEvaluator());
            PropertyValuesHolder holderScaleX = PropertyValuesHolderFactory.holderScaleX;
            PropertyValuesHolder holderScaleY = PropertyValuesHolderFactory.holderScaleY;

            mNoZanAnimator = ObjectAnimator.ofPropertyValuesHolder(this, holderColor, holderScaleX, holderScaleY);
            mNoZanAnimator.setDuration(mDuration);
        }

        if (!mNoZanAnimator.isRunning()) {
            mNoZanAnimator.start();
        }
        isZan = false;
    }

    /**
     * @return {@link #mEvaluator}
     */
    public ArgbEvaluator getEvaluator() {
        if (mEvaluator == null) {
            mEvaluator = new ArgbEvaluator();
        }
        return mEvaluator;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mZanAnimator != null && mZanAnimator.isRunning()) {
            mZanAnimator.cancel();
        }
        if (mNoZanAnimator != null && mNoZanAnimator.isRunning()) {
            mNoZanAnimator.cancel();
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mClickEvent.mClickListener = l;
    }

    //============================内部类============================

    /**
     * 点击事件
     */
    private class ClickEvent implements View.OnClickListener {

        //代理用户点击事件
        OnClickListener mClickListener;

        ClickEvent() {
        }

        @Override
        public void onClick(View v) {
            if (isZan) {
                reverseAnimateZan();
            } else {
                animateZan();
            }

            if (mClickListener != null) {
                mClickListener.onClick(v);
            }
        }
    }

    /**
     * 用于提供全局PropertyValuesHolder
     */
    static class PropertyValuesHolderFactory {

        static int zanColor;
        static int noZanColor;

        private static PropertyValuesHolder zanHolderColor;
        private static PropertyValuesHolder noZanHolderColor;

        static PropertyValuesHolder holderScaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.4f, 1f);
        static PropertyValuesHolder holderScaleY = PropertyValuesHolder.ofFloat("ScaleY", 1f, 1.4f, 1f);

        public static PropertyValuesHolder getZanHolderColor() {
            if (zanHolderColor == null) {
                zanHolderColor = PropertyValuesHolder.ofInt("color", noZanColor, zanColor);
            }
            return zanHolderColor;
        }

        public static PropertyValuesHolder getNoZanHolderColor() {
            if (noZanHolderColor == null) {
                noZanHolderColor = PropertyValuesHolder.ofInt("color", zanColor, noZanColor);
            }
            return noZanHolderColor;
        }

        public static int getZanColor() {
            return zanColor;
        }

        public static void setZanColor(int zanColor) {
            if (PropertyValuesHolderFactory.zanColor != zanColor) {
                PropertyValuesHolderFactory.zanColor = zanColor;
                PropertyValuesHolderFactory.zanHolderColor = PropertyValuesHolder.ofInt("color", noZanColor, zanColor);
                PropertyValuesHolderFactory.noZanHolderColor = PropertyValuesHolder.ofInt("color", zanColor, noZanColor);
            }
        }

        public static int getNoZanColor() {
            return noZanColor;
        }

        public static void setNoZanColor(int noZanColor) {
            if (PropertyValuesHolderFactory.noZanColor != noZanColor) {
                PropertyValuesHolderFactory.noZanColor = noZanColor;
                PropertyValuesHolderFactory.zanHolderColor = PropertyValuesHolder.ofInt("color", noZanColor, zanColor);
                PropertyValuesHolderFactory.noZanHolderColor = PropertyValuesHolder.ofInt("color", zanColor, noZanColor);
            }
        }
    }
}
