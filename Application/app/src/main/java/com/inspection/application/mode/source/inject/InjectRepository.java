package com.inspection.application.mode.source.inject;

import android.support.annotation.NonNull;

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
            callBack.onData(injectRooms);
            return Observable.just(injectRooms).subscribe();
        }
        Observable<Bean<List<InjectRoomBean>>> observable = Api.createRetrofit().create(InjectApi.class).getInjectRoom(1);
        return new ApiCallBackList<InjectRoomBean>(observable) {
            @Override
            public void onSuccess() {
                callBack.onSuccess();
            }

            @Override
            public void onData(List<InjectRoomBean> list) {
                injectRooms = list;
                callBack.onData(list);
            }

            @Override
            public void onFail(@NonNull String message) {
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
        }.execute().subscribe();
    }

    @NonNull
    @Override
    public Subscription getInjectEquipmentList(long roomId, @NonNull final IListCallBack<InjectEquipment> callBack) {
        Observable<Bean<List<InjectEquipment>>> observable = Api.createRetrofit().create(InjectApi.class).getInjectEquipment(roomId);
        return new ApiCallBackList<InjectEquipment>(observable) {
            @Override
            public void onSuccess() {
                callBack.onSuccess();
            }

            @Override
            public void onData(List<InjectEquipment> data) {
                callBack.onData(data);
            }

            @Override
            public void onFail(@NonNull String message) {
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
        }.execute().subscribe();
    }

    @NonNull
    @Override
    public Subscription injectEquipmentList(long equipmentId, Integer cycle, @NonNull final IObjectCallBack<InjectResultBean> callBack) {
        Observable<Bean<InjectResultBean>> observable = Api.createRetrofit().create(InjectApi.class).injectEquipment(equipmentId, cycle);
        return new ApiCallBackObject<InjectResultBean>(observable) {
            @Override
            public void onData(@NonNull InjectResultBean d) {
                callBack.onData(d);
            }

            @Override
            public void onSuccess() {
                callBack.onSuccess();
            }

            @Override
            public void onFail(@NonNull String message) {
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
        }.execute().subscribe();
    }

    @NonNull
    @Override
    public Subscription injectEquipmentList(long equipmentId, int oilType, @NonNull final IObjectCallBack<InjectResultBean> callBack) {
        Observable<Bean<InjectResultBean>> observable = Api.createRetrofit().create(InjectApi.class).injectEquipment(equipmentId, oilType);
        return new ApiCallBackObject<InjectResultBean>(observable) {
            @Override
            public void onData(@NonNull InjectResultBean d) {
                callBack.onData(d);
            }

            @Override
            public void onSuccess() {
                callBack.onSuccess();
            }

            @Override
            public void onFail(@NonNull String message) {
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
        }.execute().subscribe();
    }
}
