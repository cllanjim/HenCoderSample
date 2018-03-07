package com.example.wuxio.hencodertest.views.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.wuxio.hencodertest.R;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class SetBitmapShader extends View {

    private Paint        mPaint;
    private BitmapShader mShaderClamp;
    private Shader       mShaderRepeat;
    private Shader       mShaderMirror;

    public SetBitmapShader(Context context) {
        this(context, null, 0);
    }

    public SetBitmapShader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SetBitmapShader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.huluwa);
        mShaderClamp = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mShaderRepeat = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        mShaderMirror = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setShader(mShaderClamp);
        canvas.drawCircle(400, 400, 360, mPaint);

        mPaint.setShader(mShaderRepeat);
        canvas.drawCircle(1000, 900, 360, mPaint);

        mPaint.setShader(mShaderMirror);
        canvas.drawCircle(400, 1400, 360, mPaint);
    }
}
