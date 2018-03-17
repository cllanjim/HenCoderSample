package com.example.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

/**
 * Created by LiuJin on 2018-03-14:8:43
 *
 * @author wuxio
 */
public abstract class BaseMeasureGravityView extends View {

    private static final String TAG = "BaseMeasureGravityView";

    private static final int[] ATTRS = {
            android.R.attr.gravity
    };

    /**
     * android:gravity="XXX" 属性
     */
    protected int mGravity;

    protected int mGravityLeft;
    protected int mGravityTop;

    public BaseMeasureGravityView(Context context) {
        this(context, null, 0);
    }

    public BaseMeasureGravityView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseMeasureGravityView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @CallSuper
    protected void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
        if (a.hasValue(0)) {
            mGravity = a.getInt(0, Gravity.START | Gravity.TOP);
        }
        a.recycle();
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
            if (atMostWidth < 0) {
                width = widthMostSize;
            } else {
                width = atMostWidth > widthMostSize ? widthMostSize : atMostWidth;
            }
        } else if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = getAtMostWidth();
        }

        int height = 0;
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightMostSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            final int atMostHeight = getAtMostHeight();
            if (atMostHeight < 0) {
                height = heightMostSize;
            } else {
                height = atMostHeight > heightMostSize ? heightMostSize : atMostHeight;
            }
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            height = getAtMostHeight();
        }

        setMeasuredDimension(
                width + paddingLeft + paddingRight,
                height + paddingTop + paddingBottom
        );
    }

    /**
     * 此方法会在 {@link #onMeasure(int, int)}中回调,决定当宽度是 wrap_content时,宽度值,
     * 不必考虑padding,只需要返回自己需要使用的大小,
     *
     * @return 宽度是wrap_content 模式时宽度值,或者 -1 表示使用父布局传给自己的尺寸
     */
    protected abstract int getAtMostWidth();

    /**
     * 此方法会在 {@link #onMeasure(int, int)}中回调,决定当高度是 wrap_content时,高度值,
     * 不必考虑padding,只需要返回自己需要使用的大小,
     *
     * @return 高度是wrap_content 模式时高度值,或者 -1 表示使用父布局传给自己的尺寸
     */
    protected abstract int getAtMostHeight();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int[] ints = handleGravity(w, h);
        mGravityLeft = ints[0];
        mGravityTop = ints[1];
    }

    /**
     * 处理 gravity
     *
     * @param w : 宽度
     * @param h : 高度
     * @return : 偏移量
     */
    protected int[] handleGravity(int w, int h) {
        final int layoutDirection = this.getLayoutDirection();
        final int absoluteGravity = Gravity.getAbsoluteGravity(mGravity, layoutDirection);
        final int verticalGravity = mGravity & Gravity.VERTICAL_GRAVITY_MASK;

        final int paddingLeft = getPaddingLeft();
        final int paddingTop = getPaddingTop();
        final int paddingRight = getPaddingRight();
        final int paddingBottom = getPaddingBottom();

        int[] result = new int[2];

        int atMostWidth = getAtMostWidth();
        int widthNeed = atMostWidth + paddingLeft + paddingRight;
        if (widthNeed < w) {
            switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
                case Gravity.CENTER_HORIZONTAL:
                    result[0] = (w - widthNeed) / 2 + paddingLeft - paddingRight;
                    break;

                case Gravity.RIGHT:
                    result[0] = w - widthNeed - paddingRight;
                    break;

                case Gravity.LEFT:
                default:
                    result[0] = paddingLeft;
            }
        }

        int atMostHeight = getAtMostHeight();
        int heightNeed = atMostHeight + paddingTop + paddingBottom;
        if (heightNeed < h) {
            switch (verticalGravity) {
                case Gravity.CENTER_VERTICAL:
                    result[1] = (h - heightNeed) / 2 + paddingTop - paddingBottom;
                    break;

                case Gravity.BOTTOM:
                    result[1] = h - heightNeed - paddingBottom;
                    break;

                case Gravity.TOP:
                default:
                    result[1] = paddingTop;

            }
        }
        return result;
    }
}
