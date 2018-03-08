package com.example.wuxio.hencodertest.views.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.wuxio.hencodertest.R;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class CameraRotate extends View {

    private Paint  mPaint;
    private Bitmap mHuluwa;
    private Camera mCamera;

    public CameraRotate(Context context) {
        this(context, null, 0);
    }

    public CameraRotate(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraRotate(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();

        mHuluwa = BitmapFactory.decodeResource(getResources(), R.drawable.huluwa);

        mCamera = new Camera();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.save();
//        mCamera.save();
//        mCamera.rotateX(20); // 旋转 Camera 的三维空间
//        mCamera.applyToCanvas(canvas); // 把旋转投影到 Canvas
//        mCamera.restore();
//        canvas.drawBitmap(mHuluwa, 50, 50, mPaint);
//        canvas.restore();

        canvas.save();
        mCamera.save();
        mCamera.rotateY(20); // 旋转 Camera 的三维空间
        mCamera.applyToCanvas(canvas); // 把旋转投影到 Canvas
        mCamera.restore();
        canvas.drawBitmap(mHuluwa, 50, 550, mPaint);
        canvas.restore();
    }
}
