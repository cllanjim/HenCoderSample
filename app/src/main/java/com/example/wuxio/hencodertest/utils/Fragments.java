package com.example.wuxio.hencodertest.utils;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by LiuJin on 2018-03-06:21:37
 *
 * fragment 工具类
 */
public class Fragments {

    /**
     * 添加一个fragment 到容器
     *
     * @param manager     manager
     * @param containerID 容器id
     * @param fragment    需要添加的fragment
     */
    public static void addFragment(FragmentManager manager, @IdRes int containerID, Fragment fragment) {
        manager.beginTransaction().add(containerID, fragment).commit();
    }
}
