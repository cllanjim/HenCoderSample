package com.example.wuxio.hencodertest.views.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ComposeShader;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.wuxio.hencodertest.R;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class BitmapPorterDuffShader extends View {

    private static final String TAG = "PorterDuff";
    private Paint  mPaint;
    private RectF  mRectF;
    private Bitmap mRectBitmap;
    private Bitmap mCircleBitmap;
    private int    mSize;
    private Shader mShaderSrc;
    private Shader mShaderSrcOut;
    private Shader mShaderSrcIn;
    private Shader mShaderSrcOver;
    private Shader mShaderSrcAtop;
    private Shader mShaderDst;
    private Shader mShaderDstOut;
    private Shader mShaderDstIn;
    private Shader mShaderDstOver;
    private Shader mShaderDstAtop;
    private Shader mShaderXOR;

    public BitmapPorterDuffShader(Context context) {
        this(context, null, 0);
    }

    public BitmapPorterDuffShader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BitmapPorterDuffShader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mPaint = new Paint();
        mPaint.setTextSize(50);
        mRectF = new RectF();

        //关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mRectBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.trans_ractangle);
        mCircleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.trans_circle);

        mSize = mRectBitmap.getWidth();

        Shader shader1 = new BitmapShader(mRectBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Shader shader2 = new BitmapShader(mCircleBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        mShaderSrc = new ComposeShader(shader1, shader2, PorterDuff.Mode.SRC);
        mShaderSrcOut = new ComposeShader(shader1, shader2, PorterDuff.Mode.SRC_OUT);
        mShaderSrcIn = new ComposeShader(shader1, shader2, PorterDuff.Mode.SRC_IN);
        mShaderSrcOver = new ComposeShader(shader1, shader2, PorterDuff.Mode.SRC_OVER);
        mShaderSrcAtop = new ComposeShader(shader1, shader2, PorterDuff.Mode.SRC_ATOP);

        mShaderDst = new ComposeShader(shader1, shader2, PorterDuff.Mode.DST);
        mShaderDstOut = new ComposeShader(shader1, shader2, PorterDuff.Mode.DST_OUT);
        mShaderDstIn = new ComposeShader(shader1, shader2, PorterDuff.Mode.DST_IN);
        mShaderDstOver = new ComposeShader(shader1, shader2, PorterDuff.Mode.DST_OVER);
        mShaderDstAtop = new ComposeShader(shader1, shader2, PorterDuff.Mode.DST_ATOP);

        mShaderXOR = new ComposeShader(shader1, shader2, PorterDuff.Mode.XOR);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //        canvas.drawBitmap(mRectBitmap, 0, 0, mPaint);
        //        canvas.drawText("src", 0, mSize + 50, mPaint);
        //
        //        canvas.drawBitmap(mCircleBitmap, mSize, 0, mPaint);
        //        canvas.drawText("dst", mSize, mSize + 50, mPaint);

        //换行 top=mSize + 100
        int top = mSize + 100;
        int bottom = top + mSize;
        mRectF.set(0, 0, mSize, mSize);
        mPaint.setShader(mShaderSrcAtop);
        canvas.drawRect(mRectF, mPaint);

        //没有效果(因为位置不对,默认就在左上角位置)
    }
}
