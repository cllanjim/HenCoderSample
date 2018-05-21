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

import com.example.wuxio.hencodertest.practiceFlip.FlipFragment;
import com.example.wuxio.hencodertest.practiceTape.Tape00Fragment;
import com.example.wuxio.hencodertest.practiceZan.ZanFragment;

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
        final MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }


            @Override
            public void onPageSelected(int position) {

                mToolbarTitle.setText(adapter.getPageTitle(position));
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(adapter.getCount() - 1);
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
                ZanFragment.newInstance(),
                Tape00Fragment.newInstance(R.layout.tape),
                FlipFragment.newInstance(R.layout.camera),
        };

        private String[] mTitles = {
                "练习-点赞",
                "练习-尺子",
                "练习-折纸"
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