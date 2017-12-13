package com.inspection.application.view.rule;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.mode.bean.customer.StandBean;
import com.inspection.application.mode.callback.IObjectCallBack;
import com.inspection.application.mode.source.application.ApplicationDataSource;

import rx.subscriptions.CompositeSubscription;

/**
 * 规章制度
 * Created by pingan on 2017/12/12.
 */

public class RulePresenter implements RuleContract.Presenter {
    private final ApplicationDataSource dataSource;
    private final RuleContract.View mView;
    private CompositeSubscription mSubscriptions;

    public RulePresenter(ApplicationDataSource dataSource, RuleContract.View mView) {
        this.dataSource = dataSource;
        this.mView = mView;
        mView.setPresenter(this);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        mView.showLoading();
        mSubscriptions.add(dataSource.getStandData(new IObjectCallBack<StandBean>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull StandBean standBean) {
                mView.showData(standBean);
            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void noData() {
                mView.noData();
            }

            @Override
            public void onFinish() {
                mView.hideLoading();
            }
        }));
    }

    @Override
    public void unSubscribe() {

    }
}
