package com.inspection.application.mode.source.inject;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.mode.api.Api;
import com.inspection.application.mode.api.ApiCallBackList;
import com.inspection.application.mode.api.ApiCallBackObject;
import com.inspection.application.mode.api.InjectApi;
import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.inject.InjectEquipment;
import com.inspection.application.mode.bean.inject.InjectResultBean;
import com.inspection.application.mode.bean.inject.InjectRoomBean;
import com.inspection.application.mode.callback.IListCallBack;
import com.inspection.application.mode.callback.IObjectCallBack;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * 注油管理 repository
 * Created by pingan on 2017/12/3.
 */
@Singleton
public class InjectRepository implements InjectDataSource {

    @Inject
    public InjectRepository() {
    }

    //缓存
    private List<InjectRoomBean> injectRooms = null;

    @NonNull
    @Override
    public Subscription getInjectRoomList(@NonNull final IListCallBack<InjectRoomBean> callBack) {
        if (injectRooms != null) {
            callBack.onFinish();
            callBack.onSuccess(injectRooms);
            return Observable.just(injectRooms).subscribe();
        }
        Observable<Bean<List<InjectRoomBean>>> observable = Api.createRetrofit().create(InjectApi.class).getInjectRoom(1);
        return new ApiCallBackList<>(observable).execute(new IListCallBack<InjectRoomBean>() {
            @Override
            public void onSuccess(@NonNull List<InjectRoomBean> list) {
                injectRooms = list;
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

    @NonNull
    @Override
    public Subscription getInjectEquipmentList(long roomId, @NonNull IListCallBack<InjectEquipment> callBack) {
        Observable<Bean<List<InjectEquipment>>> observable = Api.createRetrofit().create(InjectApi.class).getInjectEquipment(roomId);
        return new ApiCallBackList<>(observable).execute(callBack).subscribe();
    }

    @NonNull
    @Override
    public Subscription injectEquipmentList(long equipmentId, Integer cycle, @NonNull IObjectCallBack<InjectResultBean> callBack) {
        Observable<Bean<InjectResultBean>> observable = Api.createRetrofit().create(InjectApi.class).injectEquipment(equipmentId, cycle);
        return new ApiCallBackObject<>(observable).execute(callBack).subscribe();
    }

    @NonNull
    @Override
    public Subscription injectEquipmentList(long equipmentId, int oilType, @NonNull IObjectCallBack<InjectResultBean> callBack) {
        Observable<Bean<InjectResultBean>> observable = Api.createRetrofit().create(InjectApi.class).injectEquipment(equipmentId, oilType);
        return new ApiCallBackObject<>(observable).execute(callBack).subscribe();
    }
}
