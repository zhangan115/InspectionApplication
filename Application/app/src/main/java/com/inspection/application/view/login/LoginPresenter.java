package com.inspection.application.view.login;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.app.App;
import com.inspection.application.mode.InspectionDataSource;
import com.inspection.application.mode.InspectionRepository;
import com.inspection.application.mode.bean.User;
import com.inspection.application.mode.callback.IListCallBack;

import java.util.List;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;


/**
 * (P层)登陆
 * Created by zhangan on 2016-12-06.
 */

final class LoginPresenter implements LoginContract.Presenter {

    private InspectionRepository mRepository;
    private LoginContract.View mView;
    @NonNull
    private CompositeSubscription mSubscriptions;

    @Inject
    LoginPresenter(InspectionRepository repository, LoginContract.View view) {
        this.mRepository = repository;
        this.mView = view;
        mSubscriptions = new CompositeSubscription();
    }

    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }

    @Override
    public void login(String name, String pass) {
        mView.loginLoading();
        mSubscriptions.add(mRepository.login(name, pass, new InspectionDataSource.LoadUserCallBack() {

            @Override
            public void onLoginSuccess(@NonNull User user) {
                App.getInstance().setCurrentUser(user);
                mView.loginSuccess();
            }

            @Override
            public void onLoginFail() {
                mView.loginFail();
            }

            @Override
            public void onFinish() {
                mView.loginHideLoading();
            }

        }));
    }

    @Override
    public void autoLogin() {

    }

    @Override
    public void loadHistoryUser(@NonNull String userName, @Nullable List<User> userList) {
        mSubscriptions.add(mRepository.loadHistoryUser(userName, new IListCallBack<User>() {
            @Override
            public void onSuccess(@NonNull List<User> list) {
                mView.showHistoryUser(list);
            }

            @Override
            public void onError(String message) {
                mView.showMessage(message);
            }

            @Override
            public void onFinish() {

            }
        }));
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
