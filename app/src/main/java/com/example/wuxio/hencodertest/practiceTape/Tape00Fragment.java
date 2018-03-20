package com.example.wuxio.hencodertest.practiceTape;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wuxio.hencodertest.R;

/**
 * Created by LiuJin on 2018-03-07:9:47
 *
 * @author wuxio
 */
public class Tape00Fragment extends Fragment {

    private static final String KEY_layoutId = "layoutId";

    public static Tape00Fragment newInstance(@LayoutRes int layoutId) {
        Tape00Fragment fragment = new Tape00Fragment();
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    protected void findViews(View view) {
        View click = view.findViewById(R.id.toExample);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TapeActivity.start(getContext());
            }
        });
    }
}
