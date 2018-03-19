package com.example.wuxio.hencodertest.practiceZan;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.common.zan.ZanCountView;
import com.example.wuxio.hencodertest.BaseFragment;
import com.example.wuxio.hencodertest.R;
import com.example.wuxio.hencodertest.Supplier;

/**
 * Created by LiuJin on 2018-03-07:9:47
 *
 * @author wuxio
 */
public class ZanCountV2Fragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "TintImageFragment";

    public static ZanCountV2Fragment newInstance(@LayoutRes int layoutId) {
        return ZanCountV2Fragment.newInstance(layoutId, new Supplier< ZanCountV2Fragment >() {
            @Override
            public ZanCountV2Fragment get() {
                return new ZanCountV2Fragment();
            }
        });
    }

    private ZanCountView mZanCount;
    private Button       mAdd;
    private Button       mSub;

    private void assignViews() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    protected void findViews(View view) {
        mZanCount = view.findViewById(R.id.zanCount);
        mAdd = view.findViewById(R.id.add);
        mSub = view.findViewById(R.id.sub);

        mAdd.setOnClickListener(this);
        mSub.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                mZanCount.add();
                break;
            case R.id.sub:
                mZanCount.sub();
                break;
            default:
                break;
        }
    }
}
