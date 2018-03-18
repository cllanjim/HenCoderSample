package com.example.wuxio.hencodertest.practiceZan;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.common.zan.TintImageView;
import com.example.wuxio.hencodertest.BaseFragment;
import com.example.wuxio.hencodertest.R;
import com.example.wuxio.hencodertest.Supplier;

/**
 * Created by LiuJin on 2018-03-07:9:47
 *
 * @author wuxio
 */
public class TintImageFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG          = "TintImageFragment";
    private static final String KEY_layoutId = "layoutId";

    public static TintImageFragment newInstance(@LayoutRes int layoutId) {
        TintImageFragment fragment = TintImageFragment.newInstance(layoutId, new Supplier< TintImageFragment >() {
            @Override
            public TintImageFragment get() {
                return new TintImageFragment();
            }
        });
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_layoutId, layoutId);
        fragment.setArguments(bundle);
        return fragment;
    }

    private ObjectAnimator mAnimator;

    private TintImageView mTintImage;
    private Button        mClick;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        int anInt = arguments.getInt(KEY_layoutId);
        View view = inflater.inflate(anInt, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    protected void findViews(View view) {
        mTintImage = view.findViewById(R.id.tintImage);
        mClick = view.findViewById(R.id.click);

        mClick.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.click:
                changeColor();
                break;
            default:
                break;
        }
    }

    private void changeColor() {

        if (mAnimator == null) {
            mAnimator = ObjectAnimator.ofInt(
                    mTintImage,
                    "color",
                    Color.BLACK,
                    Color.parseColor("#ffd700"),
                    Color.RED
            );
            mAnimator.setEvaluator(new ArgbEvaluator());
        }
        mAnimator.setDuration(2000).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
            mAnimator = null;
        }
    }
}
