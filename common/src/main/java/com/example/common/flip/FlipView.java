package com.example.common.flip;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.common.R;

/**
 * Created by LiuJin on 2018-03-09:18:19
 *
 * @author wuxio
 */
public class FlipView extends View {

    private static final String TAG = "TintImageView";

    protected Paint  mPaint;
    protected Bitmap mBitmap;

    protected int mWidth;
    protected int mHeight;

    protected Camera mCamera;
    protected float  mDegree;
    protected float  mCameraDegree;

    public FlipView(Context context) {
        this(context, null, 0);
    }

    public FlipView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        mPaint = new Paint();
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
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                flipView(x, y);
                break;

            case MotionEvent.ACTION_UP:
            default:
                mDegree = 0;
                mCameraDegree = 0;
                invalidate();
                break;
        }

        return true;
    }

    private void flipView(float x, float y) {
        final int halfWidth = mWidth >> 1;
        final int halfHeight = mHeight >> 1;

        float tan1 = x - halfWidth;
        float tan2 = y - halfHeight;

        float degrees = (float) Math.toDegrees(Math.atan(tan2 / tan1));
        mCameraDegree = 45;

        if (tan1 > 0 && tan2 < 0) {
            mDegree = -degrees;
        } else if (tan1 < 0 && tan2 < 0) {
            mDegree = -degrees + 180;
        } else if (tan1 < 0 && tan2 > 0) {
            mDegree = -degrees + 180;
        } else if (tan1 > 0 && tan2 > 0) {
            mDegree = -degrees + 360;
        }
        invalidate();
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
        //绘制左边部分
        canvas.rotate(degree);
        canvas.drawBitmap(bitmap, -bitmapWidth >> 1, -bitmapHeight >> 1, mPaint);
        canvas.restore();

        canvas.save();
        //旋转坐标
        canvas.rotate(-degree);
        //截取画布右边部分
        canvas.clipRect(0, -halfHeight, halfWidth, halfHeight);
        //使用camera旋转
        camera.save();
        camera.rotateY(-mCameraDegree);
        camera.applyToCanvas(canvas);
        camera.restore();
        //画布旋转回来,绘制bitmap
        canvas.rotate(degree);
        canvas.drawBitmap(bitmap, -bitmapWidth >> 1, -bitmapHeight >> 1, mPaint);
        canvas.restore();
    }
}