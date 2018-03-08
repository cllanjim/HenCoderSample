package com.example.wuxio.hencodertest.views.clip;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.wuxio.hencodertest.R;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class ClipRectTest extends View {

    private static final String TAG = "FontMetircsTest";

    private Paint  mPaint;
    private Bitmap mHuluwaBitmap;
    private Bitmap mBatmanBitmap;
    private Rect   mRect;


    public ClipRectTest(Context context) {
        this(context, null, 0);
    }

    public ClipRectTest(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipRectTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mHuluwaBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.huluwa);
        mBatmanBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.batman);

        mRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        mRect.set(
                50,
                100,
                mHuluwaBitmap.getWidth() + 50,
                (mHuluwaBitmap.getHeight() + 50) * 70 / 100
        );
        canvas.clipRect(mRect);
        canvas.drawBitmap(mHuluwaBitmap, 50, 50, mPaint);
        canvas.restore();

        canvas.save();
        mRect.set(
                50,
                50 + mHuluwaBitmap.getHeight() + 50,
                mBatmanBitmap.getWidth() + 50,
                50 + mHuluwaBitmap.getHeight() + 50 + mBatmanBitmap.getHeight() * 50 / 100
        );
        canvas.clipRect(mRect);
        canvas.drawBitmap(mBatmanBitmap, 50, 50 + mHuluwaBitmap.getHeight() + 50, mPaint);
        canvas.restore();
    }
}
