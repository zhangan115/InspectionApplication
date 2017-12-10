package com.inspection.application.view.secure;

import android.support.annotation.NonNull;

import com.inspection.application.mode.bean.secure.SecureBean;
import com.inspection.application.mode.callback.IObjectCallBack;
import com.inspection.application.mode.source.task.TaskDataSource;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Yangzb on 2017/7/23 16:52
 * E-mail：yangzongbin@si-top.com
 */
final class SecurePresenter implements SecureContract.Presenter {
    private TaskDataSource mRepository;
    private SecureContract.View mView;
    @NonNull
    private CompositeSubscription mSubscription;

    SecurePresenter(TaskDataSource dataSource, SecureContract.View view) {
        this.mRepository = dataSource;
        this.mView = view;
        mView.setPresenter(this);
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void getSecureInfo(long securityId) {
        mView.showLoading();
        mSubscription.add(mRepository.getSecureInfo(securityId, new IObjectCallBack<SecureBean>() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull SecureBean secureBean) {
                mView.showData(secureBean);
            }

            @Override
            public void onError(String message) {
                mView.noData();
            }

            @Override
            public void noData() {

            }

            @Override
            public void onFinish() {
                mView.hideLoading();
            }
        }));
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscription.clear();
    }
}