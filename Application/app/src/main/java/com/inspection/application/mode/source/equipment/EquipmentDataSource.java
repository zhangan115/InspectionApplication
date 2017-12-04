package com.inspection.application.mode.source.equipment;

import android.support.annotation.NonNull;

import com.inspection.application.mode.bean.equipment.RoomListBean;
import com.inspection.application.mode.callback.IListCallBack;

import rx.Subscription;

/**
 * 设备 data source
 * Created by pingan on 2017/12/3.
 */

public interface EquipmentDataSource {

    @NonNull
    Subscription getEquipInfo(@NonNull IListCallBack<RoomListBean> callBack);
}
