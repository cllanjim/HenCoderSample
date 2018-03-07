package com.example.wuxio.hencodertest.views.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.wuxio.hencodertest.R;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class DrawCircle extends View {

    private Paint mPaint;

    public DrawCircle(Context context) {
        this(context, null, 0);
    }

    public DrawCircle(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.FILL);
        final int base = getWidth() / 4;
        canvas.drawCircle(base, base, base, mPaint);

        mPaint.setColor(getColor(R.color.gold));
        canvas.drawCircle(base * 3, base, base, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(base, base * 3, base, mPaint);
    }

    @ColorInt
    private int getColor(@ColorRes int colorRes) {
        return getResources().getColor(colorRes);
    }
}

