package com.example.wuxio.hencodertest.views.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.wuxio.hencodertest.R;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class SetColorFilter extends View {

    private Paint  mPaint;
    private Bitmap mBitmap;
    private PorterDuffColorFilter mPorterDuffColorFilter;
    private ColorFilter mLightingColorFilter;

    public SetColorFilter(Context context) {
        this(context, null, 0);
    }

    public SetColorFilter(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SetColorFilter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.huluwa);

        mLightingColorFilter = new LightingColorFilter(0x00ffff, 0x000000);


        mPorterDuffColorFilter = new PorterDuffColorFilter(
                getResources().getColor(R.color.gold),
                PorterDuff.Mode.ADD
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColorFilter(mLightingColorFilter);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);

        mPaint.setColorFilter(mPorterDuffColorFilter);
        canvas.drawBitmap(mBitmap,0,500,mPaint);
    }
}
