package com.example.wuxio.hencodertest.views.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.wuxio.hencodertest.R;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class SetShadowLayer extends View {

    private Paint mPaint;

    public SetShadowLayer(Context context) {
        this(context, null, 0);
    }

    public SetShadowLayer(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SetShadowLayer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        mPaint.setShadowLayer(10, 0, 0, getResources().getColor(R.color.gold));
        canvas.drawText("HenCoder Text", 80, 180, mPaint);

        mPaint.setShadowLayer(10, 0, 0, Color.RED);
        canvas.drawText("HenCoder Text", 80, 300, mPaint);
    }
}
