package com.example.wuxio.hencodertest.views.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class DrawText extends View {

    private Paint mPaint;

    public DrawText(Context context) {
        this(context, null, 0);
    }

    public DrawText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final String text = "Hen coder";

        mPaint.setTextSize(18);
        canvas.drawText(text, 100, 25, mPaint);
        mPaint.setTextSize(36);
        canvas.drawText(text, 100, 70, mPaint);
        mPaint.setTextSize(60);
        canvas.drawText(text, 100, 145, mPaint);
        mPaint.setTextSize(84);
        canvas.drawText(text, 100, 240, mPaint);
    }
}
