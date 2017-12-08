package com.inspection.application.mode.api;

import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.fault.DefaultFlowBean;

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
}
