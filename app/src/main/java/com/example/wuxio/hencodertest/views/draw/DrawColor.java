package com.example.wuxio.hencodertest.views.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.wuxio.hencodertest.R;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class DrawColor extends View {

    private Paint mPaint;

    public DrawColor(Context context) {
        this(context, null, 0);
    }

    public DrawColor(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawColor(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setTextSize(80);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.clipRect(50, 50, 650, 650);
        canvas.drawColor(getResources().getColor(R.color.gold));

        canvas.drawText("drawColor", 50, 630, mPaint);
    }
}
