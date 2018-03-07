package com.example.wuxio.hencodertest.views.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class SetShader extends View {

    private Paint  mPaint;
    private Shader mLinearGradient;
    private Shader mRadialGradient;
    private Shader mSweepGradient;

    public SetShader(Context context) {
        this(context, null, 0);
    }

    public SetShader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SetShader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mLinearGradient = new LinearGradient(
                100, 100,
                500, 500,
                Color.parseColor("#E91E63"), Color.parseColor("#2196F3"),
                Shader.TileMode.CLAMP
        );
        mRadialGradient = new RadialGradient(
                300, 800,
                200,
                Color.parseColor("#E91E63"), Color.parseColor("#2196F3"),
                Shader.TileMode.CLAMP
        );
        mSweepGradient = new SweepGradient(
                300, 1300,
                Color.parseColor("#E91E63"), Color.parseColor("#2196F3")
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //注意和 shader 坐标一致
        mPaint.setShader(mLinearGradient);
        canvas.drawCircle(300, 300, 200, mPaint);

        mPaint.setShader(mRadialGradient);
        canvas.drawCircle(300, 800, 200, mPaint);

        mPaint.setShader(mSweepGradient);
        canvas.drawCircle(300, 1300, 200, mPaint);
    }
}
