package com.inspection.application.mode.callback;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * 回调
 * Created by zhangan on 2017-06-22.
 */

public interface IListCallBack<T> {

    void onSuccess(@NonNull List<T> list);

    void onError(String message);

    void onFinish();
}
