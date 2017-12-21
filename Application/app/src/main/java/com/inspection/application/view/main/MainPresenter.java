package com.inspection.application.view.main;

import android.support.annotation.NonNull;

import com.inspection.application.mode.bean.version.NewVersion;
import com.inspection.application.mode.source.application.ApplicationDataSource;
import com.inspection.application.mode.source.application.ApplicationRepository;
import com.inspection.application.mode.source.news.NewsDataSource;

import rx.subscriptions.CompositeSubscription;

/**
 * 首页p
 * Created by pingan on 2017/12/2.
 */

public class MainPresenter implements MainContract.Presenter {

    private final ApplicationDataSource mAppDataSource;
    private final NewsDataSource mDataSource;
    private final MainContract.View mView;
    private CompositeSubscription mSubscriptions;

    MainPresenter(ApplicationDataSource mAppDataSource, NewsDataSource mDataSource, MainContract.View mView) {
        this.mAppDataSource = mAppDataSource;
        this.mView = mView;
        this.mDataSource = mDataSource;
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
    public void getMessage() {
        if (mDataSource != null) {
            mDataSource.startAutoGetMessage();
        }
    }

    @Override
    public void getUnReadCount() {
        if (mDataSource != null) {
            mView.showUnReadCount(mDataSource.getUnReadCount());
        }
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        if (mDataSource != null) {
            mDataSource.cleanSub();
        }
        mSubscriptions.clear();
    }
}
