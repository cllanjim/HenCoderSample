package com.example.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by LiuJin on 2018-03-14:8:43
 *
 * @author wuxio
 */
public abstract class BaseMeasureView extends View {

    public BaseMeasureView(Context context) {
        super(context);
    }

    public BaseMeasureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseMeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int paddingLeft = getPaddingLeft();
        final int paddingTop = getPaddingTop();
        final int paddingRight = getPaddingRight();
        final int paddingBottom = getPaddingBottom();

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int widthMostSize = MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight;
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int heightMostSize = MeasureSpec.getSize(heightMeasureSpec) - paddingTop - paddingBottom;

        /*
        * 当模式是 EXACTLY 时,有明确的值了,使用该值
        * 当模式是 AT_MOST 时,需要包裹住自己的内容,但是不能超过可使用的最大值
        * 当模式是 UNSPECIFIED 时,使用自己的内容大小就行,可以超过最大约束值, 注意:一般 模式是UNSPECIFIED,自己获得的约束值是0,某些版本不是0
        */

        int width = 0;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthMostSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            final int atMostWidth = getAtMostWidth();
            width = atMostWidth > widthMostSize ? widthMostSize : atMostWidth;
        } else if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = getAtMostWidth();
        }

        int height = 0;
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightMostSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            final int atMostHeight = getAtMostHeight();
            height = atMostHeight > heightMostSize ? heightMostSize : atMostHeight;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            height = getAtMostHeight();
        }

        setMeasuredDimension(
                width + paddingLeft + paddingRight,
                height + paddingTop + paddingBottom
        );
    }

    /**
     * 此方法会在 {@link #onMeasure(int, int)}中回调,决定宽度值
     *
     * @return 宽度是wrap_content 模式时宽度值
     */
    protected abstract int getAtMostWidth();

    /**
     * 此方法会在 {@link #onMeasure(int, int)}中回调,决定高度值
     *
     * @return 高度是wrap_content 模式时高度值
     */
    protected abstract int getAtMostHeight();
}
