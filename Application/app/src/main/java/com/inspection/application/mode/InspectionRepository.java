package com.inspection.application.mode;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.mode.bean.User;
import com.inspection.application.mode.callback.IListCallBack;

import rx.Subscription;

/**
 * Created by pingan on 2017/11/30.
 */

public class InspectionRepository implements InspectionDataSource {

    @NonNull
    @Override
    public Subscription login(@NonNull String name, @NonNull String pass, @NonNull LoadUserCallBack callBack) {
        return null;
    }

    @NonNull
    @Override
    public Subscription autoLogin(@NonNull SplashCallBack callBack) {
        return null;
    }

    @NonNull
    @Override
    public Subscription loadHistoryUser(@Nullable String userName, @NonNull IListCallBack<User> callBack) {
        return null;
    }
}
