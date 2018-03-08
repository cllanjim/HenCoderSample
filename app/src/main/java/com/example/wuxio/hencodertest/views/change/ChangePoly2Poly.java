package com.example.wuxio.hencodertest.views.change;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.wuxio.hencodertest.R;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class ChangePoly2Poly extends View {

    private Paint  mPaint;
    private Bitmap mHuluwa;
    private Matrix mMatrix;

    public ChangePoly2Poly(Context context) {
        this(context, null, 0);
    }

    public ChangePoly2Poly(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChangePoly2Poly(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mHuluwa = BitmapFactory.decodeResource(getResources(), R.drawable.huluwa);
        mMatrix = new Matrix();

        int left = 50;
        int top = 50;
        int right = mHuluwa.getWidth() + 50;
        int bottom = mHuluwa.getHeight() + 50;
        float[] pointsSrc = {left, top, right, top, left, bottom, right, bottom};
        float[] pointsDst = {left - 10, top + 50, right + 120, top - 50, left + 20, bottom + 30, right + 20, bottom + 60};
        mMatrix.reset();
        mMatrix.setPolyToPoly(pointsSrc, 0, pointsDst, 0, 4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.concat(mMatrix);
        canvas.drawBitmap(mHuluwa, 50, 50, mPaint);
        canvas.restore();
    }
}
