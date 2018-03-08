package com.example.wuxio.hencodertest.views.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.wuxio.hencodertest.R;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class SetXferMode extends View {

    private Paint    mPaint;
    private Bitmap   mRect;
    private Bitmap   mCircle;
    private Xfermode mXfermode;

    public SetXferMode(Context context) {
        this(context, null, 0);
    }

    public SetXferMode(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SetXferMode(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mRect = BitmapFactory.decodeResource(getResources(), R.drawable.trans_ractangle);
        mCircle = BitmapFactory.decodeResource(getResources(), R.drawable.trans_circle);

        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.XOR);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(mRect, 0, 0, mPaint); // 画方
        mPaint.setXfermode(mXfermode); // 设置 Xfermode
        canvas.drawBitmap(mCircle, 0, 0, mPaint); // 画圆
        mPaint.setXfermode(null); // 用完及时清除 Xfermode

        canvas.restoreToCount(saved);
    }
}
