package com.example.wuxio.hencodertest.draw;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LiuJin on 2018-03-07:9:47
 */

public class DrawSubFragment extends Fragment {

    private static final String KEY_layoutId = "layoutId";

    public static DrawSubFragment newInstance(@LayoutRes int layoutId) {
        DrawSubFragment fragment = new DrawSubFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_layoutId, layoutId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        int anInt = arguments.getInt(KEY_layoutId);
        View view = inflater.inflate(anInt, container, false);
        return view;
    }
}
