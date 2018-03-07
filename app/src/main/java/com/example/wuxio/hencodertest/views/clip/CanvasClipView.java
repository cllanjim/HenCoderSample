package com.example.wuxio.hencodertest.views.clip;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by LiuJin on 2018-03-06:21:59
 */

public class CanvasClipView extends View {

    private static final String TAG = CanvasClipView.class.getSimpleName();

    private Paint mPaint = new Paint();
    private float m1dp;

    private boolean isClip;

    private int left;
    private int top;
    private int right;
    private int bottom;

    public CanvasClipView(Context context) {
        super(context);
        init();
    }

    public CanvasClipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CanvasClipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //1dp
        m1dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        mPaint.setTextSize(12 * m1dp);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawScene(canvas);
    }

    private void drawScene(Canvas canvas) {
        canvas.clipRect(0, 0, 100 * m1dp, 100 * m1dp);

        canvas.drawColor(Color.WHITE);

        mPaint.setColor(Color.RED);
        canvas.drawLine(0, 0, 100 * m1dp, 100 * m1dp, mPaint);

        mPaint.setColor(Color.GREEN);
        canvas.drawCircle(30 * m1dp, 70 * m1dp, 30 * m1dp, mPaint);

        mPaint.setColor(Color.BLUE);
        canvas.drawText("Clipping", 50 * m1dp, 30 * m1dp, mPaint);
    }
}
