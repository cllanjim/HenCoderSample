package com.example.common.engine;

/**
 * Created by LiuJin on 2018-03-19:9:42
 *
 * @author wuxio
 */

public interface Fraction {

    /**
     * 开始计算进度
     */
    void start();

    /**
     * 返回一个进度值
     *
     * @return 进度值
     */
    float getFraction();

    /**
     * 验证是否已经完成
     *
     * @return true:正在运行中
     */
    boolean isRunning();
}
