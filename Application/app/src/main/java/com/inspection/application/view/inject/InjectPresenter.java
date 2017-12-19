package com.inspection.application.view.inject;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.inspection.application.mode.bean.inject.InjectEquipment;
import com.inspection.application.mode.bean.inject.InjectResultBean;
import com.inspection.application.mode.bean.inject.InjectRoomBean;
import com.inspection.application.mode.bean.inject.OilList;
import com.inspection.application.mode.callback.IListCallBack;
import com.inspection.application.mode.callback.IObjectCallBack;
import com.inspection.application.mode.source.inject.InjectDataSource;

import java.util.ArrayList;
import java.util.List;

import rx.subscriptions.CompositeSubscription;

/**
 * 注油管理
 * Created by Administrator on 2017/6/22.
 */
final class InjectPresenter implements InjectContract.Presenter {
    private InjectDataSource mInjectDataSource;
    private InjectContract.View mView;
    private CompositeSubscription mSubscriptions;
    private List<InjectEquipment> needInjectEqu;

    InjectPresenter(InjectDataSource repository, InjectContract.View view) {
        this.mInjectDataSource = repository;
        this.mView = view;
        mSubscriptions = new CompositeSubscription();
        needInjectEqu = new ArrayList<>();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void searchEquipment(List<InjectEquipment> injectEquipments, String searchText) {
        needInjectEqu.clear();
        for (int i = 0; i < injectEquipments.size(); i++) {
            InjectEquipment data = injectEquipments.get(i);
            if (!TextUtils.isEmpty(data.getEquipmentSn())) {
                if (data.getEquipmentSn().contains(searchText) || data.getEquipmentName().contains(searchText)) {
                    needInjectEqu.add(data);
                }
            } else {
                if (data.getEquipmentName().contains(searchText)) {
                    needInjectEqu.add(data);
                }
            }
        }
        if (TextUtils.isEmpty(searchText)) {
            mView.showNeedInjectEqu(injectEquipments);
        } else {
            mView.showNeedInjectEqu(needInjectEqu);
        }
    }

    @Override
    public void getRoomList() {
        mSubscriptions.add(mInjectDataSource.getInjectRoomList(new IListCallBack<InjectRoomBean>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<InjectRoomBean> list) {
                mView.showRoomList(list);
            }

            @Override
            public void onError(String message) {
                mView.getRoomListError();
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void noData() {

            }
        }));
    }

    @Override
    public void getRoomEquipmentList(long roomId) {
        mView.showLoading();
        mSubscriptions.add(mInjectDataSource.getInjectEquipmentList(roomId, new IListCallBack<InjectEquipment>() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<InjectEquipment> list) {
                mView.showRoomEquipment(list);
            }

            @Override
            public void onError(String message) {
                mView.getEquipmentError();
                mView.noData();
            }

            @Override
            public void onFinish() {
                mView.hideLoading();
            }

            @Override
            public void noData() {

            }
        }));
    }

    @Override
    public void injectionEquipment(long equipmentId, Integer cycle, Long oriId, final int position) {
        mSubscriptions.add(mInjectDataSource.injectEquipmentList(equipmentId, cycle, oriId, new IObjectCallBack<InjectResultBean>() {

            @Override
            public void onSuccess() {
                mView.injectSuccess(position);
            }

            @Override
            public void onData(@NonNull InjectResultBean injectResultBean) {

            }

            @Override
            public void onError(String message) {
                mView.injectFail(position);
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
    public void getOilList() {
        mSubscriptions.add(mInjectDataSource.getOilList(new IListCallBack<OilList>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<OilList> list) {
                mView.showOilList(list);
            }

            @Override
            public void onError(@Nullable String message) {
                mView.noOilList();
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void noData() {
                mView.noOilList();
            }
        }));
    }

    @Override
    public void getNeedInjectEqu(List<InjectEquipment> injectEquipments) {
        needInjectEqu.clear();
        for (int i = 0; i < injectEquipments.size(); i++) {
            InjectEquipment data = injectEquipments.get(i);
            if (data.getInjectionOil() == null) {
                needInjectEqu.add(data);
            } else {
                long time = System.currentTimeMillis() - data.getInjectionOil().getCreateTime();
                if (time > data.getCycle() * 24L * 60L * 60L * 1000L) {
                    needInjectEqu.add(data);
                }
            }
        }
        mView.showNeedInjectEqu(needInjectEqu);
    }
}
