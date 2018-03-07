package com.example.wuxio.hencodertest.views.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by LiuJin on 2018-03-07:9:51
 */

public class DrawPath extends View {

    private Paint mPaint;
    // 初始化 Path 对象
    Path  path     = new Path();
    // 初始化 Path 对象
    Path  pathLine = new Path();
    RectF mRectF   = new RectF();

    {
        // 使用 path 对图形进行描述（这段描述代码不必看懂）
        RectF rectF = new RectF(200, 200, 400, 400);
        path.addArc(rectF, -225, 225);
        RectF rectF1 = new RectF(400, 200, 600, 400);
        path.arcTo(rectF1, -180, 225, false);
        path.lineTo(400, 542);
    }

    public DrawPath(Context context) {
        this(context, null, 0);
    }

    public DrawPath(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawPath(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(80);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制出 path 描述的图形（心形），大功告成
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, mPaint);

        path.addCircle(400, 900, 300, Path.Direction.CW);
        canvas.drawPath(path, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        pathLine.moveTo(50, 1300);
        pathLine.lineTo(150, 1450);
        pathLine.rLineTo(300, 0);
        canvas.drawPath(pathLine, mPaint);

        pathLine.moveTo(600, 200);
        pathLine.quadTo(900, 500, 1000, 100);
        canvas.drawPath(pathLine, mPaint);

        pathLine.moveTo(700, 700);
        pathLine.lineTo(800, 800);
        mRectF.set(900, 800, 1400, 1200);
        pathLine.arcTo(mRectF, -90, 90,true);
        canvas.drawPath(pathLine, mPaint);

        pathLine.moveTo(700, 1300);
        pathLine.lineTo(800, 1400);
        mRectF.set(900, 1400, 1400, 1800);
        pathLine.arcTo(mRectF, -90, 90,false);
        canvas.drawPath(pathLine, mPaint);
    }
}
