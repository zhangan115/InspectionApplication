package com.inspection.application.view.main;

import android.support.annotation.NonNull;

import com.inspection.application.mode.bean.version.NewVersion;
import com.inspection.application.mode.source.application.ApplicationDataSource;
import com.inspection.application.mode.source.application.ApplicationRepository;

import rx.subscriptions.CompositeSubscription;

/**
 * 首页p
 * Created by pingan on 2017/12/2.
 */

public class MainPresenter implements MainContract.Presenter {

    private final ApplicationDataSource mAppDataSource;
    private final MainContract.View mView;
    private CompositeSubscription mSubscriptions;

    MainPresenter(ApplicationDataSource mAppDataSource, MainContract.View mView) {
        this.mAppDataSource = mAppDataSource;
        this.mView = mView;
        mView.setPresenter(this);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void getNewVersion() {
        mSubscriptions.add(mAppDataSource.getNewVersion(new ApplicationDataSource.NewVersionCallBack() {
            @Override
            public void newVersion(NewVersion result) {
                mView.showNewVersion(result);
            }

            @Override
            public void noNewVersion() {

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
