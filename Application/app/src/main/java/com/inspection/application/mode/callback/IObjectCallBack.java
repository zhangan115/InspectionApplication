package com.inspection.application.mode.callback;

import android.support.annotation.NonNull;

/**
 * 回调
 * Created by zhangan on 2017-06-22.
 */

public interface IObjectCallBack<T> {

    void onSuccess(@NonNull T t);

    void onError(String message);

    void onFinish();
}
