package com.inspection.application.view.equipment;

import android.support.annotation.NonNull;

import com.inspection.application.mode.source.equipment.EquipmentDataSource;

import rx.subscriptions.CompositeSubscription;

/**
 * 设备P
 */
final class EquipListPresenter implements EquipListContract.Presenter {
    private final EquipmentDataSource mRepository;
    private final EquipListContract.View mView;
    @NonNull
    private CompositeSubscription subscription;

    EquipListPresenter(EquipmentDataSource repository, EquipListContract.View view) {
        this.mRepository = repository;
        this.mView = view;
        mView.setPresenter(this);
        subscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        subscription.clear();
    }

    @Override
    public void getEquipList() {
        mView.showLoading();
    }
}