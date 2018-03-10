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
        boolean isUseSelfColor = typedArray.getBoolean(R.styleable.ZanImageView_self_color, false);

        //从theme读取
        if (!isUseSelfColor && typedThemeArray.hasValue(R.styleable.ZanColors_noZanColor)) {
            mNoZanColor = typedThemeArray.getColor(R.styleable.ZanColors_noZanColor, Color.TRANSPARENT);
        } else {
            //从自定义属性读取
            mNoZanColor = typedArray.getColor(R.styleable.ZanImageView_no_zan_color, Color.GRAY);
        }
        //从theme读取
        if (!isUseSelfColor && typedThemeArray.hasValue(R.styleable.ZanColors_zanColor)) {
            mZanColor = typedThemeArray.getColor(R.styleable.ZanColors_zanColor, Color.TRANSPARENT);
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
            // TODO: 2018-03-10 此处可以优化,因为全局使用同一个配置可以抽取出去,减少类的创建
            PropertyValuesHolder holderColor = PropertyValuesHolder.ofInt("color", mNoZanColor, mZanColor);
            holderColor.setEvaluator(getEvaluator());
            PropertyValuesHolder holderScaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.4f, 1f);
            PropertyValuesHolder holderScaleY = PropertyValuesHolder.ofFloat("ScaleY", 1f, 1.4f, 1f);
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
            // TODO: 2018-03-10 此处可以优化,因为全局使用同一个配置可以抽取出去,减少类的创建
            PropertyValuesHolder holderColor = PropertyValuesHolder.ofInt("color", mZanColor, mNoZanColor);
            holderColor.setEvaluator(getEvaluator());
            PropertyValuesHolder holderScaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.4f, 1f);
            PropertyValuesHolder holderScaleY = PropertyValuesHolder.ofFloat("ScaleY", 1f, 1.4f, 1f);

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
}
