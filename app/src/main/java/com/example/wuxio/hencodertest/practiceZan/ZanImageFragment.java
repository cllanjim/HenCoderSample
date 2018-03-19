package com.example.wuxio.hencodertest.practiceZan;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.common.zan.ZanImageView;
import com.example.wuxio.hencodertest.BaseFragment;
import com.example.wuxio.hencodertest.R;
import com.example.wuxio.hencodertest.Supplier;

/**
 * Created by LiuJin on 2018-03-07:9:47
 *
 * @author wuxio
 */
public class ZanImageFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "TintImageFragment";

    private ZanImageView mZanImage;
    private Button       mAdd;
    private Button       mSub;

    public static ZanImageFragment newInstance(@LayoutRes int layoutId) {
        return ZanImageFragment.newInstance(layoutId, new Supplier< ZanImageFragment >() {
            @Override
            public ZanImageFragment get() {
                return new ZanImageFragment();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mZanImage = view.findViewById(R.id.zanImage);
        mAdd = view.findViewById(R.id.add);
        mSub = view.findViewById(R.id.sub);

        mAdd.setOnClickListener(this);
        mSub.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                mZanImage.animateZan();
                break;
            case R.id.sub:
                mZanImage.reverseAnimateZan();
                break;
            default:
                break;
        }
    }
}
