package com.example.common;

import android.graphics.Paint;

/**
 * Created by LiuJin on 2018-03-16:12:22
 * @author wuxio
 */
public class BaseLineUtil {

    public static float getBaseLine(Paint paint) {
        float fontSpacing = paint.getFontSpacing();
        Paint.FontMetrics metrics = paint.getFontMetrics();
        float ascent = metrics.ascent;
        float descent = metrics.descent;
        float height = descent - ascent;

        float extra = (fontSpacing - height) / 2;
        return extra - ascent;
    }

}
