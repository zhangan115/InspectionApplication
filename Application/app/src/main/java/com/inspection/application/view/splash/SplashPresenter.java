package com.inspection.application.view.splash;

import android.support.annotation.NonNull;


import com.inspection.application.mode.source.application.ApplicationDataSource;
import com.inspection.application.mode.source.application.ApplicationRepository;
import com.inspection.application.mode.source.user.UserDataSource;
import com.inspection.application.mode.source.user.UserRepository;

import rx.subscriptions.CompositeSubscription;

/**
 * 启动屏幕界面
 * Created by zhangan on 2017-07-24.
 */

class SplashPresenter implements SplashContract.Presenter {

    private UserDataSource mUserDataSource;
    private SplashContract.View mView;
    @NonNull
    private CompositeSubscription mSubscriptions;

    SplashPresenter(UserDataSource userDataSource, SplashContract.View view) {
        this.mUserDataSource = userDataSource;
        this.mView = view;
        mView.setPresenter(this);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        mSubscriptions.add(mUserDataSource.autoLogin(new UserDataSource.AutoLoginCallBack() {
            @Override
            public void showWelcome() {
                mView.showWelcome();
            }

            @Override
            public void showMessage(@NonNull String message) {

            }

            @Override
            public void onNeedLogin() {
                mView.needLogin();
            }

            @Override
            public void onAutoFinish() {
                mView.openHome();
            }
        }));
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
