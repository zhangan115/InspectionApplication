package com.inspection.application.mode.api;

import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.equipment.RoomListBean;

import java.util.List;

import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 设备api
 * Created by pingan on 2017/12/2.
 */

public interface EquipmentApi {

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("room/equipment/list.json")
    Observable<Bean<List<RoomListBean>>> getRoomDta();
}
