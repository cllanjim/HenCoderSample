package com.example.wuxio.hencodertest.clip;

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

public class ClipFragment extends BasePagerFragment {

    public static ClipFragment newInstance() {
        ClipFragment fragment = new ClipFragment();
        return fragment;
    }

    @Override
    protected PagerAdapter getPagerAdapter() {
        return new PaintPagerAdapter(getChildFragmentManager());
    }

    //============================内部类============================
    class PaintPagerAdapter extends FragmentPagerAdapter {

        private Fragment[] mFragments = {
                ShowViewFragment.newInstance(R.layout.canvas_clip_rect),
                ShowViewFragment.newInstance(R.layout.canvas_clip_path),
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