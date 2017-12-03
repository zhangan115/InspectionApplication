package com.inspection.application.mode.callback;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 回调
 * Created by zhangan on 2017-06-22.
 */

public interface IObjectCallBack<T> {

    void onSuccess(@NonNull T t);

    void onError(@Nullable String message);

    void noData();

    void onFinish();
}
