package com.example.common.zan;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.lang.reflect.Field;

/**
 * Created by LiuJin on 2018-03-09:18:19
 *
 * @author wuxio
 */
public class TintImageView extends android.support.v7.widget.AppCompatImageView {

    private static final String TAG = "TintImageView";
    protected Drawable mTintDrawable;

    public TintImageView(Context context) {
        this(context, null, 0);
    }

    public TintImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TintImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {

        // mutate方法 ? 多个View 会不会爆内存
        mTintDrawable = DrawableCompat.wrap(getDrawable()).mutate();

        try {
            Class< ? > superclass = getClass().getSuperclass();
            while (superclass != ImageView.class) {
                superclass = superclass.getSuperclass();
            }
            Field field = superclass.getDeclaredField("mDrawable");
            field.setAccessible(true);
            field.set(this, mTintDrawable);
            //Log.i(TAG, "init:" + "反射成功");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setColor(@ColorInt int color) {
        DrawableCompat.setTint(mTintDrawable, color);
        invalidate();
    }
}