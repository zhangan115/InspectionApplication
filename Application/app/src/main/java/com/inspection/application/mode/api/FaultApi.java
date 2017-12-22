package com.inspection.application.mode.api;

import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.fault.DefaultFlowBean;
import com.inspection.application.mode.bean.fault.FaultDetail;
import com.inspection.application.mode.bean.fault.FaultList;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 缺陷api
 * Created by pingan on 2017/12/2.
 */

public interface FaultApi {

    @GET("defaultFlow/list.json")
    Observable<Bean<List<DefaultFlowBean>>> getDefaultFlow(@Query("byCurrentUser") int byCurrentUser);

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("fault/add.json")
    Observable<Bean<String>> uploadFaultData(@Body String body);

    /**
     * <br>
     * lastId 上一次查询的最后一个ID，app分页用
     * count 每页多少条，app和bs使用
     * byCurrentUser 查询当前用户创建的故障
     * roomId 配电室id
     * equipmentId 设备id
     * taskId 任务id
     * faultState 故障状态
     * faultType 故障等级
     * equipmentType 设备类型
     * startTime 开始时间
     * endTime 结束时间
     * lessThanFaultState 故障状态
     * </br>
     *
     * @param info 参数
     * @return 订阅
     */
    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("fault/list.json")
    Observable<Bean<List<FaultList>>> getFaultList(@Body() String info);

    /**
     * 故障详情
     *
     * @param faultId 故障id
     * @return 订阅
     */
    @GET("fault/get.json")
    Observable<Bean<FaultDetail>> getFaultDetail(@Query("faultId") long faultId);



    @GET("fault/close.json")
    Observable<Bean<String>> closeFault(@Query("faultId") long faultId, @Query("flowRemark") String flowRemark);


    @GET("fault/confirm.json")
    Observable<Bean<String>> sureFault(@Query("faultId") long faultId, @Query("flowRemark") String flowRemark);

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("fault/flow.json")
    Observable<Bean<String>> pointFault(@Body() String info);


}
