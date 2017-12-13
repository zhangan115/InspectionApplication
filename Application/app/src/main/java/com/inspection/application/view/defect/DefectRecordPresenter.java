package com.inspection.application.view.defect;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.mode.bean.fault.FaultList;
import com.inspection.application.mode.callback.IListCallBack;
import com.inspection.application.mode.source.fault.FaultDataSource;
import com.inspection.application.view.fault.FaultContract;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

/**
 * 缺陷
 * Created by pingan on 2017/12/13.
 */

class DefectRecordPresenter implements DefectRecordContract.Presenter {

    private final FaultDataSource mFaultDataSource;
    private final DefectRecordContract.View mView;
    private CompositeSubscription subscription;

    DefectRecordPresenter(FaultDataSource mFaultDataSource, DefectRecordContract.View mView) {
        this.mFaultDataSource = mFaultDataSource;
        this.mView = mView;
        mView.setPresenter(this);
        subscription = new CompositeSubscription();
    }

    @Override
    public void getFaultList(@NonNull String info) {
        subscription.add(mFaultDataSource.getFaultList(info, new IListCallBack<FaultList>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<FaultList> list) {
                mView.showFaultList(list);
            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void onFinish() {
                mView.hideLoading();
            }

            @Override
            public void noData() {
                mView.noData();
            }
        }));
    }

    @Override
    public void getFaultListMore(@NonNull String info) {
        subscription.add(mFaultDataSource.getFaultList(info, new IListCallBack<FaultList>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<FaultList> list) {
                mView.showMoreFaultList(list);
            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void onFinish() {
                mView.hideLoadingMore();
            }

            @Override
            public void noData() {
                mView.noMoreData();
            }
        }));
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        subscription.clear();
    }
}
