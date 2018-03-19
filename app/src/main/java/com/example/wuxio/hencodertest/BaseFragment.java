package com.example.wuxio.hencodertest;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LiuJin on 2018-03-07:9:47
 * 为fragment创建提供静态instance方法
 *
 * @author wuxio
 */
public abstract class BaseFragment extends Fragment {

    protected static final String KEY_LAYOUT_ID = "layoutId";

    /**
     * 提供一个Fragment
     *
     * @param layoutId 布局ID
     * @param supplier 实例化Fragment的方法
     * @param <T>      {@link BaseFragment}的子类
     * @return {@link BaseFragment}的子类
     */
    public static < T extends BaseFragment > T newInstance(@LayoutRes int layoutId, Supplier< T > supplier) {
        T t = supplier.get();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_LAYOUT_ID, layoutId);
        t.setArguments(bundle);
        return t;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        int anInt = arguments.getInt(KEY_LAYOUT_ID);
        return inflater.inflate(anInt, container, false);
    }
}
