package com.example.wuxio.hencodertest.views.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class ShaderTileMode extends View {

    private Paint  mPaint;
    private Shader mShaderREPEAT;
    private Shader mShaderCLAMP;
    private Shader mShaderMIRROR;

    public ShaderTileMode(Context context) {
        this(context, null, 0);
    }

    public ShaderTileMode(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShaderTileMode(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(50);

        mShaderCLAMP = new LinearGradient(
                100, 100,
                200, 100,
                Color.parseColor("#E91E63"), Color.parseColor("#2196F3"),
                Shader.TileMode.CLAMP
        );

        mShaderREPEAT = new LinearGradient(
                100, 600,
                200, 600,
                Color.parseColor("#E91E63"), Color.parseColor("#2196F3"),
                Shader.TileMode.REPEAT
        );

        mShaderMIRROR = new LinearGradient(
                100, 1100,
                200, 1100,
                Color.parseColor("#E91E63"), Color.parseColor("#2196F3"),
                Shader.TileMode.MIRROR
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText("shader 宽度 100", 100, 80, mPaint);

        mPaint.setShader(mShaderCLAMP);
        canvas.drawRect(100, 100, getWidth() - 100, 500, mPaint);
        canvas.drawText("CLAMP ↑", 100, 580, mPaint);

        mPaint.setShader(mShaderREPEAT);
        canvas.drawRect(100, 600, getWidth() - 100, 1000, mPaint);
        canvas.drawText("REPEAT ↑", 100, 1080, mPaint);

        mPaint.setShader(mShaderMIRROR);
        canvas.drawRect(100, 1100, getWidth() - 100, 1500, mPaint);
        canvas.drawText("MIRROR ↑", 100, 1580, mPaint);
    }
}
