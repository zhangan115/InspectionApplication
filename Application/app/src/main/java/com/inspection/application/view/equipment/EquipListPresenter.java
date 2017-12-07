package com.inspection.application.view.equipment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.mode.bean.equipment.RoomListBean;
import com.inspection.application.mode.callback.IListCallBack;
import com.inspection.application.mode.source.equipment.EquipmentDataSource;

import java.util.List;

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
        subscription.add(mRepository.getEquipInfo(new IListCallBack<RoomListBean>() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<RoomListBean> list) {
                mView.showRoomList(list);
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
}