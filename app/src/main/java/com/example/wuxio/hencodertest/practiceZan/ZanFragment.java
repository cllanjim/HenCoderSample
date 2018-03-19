package com.example.wuxio.hencodertest.practiceZan;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.example.wuxio.hencodertest.BasePagerFragment;
import com.example.wuxio.hencodertest.R;

/**
 * Created by LiuJin on 2018-03-07:15:13
 *
 * @author wuxio
 */

public class ZanFragment extends BasePagerFragment {

    public static ZanFragment newInstance() {
        return new ZanFragment();
    }

    @Override
    protected PagerAdapter getPagerAdapter() {
        return new PaintPagerAdapter(getChildFragmentManager());
    }

    //============================内部类============================
    class PaintPagerAdapter extends FragmentPagerAdapter {

        private Fragment[] mFragments = {
                TintImageFragment.newInstance(R.layout.zan_1),
                ZanCountFragment.newInstance(R.layout.zan_2),
                ZanCountV2Fragment.newInstance(R.layout.zan_3),
                ZanCountV3Fragment.newInstance(R.layout.zan_4),
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