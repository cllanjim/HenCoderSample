package com.example.wuxio.hencodertest.canvas;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.example.wuxio.hencodertest.ShowViewFragment;
import com.example.wuxio.hencodertest.BasePagerFragment;
import com.example.wuxio.hencodertest.R;

/**
 * Created by LiuJin on 2018-03-06:21:34
 */

public class CanvasFragment extends BasePagerFragment {

    public static CanvasFragment newInstance() {
        CanvasFragment fragment = new CanvasFragment();
        return fragment;
    }

    @Override
    protected PagerAdapter getPagerAdapter() {
        return new DrawAdapter(getChildFragmentManager());
    }

    //============================内部类============================

    class DrawAdapter extends FragmentPagerAdapter {

        private Fragment[] mFragments = {
                ShowViewFragment.newInstance(R.layout.canvas_draw_color),
                ShowViewFragment.newInstance(R.layout.canvas_draw_circle),
                ShowViewFragment.newInstance(R.layout.canvas_draw_rect),
                ShowViewFragment.newInstance(R.layout.canvas_draw_point),
                ShowViewFragment.newInstance(R.layout.canvas_draw_oval),
                ShowViewFragment.newInstance(R.layout.canvas_draw_line),
                ShowViewFragment.newInstance(R.layout.canvas_draw_round_rect),
                ShowViewFragment.newInstance(R.layout.canvas_draw_arc),
                ShowViewFragment.newInstance(R.layout.canvas_draw_path),
                ShowViewFragment.newInstance(R.layout.canvas_draw_bitmap),
                ShowViewFragment.newInstance(R.layout.canvas_draw_text),
                ShowViewFragment.newInstance(R.layout.canvas_draw_histogram),
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