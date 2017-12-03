package com.inspection.application.mode.source.inject;

import android.support.annotation.NonNull;

import com.inspection.application.mode.bean.inject.InjectEquipment;
import com.inspection.application.mode.bean.inject.InjectResultBean;
import com.inspection.application.mode.bean.inject.InjectRoomBean;
import com.inspection.application.mode.callback.IListCallBack;
import com.inspection.application.mode.callback.IObjectCallBack;

import javax.inject.Inject;
import javax.inject.Singleton;

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


    @NonNull
    @Override
    public Subscription getInjectRoomList(@NonNull IListCallBack<InjectRoomBean> callBack) {
        return null;
    }

    @NonNull
    @Override
    public Subscription getInjectEquipmentList(long roomId, @NonNull IListCallBack<InjectEquipment> callBack) {
        return null;
    }

    @NonNull
    @Override
    public Subscription injectEquipmentList(long equipmentId, Integer cycle, @NonNull IObjectCallBack<InjectResultBean> callBack) {
        return null;
    }

    @NonNull
    @Override
    public Subscription injectEquipmentList(long equipmentId, int oilType, @NonNull IObjectCallBack<InjectResultBean> callBack) {
        return null;
    }
}
