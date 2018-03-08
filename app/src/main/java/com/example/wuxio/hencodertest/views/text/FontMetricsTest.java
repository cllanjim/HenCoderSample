package com.example.wuxio.hencodertest.views.text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class FontMetricsTest extends View {

    private static final String TAG = "FontMetircsTest";

    private TextPaint    mPaint;
    private StaticLayout mStaticLayout1;
    private StaticLayout mStaticLayout2;

    private String text00 = "第一行文本";
    private String text01 = "Test Text";
    private String text02 = "测试文本 Test google 159";
    private Rect mRect;

    public FontMetricsTest(Context context) {
        this(context, null, 0);
    }

    public FontMetricsTest(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontMetricsTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new TextPaint();
        mPaint.setTextSize(80);

        mRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //FontSpacing 推荐的两行文字的 baseline 的距离
        canvas.drawText(text00, 50, 150, mPaint);
        canvas.drawText(text01, 50, 150 + mPaint.getFontSpacing(), mPaint);
        canvas.drawText(text02, 50, 150 + mPaint.getFontSpacing() * 2, mPaint);

        Paint.FontMetrics metrics = mPaint.getFontMetrics();
        Log.i(TAG, "onDraw top:" + metrics.top);
        Log.i(TAG, "onDraw ascent:" + metrics.ascent);
        Log.i(TAG, "onDraw descent:" + metrics.descent);
        Log.i(TAG, "onDraw bottom:" + metrics.bottom);

        mPaint.getTextBounds(text01, 0, text01.length(), mRect);
        Log.i(TAG, "onDraw:getTextBounds: " + mRect.left + " " + mRect.top + " " + mRect.right + " " + mRect.bottom);

        mRect.set(
                50 + mRect.left,
                mRect.top + 150 + (int) mPaint.getFontSpacing(),
                mRect.right + 50,
                mRect.bottom + 150 + (int) mPaint.getFontSpacing()
        );
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        canvas.drawRect(
                mRect,
                mPaint
        );

        float width = mPaint.measureText(text02);
        float text02Bottom = 150 + mPaint.getFontSpacing() * 2 + metrics.descent;
        canvas.drawLine(50, text02Bottom, 50 + width, text02Bottom, mPaint);
    }
}
