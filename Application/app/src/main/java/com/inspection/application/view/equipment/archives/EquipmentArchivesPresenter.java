package com.inspection.application.view.equipment.archives;

import android.support.annotation.NonNull;

import com.inspection.application.mode.source.equipment.EquipmentDataSource;

import rx.subscriptions.CompositeSubscription;

/**
 * 设备档案
 * Created by zhangan on 2017/10/12.
 */

public class EquipmentArchivesPresenter implements EquipmentArchivesContract.Presenter {

    private final EquipmentDataSource mEquipmentDataSource;
    private final EquipmentArchivesContract.View mView;
    @NonNull
    private CompositeSubscription subscription;

    EquipmentArchivesPresenter(EquipmentDataSource mRepository, EquipmentArchivesContract.View mView) {
        this.mEquipmentDataSource = mRepository;
        this.mView = mView;
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
}
