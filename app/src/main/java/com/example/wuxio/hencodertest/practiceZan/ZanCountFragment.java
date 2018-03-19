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
public class ZanCountFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "TintImageFragment";

    public static ZanCountFragment newInstance(@LayoutRes int layoutId) {
        return ZanCountFragment.newInstance(layoutId, new Supplier< ZanCountFragment >() {
            @Override
            public ZanCountFragment get() {
                return new ZanCountFragment();
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
