package com.core.adapter;

/**
 * Created by sufun_job on 2016/5/25.
 *
 * @description 事件回调接口
 */
public interface IAdapterEventDelegate<T> {
    void onEevnt(int pos,T t,int EventType);
}
