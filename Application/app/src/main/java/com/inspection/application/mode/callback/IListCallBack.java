package com.inspection.application.mode.callback;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * 回调
 * Created by zhangan on 2017-06-22.
 */

public interface IListCallBack<T> {

    void onSuccess();

    void onData(@NonNull List<T> list);

    void onError(@Nullable String message);

    void onFinish();

    void noData();
}
