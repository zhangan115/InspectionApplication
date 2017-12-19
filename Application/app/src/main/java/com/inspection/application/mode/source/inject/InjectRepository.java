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
import com.inspection.application.mode.bean.inject.OilList;
import com.inspection.application.mode.callback.IListCallBack;
import com.inspection.application.mode.callback.IObjectCallBack;
import com.library.utils.DataUtil;

import java.util.Collections;
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
            public void onData(List<InjectEquipment> injectEquipments) {
                long sysTime = System.currentTimeMillis();
                for (int i = 0; i < injectEquipments.size(); i++) {
                    if (injectEquipments.get(i).getInjectionOil() != null) {
                        try {
                            long aa = DataUtil.getDistanceDays(DataUtil.timeFormat(injectEquipments.get(i).getInjectionOil().getCreateTime(), null), DataUtil.timeFormat(System.currentTimeMillis(), null));
                            injectEquipments.get(i).setTime(aa - injectEquipments.get(i).getCycle());
                        } catch (Exception e) {
                            e.printStackTrace();
                            injectEquipments.get(i).setTime(sysTime);
                        }

                    } else {
                        injectEquipments.get(i).setTime(sysTime);
                    }
                }
                Collections.sort(injectEquipments);
                callBack.onData(injectEquipments);
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
    public Subscription injectEquipmentList(long equipmentId, Integer cycle, Long oriId, @NonNull final IObjectCallBack<InjectResultBean> callBack) {
        Observable<Bean<InjectResultBean>> observable = Api.createRetrofit().create(InjectApi.class).injectEquipment(equipmentId, cycle, oriId);
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

    private List<OilList> oilListCache;

    @NonNull
    @Override
    public Subscription injectEquipmentList(long equipmentId, int oilType, @NonNull final IObjectCallBack<InjectResultBean> callBack) {
        Observable<Bean<InjectResultBean>> observable = Api.createRetrofit().create(InjectApi.class).injectEquipment(equipmentId, oilType, null);
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
    public Subscription getOilList(@NonNull final IListCallBack<OilList> callBack) {
        if (oilListCache != null && oilListCache.size() > 0) {
            callBack.onFinish();
            callBack.onData(oilListCache);
            return Observable.just(oilListCache).subscribe();
        }
        return new ApiCallBackList<OilList>(Api.createRetrofit().create(InjectApi.class).getOilList()) {
            @Override
            public void onSuccess() {
                callBack.onSuccess();
            }

            @Override
            public void onData(List<OilList> data) {
                oilListCache = data;
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
}
