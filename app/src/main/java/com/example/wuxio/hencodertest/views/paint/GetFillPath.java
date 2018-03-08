package com.example.wuxio.hencodertest.views.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class GetFillPath extends View {

    private static final String TAG = "GetFillPath";

    private Paint mPaint;
    private Path  mPath;
    private Path  mDstPath;

    public GetFillPath(Context context) {
        this(context, null, 0);
    }

    public GetFillPath(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GetFillPath(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(80);
        mPaint.setStrokeWidth(0);
        mPath = new Path();

        mDstPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        mPath.moveTo(80, 80);
        mPath.rLineTo(400, 0);
        mPath.rLineTo(560, 240);
        canvas.drawPath(mPath, mPaint);

        mPaint.getFillPath(mPath, mDstPath);

        boolean empty = mDstPath.isEmpty();
        Log.i(TAG, "onDraw:dst is empty:" + empty);

    }
}
