package com.inspection.application.mode.source.user;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.mode.bean.user.User;
import com.inspection.application.mode.callback.IListCallBack;

import rx.Subscription;

/**
 * 用户操作 data source
 * Created by pingan on 2017/12/2.
 */

public interface UserDataSource {

    interface LoadUserCallBack {

        void onLoginSuccess(@NonNull User user);

        void onLoginFail(@Nullable String failMessage);

        void onFinish();
    }

    /**
     * 登陆
     *
     * @param name     用户名
     * @param pass     密码
     * @param callBack 回调
     * @return 订阅
     */
    @NonNull
    Subscription login(@NonNull String name, @NonNull String pass, @NonNull LoadUserCallBack callBack);

    interface AutoLoginCallBack {

        void onNeedLogin();

        void onAutoFinish();

        void showWelcome();

        void showMessage(@NonNull String message);
    }

    /**
     * 自动登陆
     *
     * @param callBack 回调
     * @return 订阅
     */
    @NonNull
    Subscription autoLogin(@NonNull AutoLoginCallBack callBack);

}
