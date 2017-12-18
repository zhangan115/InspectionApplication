package com.inspection.application.mode.api;

import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.equipment.CheckValue;
import com.inspection.application.mode.bean.equipment.InspectionData;
import com.inspection.application.mode.bean.equipment.RoomListBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 设备api
 * Created by pingan on 2017/12/2.
 */

public interface EquipmentApi {

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("room/equipment/list.json")
    Observable<Bean<List<RoomListBean>>> getRoomDta();

    //根据设备id获取检修数据
    @GET("inspectionData/item/list.json")
    Observable<Bean<InspectionData>> getCheckData(@Query("equipmentId") long equipId);

    @GET("inspectionData/item/value/list.json")
    Observable<Bean<CheckValue>> getCheckValue(@Query("equipmentId") long equipId, @Query("inspectionId") long inspectionId);
}
