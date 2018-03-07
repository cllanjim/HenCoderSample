package com.example.wuxio.hencodertest;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.wuxio.hencodertest.canvas.CanvasFragment;
import com.example.wuxio.hencodertest.paint.PaintFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Toolbar           mToolbar;
    private TextView          mToolbarTitle;
    private ViewPager         mViewPager;
    private TabLayout         mTabLayout;
    private FragmentContainer mFragmentContainer;

    private void assignViews() {
        mToolbar = findViewById(R.id.toolbar);
        mToolbarTitle = findViewById(R.id.toolbar_title);
        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabLayout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
        initFields();
    }

    private void initFields() {
        mFragmentContainer = new FragmentContainer();
        mViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    //============================内部类============================
    class MainPagerAdapter extends FragmentPagerAdapter {

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentContainer.getFragment(position);
        }

        @Override
        public int getCount() {
            return mFragmentContainer.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentContainer.getTitle(position);
        }
    }

    /**
     * 为 main 提供fragment
     */
    class FragmentContainer {

        private Fragment[] mFragments = {
                CanvasFragment.newInstance(),
                PaintFragment.newInstance(),

        };

        private String[] mTitles = {
                "canvas",
                "paint",
        };

        Fragment getFragment(int position) {
            return mFragments[position];
        }

        int size() {
            return mFragments.length;
        }

        String getTitle(int position) {
            return mTitles[position];
        }
    }
}