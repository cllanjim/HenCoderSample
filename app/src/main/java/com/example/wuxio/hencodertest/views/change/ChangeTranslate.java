package com.example.wuxio.hencodertest.views.change;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.wuxio.hencodertest.R;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class ChangeTranslate extends View {

    private Paint  mPaint;
    private Bitmap mHuluwa;

    public ChangeTranslate(Context context) {
        this(context, null, 0);
    }

    public ChangeTranslate(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChangeTranslate(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();

        mHuluwa = BitmapFactory.decodeResource(getResources(), R.drawable.huluwa);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(100, 100);
        canvas.drawBitmap(mHuluwa, 0, 0, mPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(100, 800);
        canvas.rotate(90);
        canvas.drawBitmap(mHuluwa, 0, -600, mPaint);
        canvas.restore();

    }
}
