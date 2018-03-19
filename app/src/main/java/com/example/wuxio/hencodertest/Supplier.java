package com.example.wuxio.hencodertest;

/**
 * Created by LiuJin on 2018-03-19:7:04
 * 提供一个类
 *
 * @param <T> 接口可以提供的类
 * @author wuxio
 */
public interface Supplier < T > {

    /**
     * @return 一个 T
     */
    T get();

}
