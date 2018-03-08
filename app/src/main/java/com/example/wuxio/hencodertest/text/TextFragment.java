package com.example.wuxio.hencodertest.text;

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

public class TextFragment extends BasePagerFragment {

    public static TextFragment newInstance() {
        TextFragment fragment = new TextFragment();
        return fragment;
    }

    @Override
    protected PagerAdapter getPagerAdapter() {
        return new PaintPagerAdapter(getChildFragmentManager());
    }

    //============================内部类============================
    class PaintPagerAdapter extends FragmentPagerAdapter {

        private Fragment[] mFragments = {
                ShowViewFragment.newInstance(R.layout.text_static),
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
