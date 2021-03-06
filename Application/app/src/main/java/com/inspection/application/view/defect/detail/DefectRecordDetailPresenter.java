package com.inspection.application.view.defect.detail;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.mode.bean.fault.FaultDetail;
import com.inspection.application.mode.callback.IObjectCallBack;
import com.inspection.application.mode.source.fault.FaultDataSource;

import rx.subscriptions.CompositeSubscription;

/**
 * 缺陷详情
 * Created by pingan on 2017/12/13.
 */
class DefectRecordDetailPresenter implements DefectRecordDetailContract.Presenter {

    private final FaultDataSource mFaultDataSource;
    private final DefectRecordDetailContract.View mView;
    private CompositeSubscription subscription;

    DefectRecordDetailPresenter(FaultDataSource mFaultDataSource, DefectRecordDetailContract.View mView) {
        this.mFaultDataSource = mFaultDataSource;
        this.mView = mView;
        mView.setPresenter(this);
        subscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void getFaultDetail(long id) {
        mView.showLoading();
        subscription.add(mFaultDataSource.getFaultDetail(id, new IObjectCallBack<FaultDetail>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull FaultDetail faultDetail) {
                mView.showData(faultDetail);
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
    public void pointFault(String info) {
        mView.showActionLoading();
        subscription.add(mFaultDataSource.pointFault(info, new IObjectCallBack<String>() {
            @Override
            public void onSuccess() {
                mView.success();
            }

            @Override
            public void onData(@NonNull String s) {

            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void noData() {

            }

            @Override
            public void onFinish() {
                mView.hideActionLoading();
            }
        }));
    }

    @Override
    public void sureFault(long faultId, String flowRemark) {
        mView.showActionLoading();
        subscription.add(mFaultDataSource.sureFault(faultId, flowRemark, new IObjectCallBack<String>() {
            @Override
            public void onSuccess() {
                mView.success();
            }

            @Override
            public void onData(@NonNull String s) {

            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void noData() {

            }

            @Override
            public void onFinish() {
                mView.hideActionLoading();
            }
        }));
    }

    @Override
    public void closeFault(long faultId, String content) {
        mView.showActionLoading();
        subscription.add(mFaultDataSource.closeFault(faultId, content, new IObjectCallBack<String>() {
            @Override
            public void onSuccess() {
                mView.success();
            }

            @Override
            public void onData(@NonNull String s) {

            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void noData() {

            }

            @Override
            public void onFinish() {
                mView.hideActionLoading();
            }
        }));
    }
}
