package com.example.wuxio.hencodertest.paint;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.example.wuxio.hencodertest.BasePagerFragment;
import com.example.wuxio.hencodertest.R;
import com.example.wuxio.hencodertest.ShowViewFragment;

/**
 * Created by LiuJin on 2018-03-07:15:13
 */

public class PaintFragment extends BasePagerFragment {

    public static PaintFragment newInstance() {
        PaintFragment fragment = new PaintFragment();
        return fragment;
    }

    @Override
    protected PagerAdapter getPagerAdapter() {
        return new PaintPagerAdapter(getChildFragmentManager());
    }

    //============================内部类============================
    class PaintPagerAdapter extends FragmentPagerAdapter {

        private Fragment[] mFragments = {
                ShowViewFragment.newInstance(R.layout.paint_color),
                ShowViewFragment.newInstance(R.layout.paint_shader),
                ShowViewFragment.newInstance(R.layout.paint_shader_tile),
                ShowViewFragment.newInstance(R.layout.paint_porterduff),
                ShowViewFragment.newInstance(R.layout.paint_color_filter),
        };

        public PaintPagerAdapter(FragmentManager fm) {
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
