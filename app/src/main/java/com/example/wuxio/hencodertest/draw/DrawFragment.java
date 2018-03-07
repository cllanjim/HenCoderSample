package com.example.wuxio.hencodertest.draw;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wuxio.hencodertest.R;

/**
 * Created by LiuJin on 2018-03-06:21:34
 */

public class DrawFragment extends Fragment {

    private ViewPager mViewPager;

    public static DrawFragment newInstance() {
        DrawFragment fragment = new DrawFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_draw, container, false);
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
        mViewPager.setAdapter(new DrawAdapter(getChildFragmentManager()));
        mViewPager.setPageMargin(50);
        mViewPager.setOffscreenPageLimit(2);
    }

    //============================内部类============================

    class DrawAdapter extends FragmentPagerAdapter {

        private Fragment[] mFragments = {
                DrawSubFragment.newInstance(R.layout.draw_color),
                DrawSubFragment.newInstance(R.layout.draw_circle),
                DrawSubFragment.newInstance(R.layout.draw_rect),
                DrawSubFragment.newInstance(R.layout.draw_point),
                DrawSubFragment.newInstance(R.layout.draw_oval),
                DrawSubFragment.newInstance(R.layout.draw_line),
                DrawSubFragment.newInstance(R.layout.draw_round_rect),
                DrawSubFragment.newInstance(R.layout.draw_arc),
                DrawSubFragment.newInstance(R.layout.draw_path),
        };

        public DrawAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return mFragments.length;
        }
    }
}