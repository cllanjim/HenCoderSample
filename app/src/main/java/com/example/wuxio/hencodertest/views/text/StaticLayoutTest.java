package com.example.wuxio.hencodertest.views.text;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class StaticLayoutTest extends View {

    private TextPaint    mPaint;
    private StaticLayout mStaticLayout1;
    private StaticLayout mStaticLayout2;

    public StaticLayoutTest(Context context) {
        this(context, null, 0);
    }

    public StaticLayoutTest(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StaticLayoutTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new TextPaint();
        mPaint.setTextSize(40);

        String text1 = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.";
        mStaticLayout1 = new StaticLayout(text1, mPaint, 600,
                Layout.Alignment.ALIGN_NORMAL, 1, 0, true);

        String text2 = "a\nbc\ndefghi\njklm\nnopqrst\nuvwx\nyz";
        mStaticLayout2 = new StaticLayout(text2, mPaint, 600,
                Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(50, 100);
        mStaticLayout1.draw(canvas);
        canvas.translate(0, 200);
        mStaticLayout2.draw(canvas);
        canvas.restore();
    }
}
