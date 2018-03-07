package com.example.wuxio.hencodertest.views.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class DrawHistogram extends View {

    private Paint mPaint;
    private RectF mRectF;
    private int[] data = {800, 200, 300, 400, 600, 900, 700, 500, 700, 500};

    public DrawHistogram(Context context) {
        this(context, null, 0);
    }

    public DrawHistogram(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawHistogram(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);

        mRectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int bottom = getHeight() / 2;
        int cellWidth = width / 10;
        int space = cellWidth / 5;
        int hisWidth = space * 4;

        for (int i = 0; i < 9; i++) {
            int left = space + cellWidth * i;
            int top = data[i];
            int right = left + hisWidth;
            mRectF.set(left, top, right, bottom);
            canvas.drawRect(mRectF, mPaint);
        }
    }
}

