package com.example.wuxio.hencodertest.views.clip;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.wuxio.hencodertest.R;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class ClipPathTest extends View {

    private static final String TAG = "FontMetircsTest";

    private Paint  mPaint;
    private Bitmap mHuluwaBitmap;
    private Bitmap mBatmanBitmap;
    private Path   mPath;

    public ClipPathTest(Context context) {
        this(context, null, 0);
    }

    public ClipPathTest(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipPathTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mHuluwaBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.huluwa);
        mBatmanBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.batman);

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        mPath.addCircle(400, 200, 150, Path.Direction.CW);
        canvas.clipPath(mPath);
        canvas.drawBitmap(mHuluwaBitmap, 50, 50, mPaint);
        canvas.restore();

        canvas.save();
        mPath.reset();
        mPath.moveTo(50, 50 + mHuluwaBitmap.getHeight() + 50);
        mPath.rLineTo(mBatmanBitmap.getWidth(), 0);
        mPath.rLineTo(-mBatmanBitmap.getWidth() / 2, 300);
        canvas.clipPath(mPath);
        canvas.drawBitmap(mBatmanBitmap, 50, 50 + mHuluwaBitmap.getHeight() + 50, mPaint);
        canvas.restore();
    }
}
