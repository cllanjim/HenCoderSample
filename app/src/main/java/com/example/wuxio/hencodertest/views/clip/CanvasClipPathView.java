package com.example.wuxio.hencodertest.views.clip;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by LiuJin on 2018-03-06:21:59
 */

public class CanvasClipPathView extends View {

    private static final String TAG = CanvasClipPathView.class.getSimpleName();

    private Paint mPaint = new Paint();
    private Path  mPath  = new Path();
    private float m1dp;

    private boolean isClip;

    private int left;
    private int top;
    private int right;
    private int bottom;

    public CanvasClipPathView(Context context) {
        super(context);
        init();
    }

    public CanvasClipPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CanvasClipPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        float radius = 100 * m1dp;
        mPath.addCircle(radius / 2, radius / 2, radius / 2, Path.Direction.CCW);
        canvas.clipPath(mPath);
        canvas.drawColor(Color.RED);
    }
}
