package com.inspection.application.mode.callback;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 回调
 * Created by zhangan on 2017-06-22.
 */

public interface IObjectCallBack<T> {

    /**
     * 请求成功
     */
    void onSuccess();

    /**
     * 获取到数据
     *
     * @param t 数据
     */
    void onData(@NonNull T t);

    /**
     * 出错了
     *
     * @param message 出错信息
     */
    void onError(@Nullable String message);

    /**
     * 没有数据
     */
    void noData();

    /**
     * 请求完成
     */
    void onFinish();
}
