package com.example.wuxio.hencodertest.practiceZan;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.wuxio.hencodertest.BaseFragment;
import com.example.wuxio.hencodertest.Supplier;

/**
 * Created by LiuJin on 2018-03-07:9:47
 *
 * @author wuxio
 */
public class ZanCountV3Fragment extends BaseFragment {

    private static final String TAG = "TintImageFragment";

    public static ZanCountV3Fragment newInstance(@LayoutRes int layoutId) {
        return ZanCountV3Fragment.newInstance(layoutId, new Supplier< ZanCountV3Fragment >() {
            @Override
            public ZanCountV3Fragment get() {
                return new ZanCountV3Fragment();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
