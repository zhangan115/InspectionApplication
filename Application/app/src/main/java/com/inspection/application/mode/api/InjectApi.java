package com.inspection.application.mode.api;

import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.inject.InjectEquipment;
import com.inspection.application.mode.bean.inject.InjectResultBean;
import com.inspection.application.mode.bean.inject.InjectRoomBean;
import com.inspection.application.mode.bean.inject.OilList;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 注油管理
 * Created by pingan on 2017/12/2.
 */

public interface InjectApi {

    @GET("room/user/list.json")
    Observable<Bean<List<InjectRoomBean>>> getInjectRoom(@Query("roomType") int roomType);

    @GET("room/get/equipments.json")
    Observable<Bean<List<InjectEquipment>>> getInjectEquipment(@Query("roomId") long roomId);

    @GET("oil/listAll.json")
    Observable<Bean<List<OilList>>> getOilList();

    @GET("oil/injection.json")
    Observable<Bean<InjectResultBean>> injectEquipment(@Query("equipmentId") long equipmentId, @Query("cycle") int cycle, @Query("oilId") Long oriId);

}
