package com.example.zanview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.View;

import com.example.zanview.R;

/**
 * Created by LiuJin on 2018-03-09:17:59
 *
 * @author wuxio
 */
public class ZanView extends View {

    private static final String TAG = "ZanView";

    private Paint          mPaint;
    private BitmapDrawable mEditBitmap;

    public ZanView(Context context) {
        this(context, null, 0);
    }

    public ZanView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        Drawable zan = ContextCompat.getDrawable(getContext(), R.drawable.zan);
        Drawable tintZan = DrawableCompat.wrap(zan).mutate();
        //DrawableCompat.setTint(tintZan, Color.RED);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mEditBitmap.getBitmap(), 50, 50, mPaint);
    }
}
