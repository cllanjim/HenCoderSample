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

public class ChangeMatrix extends View {

    private Paint  mPaint;
    private Bitmap mHuluwa;
    private Matrix mMatrix;

    public ChangeMatrix(Context context) {
        this(context, null, 0);
    }

    public ChangeMatrix(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChangeMatrix(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mHuluwa = BitmapFactory.decodeResource(getResources(), R.drawable.huluwa);
        mMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mMatrix.reset();
        mMatrix.postTranslate(200,200);
        mMatrix.postRotate(12);

        canvas.save();
        canvas.concat(mMatrix);
        canvas.drawBitmap(mHuluwa, 0, 0, mPaint);
        canvas.restore();
    }
}
