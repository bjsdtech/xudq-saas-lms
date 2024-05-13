package com.xudq.sd.common.utils.concurrent;

/**
 * 执行器回调
 *
 * @author: xudq
 * @date: 2022-06-26 14:43
 */
@SuppressWarnings("unused")
public interface ExecutorCallback<T> {
    /**
     * 获得执行许可时回调
     *
     * @return 结果
     * @throws Throwable 异常
     */
    T acquire() throws Throwable;

    /**
     * 未获得执行许可时回调
     *
     * @return 结果
     * @throws Throwable 异常
     */
    T noAcquire() throws Throwable;
}
