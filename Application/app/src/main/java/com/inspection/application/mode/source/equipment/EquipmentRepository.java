package com.inspection.application.mode.source.equipment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.app.App;
import com.inspection.application.mode.api.Api;
import com.inspection.application.mode.api.ApiCallBackList;
import com.inspection.application.mode.api.EquipmentApi;
import com.inspection.application.mode.bean.equipment.RoomListBean;
import com.inspection.application.mode.callback.IListCallBack;

import java.util.List;
import java.util.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Subscription;

/**
 * 设备 repository
 * Created by pingan on 2017/12/3.
 */
@Singleton
public class EquipmentRepository implements EquipmentDataSource {

    @Inject
    public EquipmentRepository() {

    }

    private List<RoomListBean> roomListBeans = null;

    @NonNull
    @Override
    public Subscription getEquipInfo(@NonNull final IListCallBack<RoomListBean> callBack) {
        if (roomListBeans != null) {
            callBack.onFinish();
            callBack.onSuccess(roomListBeans);
            return rx.Observable.just(roomListBeans).subscribe();
        }
        return new ApiCallBackList<>(Api.createRetrofit().create(EquipmentApi.class).getRoomDta()).execute(new IListCallBack<RoomListBean>() {
            @Override
            public void onSuccess(@NonNull List<RoomListBean> list) {
                roomListBeans = list;
                callBack.onSuccess(list);
            }

            @Override
            public void onError(@Nullable String message) {
                callBack.onError(message);
            }

            @Override
            public void onFinish() {
                callBack.onFinish();
            }

            @Override
            public void noData() {
                callBack.noData();
            }
        }).subscribe();
    }
}
