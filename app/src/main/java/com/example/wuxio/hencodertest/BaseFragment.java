package com.example.wuxio.hencodertest;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LiuJin on 2018-03-07:9:47
 *
 * @author wuxio
 */
public class BaseFragment extends Fragment {

    private static final String KEY_layoutId = "layoutId";

    public static < T extends BaseFragment > T newInstance(@LayoutRes int layoutId, Supplier< T > supplier) {
        T t = supplier.get();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_layoutId, layoutId);
        t.setArguments(bundle);
        return t;
    }

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
    }
}
