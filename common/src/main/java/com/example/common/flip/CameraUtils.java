package com.example.common.flip;

import android.content.Context;
import android.graphics.Matrix;

/**
 * Created by LiuJin on 2018-03-18:10:55
 *
 * @author wuxio
 */

public class CameraUtils {

    private static float[] mValues = new float[9];

    public static void fixCameraHitFace(Context context, Matrix matrix) {
        float scale = context.getResources().getDisplayMetrics().density;
        //获取数值
        matrix.getValues(mValues);
        //数值修正
        mValues[6] = mValues[6] / scale;
        //数值修正
        mValues[7] = mValues[7] / scale;
        //重新赋值
        matrix.setValues(mValues);
    }

}
