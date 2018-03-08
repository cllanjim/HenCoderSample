package com.example.wuxio.hencodertest.change2d;

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

public class Change2dFragment extends BasePagerFragment {

    public static Change2dFragment newInstance() {
        Change2dFragment fragment = new Change2dFragment();
        return fragment;
    }

    @Override
    protected PagerAdapter getPagerAdapter() {
        return new PaintPagerAdapter(getChildFragmentManager());
    }

    //============================内部类============================
    class PaintPagerAdapter extends FragmentPagerAdapter {

        private Fragment[] mFragments = {
                ShowViewFragment.newInstance(R.layout.change_translate),
                ShowViewFragment.newInstance(R.layout.change_scale),
                ShowViewFragment.newInstance(R.layout.change_skew),
                ShowViewFragment.newInstance(R.layout.change_matrix),
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