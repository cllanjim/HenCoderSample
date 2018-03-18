package com.example.flipview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by LiuJin on 2018-03-09:18:19
 *
 * @author wuxio
 */
public class FlipViewSample extends View {

    private static final String TAG = "TintImageView";

    protected Paint  mPaint;
    protected Paint  mPaintRect;
    protected Bitmap mBitmap;

    protected int mWidth;
    protected int mHeight;

    protected Camera mCamera;
    protected float  mDegree;

    public FlipViewSample(Context context) {
        this(context, null, 0);
    }

    public FlipViewSample(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlipViewSample(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);

        mPaintRect = new Paint();
        mPaintRect.setStyle(Paint.Style.STROKE);
        mPaintRect.setColor(Color.RED);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.huluwa);
        mCamera = new Camera();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final Bitmap bitmap = mBitmap;
        final int bitmapWidth = bitmap.getWidth();
        final int bitmapHeight = bitmap.getHeight();
        final int halfWidth = mWidth >> 1;
        final int halfHeight = mHeight >> 1;
        final Camera camera = mCamera;
        final float degree = mDegree;

        //移动坐标到中心
        canvas.translate(halfWidth, halfHeight);

        //绘制没有camera旋转部分
        canvas.save();
        //旋转坐标
        canvas.rotate(-degree);
        //截取画布左边部分
        canvas.clipRect(0, -halfHeight, -halfWidth, halfHeight);
        canvas.drawRect(0, -400, -halfWidth, 400, mPaintRect);
        //绘制左边部分
        canvas.rotate(degree);
        canvas.drawRect(-bitmapWidth >> 1, -bitmapHeight >> 1, bitmapWidth >> 1, bitmapHeight >> 1, mPaint);
        canvas.restore();

        canvas.save();
        //旋转坐标
        canvas.rotate(-degree);
        //截取画布右边部分
        canvas.clipRect(0, -halfHeight, halfWidth, halfHeight);
        canvas.drawRect(0, -400, halfWidth, 400, mPaintRect);
        //使用camera旋转
        camera.save();
        camera.rotateY(-45);
        camera.applyToCanvas(canvas);
        camera.restore();
        //画布旋转回来,绘制bitmap
        canvas.rotate(degree);
        canvas.drawRect(-bitmapWidth >> 1, -bitmapHeight >> 1, bitmapWidth >> 1, bitmapHeight >> 1, mPaint);
        canvas.restore();
    }

    public void change() {

        ValueAnimator animator = ValueAnimator.ofInt(0, 12);
        animator.setDuration(12 * 1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                mDegree = 360 * fraction;
                invalidate();
            }
        });
        animator.start();
    }
}