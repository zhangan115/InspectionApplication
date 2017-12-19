package com.inspection.application.mode.source.inject;

import android.support.annotation.NonNull;

import com.inspection.application.mode.bean.inject.InjectEquipment;
import com.inspection.application.mode.bean.inject.InjectResultBean;
import com.inspection.application.mode.bean.inject.InjectRoomBean;
import com.inspection.application.mode.bean.inject.OilList;
import com.inspection.application.mode.bean.version.NewVersion;
import com.inspection.application.mode.callback.IListCallBack;
import com.inspection.application.mode.callback.IObjectCallBack;

import rx.Subscription;

/**
 * 注油管理 data source</br>
 * Created by pingan on 2017/11/30.
 */

public interface InjectDataSource {

    /**
     * 获取注油区域的配电室
     *
     * @param callBack 回调
     * @return 订阅
     */
    @NonNull
    Subscription getInjectRoomList(@NonNull IListCallBack<InjectRoomBean> callBack);

    /**
     * 获取配电室的设备列表
     *
     * @param roomId   区域id
     * @param callBack 回调
     * @return 订阅
     */
    @NonNull
    Subscription getInjectEquipmentList(long roomId, @NonNull IListCallBack<InjectEquipment> callBack);

    /**
     * 对设备进行注油
     *
     * @param equipmentId 设备id
     * @param cycle       注油周期
     * @param oriId       油料Id
     * @param callBack    回调
     * @return 订阅
     */
    @NonNull
    Subscription injectEquipmentList(long equipmentId, Integer cycle, Long oriId, @NonNull IObjectCallBack<InjectResultBean> callBack);

    /**
     * 对设备进行注油
     *
     * @param equipmentId 设备id
     * @param oilType     注油那种油
     * @param callBack    回调
     * @return 订阅
     */
    @NonNull
    Subscription injectEquipmentList(long equipmentId, int oilType, @NonNull IObjectCallBack<InjectResultBean> callBack);

    @NonNull
    Subscription getOilList(@NonNull IListCallBack<OilList> callBack);
}
