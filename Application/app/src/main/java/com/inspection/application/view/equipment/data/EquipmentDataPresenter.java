package com.inspection.application.view.equipment.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.mode.bean.equipment.CheckValue;
import com.inspection.application.mode.bean.equipment.InspectionData;
import com.inspection.application.mode.callback.IObjectCallBack;
import com.inspection.application.mode.source.equipment.EquipmentDataSource;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by pingan on 2017/7/3.
 */

class EquipmentDataPresenter implements EquipmentDataContact.Presenter {

    private EquipmentDataSource mRepository;
    private EquipmentDataContact.View mView;
    @NonNull
    private CompositeSubscription mSubscription;

    EquipmentDataPresenter(EquipmentDataSource repository, EquipmentDataContact.View view) {
        this.mRepository = repository;
        this.mView = view;
        mSubscription = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscription.clear();
    }

    @Override
    public void getCheckData(long equipId) {
        mSubscription.add(mRepository.getCheckData(equipId, new IObjectCallBack<InspectionData>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull InspectionData inspectionData) {
                mView.showCheckData(inspectionData);
            }

            @Override
            public void onError(@Nullable String message) {

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
    public void getCheckValue(long equipId, int inspectId) {
        mView.showLoading();
        mSubscription.add(mRepository.getCheckValue(equipId, inspectId, new IObjectCallBack<CheckValue>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull CheckValue checkValue) {
                mView.showCheckValue(checkValue);
            }

            @Override
            public void onError(@Nullable String message) {

            }

            @Override
            public void noData() {

            }

            @Override
            public void onFinish() {

            }
        }));
    }
}
