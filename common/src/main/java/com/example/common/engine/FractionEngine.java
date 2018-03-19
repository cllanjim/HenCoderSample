package com.example.common.engine;

import android.animation.TimeInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by LiuJin on 2018-03-19:9:33
 *
 * @author wuxio
 */

public class FractionEngine implements Fraction {

    private TimeInterpolator mInterpolator;

    private int  mDuration;
    private long mStartTime;

    public FractionEngine() {
        this(new LinearInterpolator());
    }

    public FractionEngine(TimeInterpolator interpolator) {
        mInterpolator = interpolator;
    }

    /**
     * @param duration 设置总时长
     */
    public void setDuration(int duration) {
        mDuration = duration;
    }

    /**
     * 开始工作
     */
    public void start() {
        mStartTime = System.currentTimeMillis();
    }

    /**
     * @return true : 正在运行
     */
    public boolean isRunning() {
        return System.currentTimeMillis() - mStartTime < mDuration;
    }

    /**
     * @return 当前进度值
     */
    @Override
    public float getFraction() {
        long currentTime = System.currentTimeMillis() - mStartTime;
        float interpolation = mInterpolator.getInterpolation(currentTime);
        return interpolation / mDuration;
    }
}
