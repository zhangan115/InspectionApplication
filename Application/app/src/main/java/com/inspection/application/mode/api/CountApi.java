package com.inspection.application.mode.api;

import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.count.DeptType;
import com.inspection.application.mode.bean.count.FaultLevel;
import com.inspection.application.mode.bean.count.FaultNumber;
import com.inspection.application.mode.bean.count.MissCountBean;
import com.inspection.application.mode.bean.count.MonthCount;
import com.inspection.application.mode.bean.count.WorkCountBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 统计api
 * Created by pingan on 2017/12/2.
 */

public interface CountApi {

    //获取部门id
    @GET("dept/list/depttype.json")
    Observable<Bean<List<DeptType>>> getDeptType(@Query("types") String types);

    /**
     * 工作量统计
     *
     * @param deptId 部门id
     * @param time   时间
     * @return 订阅
     */
    @GET("statistics/user/workload.json")
    Observable<Bean<List<WorkCountBean>>> getWorkCount(@Query("deptId") long deptId, @Query("time") String time);

    /**
     * 漏检统计
     *
     * @param deptId 部门id
     * @param time   时间
     * @return 订阅
     */
    @GET("statistics/user/miss/data.json")
    Observable<Bean<List<MissCountBean>>> getMissCount(@Query("deptId") long deptId, @Query("time") String time);

    /**
     * 本月到岗统计
     *
     * @param time   时间
     * @param deptId 部门id
     * @return 订阅
     */
    @GET("task/unstation/month.json")
    Observable<Bean<List<MonthCount>>> getMonth(@Query("time") String time, @Query("deptId") long deptId);

    /**
     * 故障等级统计
     *
     * @param time 时间
     * @return 订阅
     */
    @GET("statistics/user/fault/level.json")
    Observable<Bean<List<FaultLevel>>> getFaultLevel(@Query("time") String time);

    /**
     * 确认故障统计
     *
     * @param time 时间
     * @return 订阅
     */
    @GET("statistics/user/fault/confirm.json")
    Observable<Bean<List<FaultNumber>>> getFaultCount(@Query("time") String time);


}
