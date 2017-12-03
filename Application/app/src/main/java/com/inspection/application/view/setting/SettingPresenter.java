package com.inspection.application.view.setting;

import android.support.annotation.NonNull;

import com.inspection.application.mode.bean.version.NewVersion;
import com.inspection.application.mode.callback.IObjectCallBack;
import com.inspection.application.mode.source.application.ApplicationDataSource;

import java.io.File;

import rx.subscriptions.CompositeSubscription;

/**
 * 设置
 */
final class SettingPresenter implements SettingContract.Presenter {
    private ApplicationDataSource mApplicationDataSource;
    private SettingContract.View mView;
    private CompositeSubscription mSubscription;

    SettingPresenter(ApplicationDataSource dataSource, SettingContract.View view) {
        this.mApplicationDataSource = dataSource;
        this.mView = view;
        mView.setPresenter(this);
        mSubscription = new CompositeSubscription();
    }


    @Override
    public void getNewVersion() {
        mSubscription.add(mApplicationDataSource.getNewVersion(new ApplicationDataSource.NewVersionCallBack() {
            @Override
            public void newVersion(NewVersion result) {
                mView.newVersionDialog(result);
            }

            @Override
            public void noNewVersion() {
                mView.currentVersion();
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

    @Override
    public void uploadUserPhoto(File file) {
        mSubscription.add(mApplicationDataSource.uploadUserPhoto(file, new IObjectCallBack<String>() {
            @Override
            public void onSuccess(@NonNull String s) {
                mView.uploadUserPhotoSuccess();
            }

            @Override
            public void onError(String message) {
                mView.uploadUserPhotoFail();
            }

            @Override
            public void noData() {

            }

            @Override
            public void onFinish() {
            }
        }));
    }

    @Override
    public void cleanCache() {

    }
}