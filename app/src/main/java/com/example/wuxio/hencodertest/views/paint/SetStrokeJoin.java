package com.example.wuxio.hencodertest.views.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class SetStrokeJoin extends View {

    private Paint mPaint;
    private Path  mPath;

    public SetStrokeJoin(Context context) {
        this(context, null, 0);
    }

    public SetStrokeJoin(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SetStrokeJoin(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.moveTo(80, 80);
        mPath.rLineTo(300, 0);
        mPath.rLineTo(-300, 160);
        mPaint.setStrokeJoin(Paint.Join.BEVEL);
        canvas.drawPath(mPath, mPaint);

        mPath.moveTo(80, 320);
        mPath.rLineTo(300, 0);
        mPath.rLineTo(-300, 160);
        mPaint.setStrokeJoin(Paint.Join.MITER);
        canvas.drawPath(mPath, mPaint);

        mPath.moveTo(80, 560);
        mPath.rLineTo(300, 0);
        mPath.rLineTo(-300, 160);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawPath(mPath, mPaint);
    }
}
