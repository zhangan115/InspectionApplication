package com.inspection.application.mode;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.mode.bean.NewVersion;
import com.inspection.application.mode.bean.User;
import com.inspection.application.mode.callback.IListCallBack;

import rx.Subscription;

/**
 * 点检
 * Created by pingan on 2017/11/30.
 */

public interface InspectionDataSource {

    //用户
    interface LoadUserCallBack {

        void onLoginSuccess(@NonNull User user);

        void onLoginFail();

        void onFinish();
    }

    interface AutoLoginCallBack {

        void onNeedLogin();

        void onAutoFinish();
    }

    interface SplashCallBack extends AutoLoginCallBack {

        void showWelcome();

    }

    interface NewVersionCallBack {

        void newVersion(NewVersion result);

        void noNewVersion();
    }
    @NonNull
    Subscription login(@NonNull String name, @NonNull String pass, @NonNull LoadUserCallBack callBack);

    @NonNull
    Subscription autoLogin(@NonNull SplashCallBack callBack);

    @NonNull
    Subscription loadHistoryUser(@Nullable String userName, @NonNull IListCallBack<User> callBack);
}
