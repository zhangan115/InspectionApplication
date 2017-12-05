package com.inspection.application.view.fault;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.mode.bean.image.Image;
import com.inspection.application.mode.source.fault.FaultDataSource;

import org.json.JSONObject;

import rx.subscriptions.CompositeSubscription;

/**
 * 异常 p
 * Created by pingan on 2017/12/5.
 */

class FaultPresenter implements FaultContract.Presenter {
    private final FaultDataSource mFaultDataSource;
    private final FaultContract.View mView;
    private CompositeSubscription subscription;

    FaultPresenter(FaultDataSource mFaultDataSource, FaultContract.View mView) {
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
    public void getCacheFromDb(@Nullable Long taskId, @Nullable Long roomId, @Nullable Long equipmentId) {

    }

    @Override
    public void uploadPhoto(@NonNull Image image) {

    }

    @Override
    public void uploadFaultData(@NonNull JSONObject jsonObject) {

    }

    @Override
    public void getUserFlowList() {

    }
}
