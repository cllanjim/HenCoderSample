package com.example.wuxio.hencodertest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LiuJin on 2018-03-06:21:34
 *
 * @author wuxio
 */
public abstract class BasePagerFragment extends Fragment {

    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    /**
     * find view by id
     *
     * @param view root View
     */
    private void findViews(View view) {
        mViewPager = view.findViewById(R.id.viewPager);
        mViewPager.setAdapter(getPagerAdapter());
        mViewPager.setPageMargin(50);
        mViewPager.setOffscreenPageLimit(2);
    }

    protected abstract PagerAdapter getPagerAdapter();
}