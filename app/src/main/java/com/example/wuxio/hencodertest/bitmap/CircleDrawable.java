package com.example.wuxio.hencodertest.bitmap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;

/**
 * Created by LiuJin on 2018-03-07:13:34
 */

public class CircleDrawable extends Drawable {

    private int   width;
    private int   height;
    private Paint mPaint;

    public CircleDrawable(Context context) {
        float v100dp = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                100,
                context.getResources().getDisplayMetrics()
        );

        width = (int) v100dp;
        height = (int) v100dp;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

        int radius = width * 40 / 100;

        canvas.drawCircle(
                width - radius,
                radius,
                radius,
                mPaint
        );
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return width;
    }

    @Override
    public int getIntrinsicHeight() {
        return height;
    }
}
