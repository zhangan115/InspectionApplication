package com.inspection.application.mode.api;

import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.secure.SecureBean;
import com.inspection.application.mode.bean.task.InspectionBean;
import com.inspection.application.mode.bean.task.InspectionDetailBean;
import com.inspection.application.mode.bean.task.data.CheckBean;
import com.inspection.application.mode.bean.task.data.InspectionDataBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * task api
 * Created by pingan on 2017/12/2.
 */

public interface TaskApi {

    /**
     * 获取用户下巡检任务列表
     *
     * @param type     客户版
     * @param time     时间
     * @param pageSize 分页
     * @return 请求对象
     */
    @GET("task/list.json")
    Observable<Bean<List<InspectionBean>>> getInspectionList(@Query("agentType") int type, @Query("time") String time, @Query("count") int pageSize);

    @GET("task/list.json")
    Observable<Bean<List<InspectionBean>>> getInspectionList(@Query("agentType") int type, @Query("count") int pageSize, @Query("lastId") Long lastId);

    /**
     * 领取任务
     *
     * @param taskId        任务id
     * @param operationType 类型
     * @return 请求对象
     */
    @GET("task/edit/state.json")
    Observable<Bean<String>> operationTask(@Query("taskId") long taskId, @Query("operation") int operationType);

    /**
     * 获取安全包数据
     *
     * @param securityId 安全包id
     * @return 请求对象
     */
    @GET("bag/security/get.json")
    Observable<Bean<SecureBean>> getSecureInfo(@Query("securityId") long securityId);

    /**
     * 获取巡检详情列表
     *
     * @param taskId 巡检列表
     * @return 请求对象
     */
    @GET("task/get/task.json")
    Observable<Bean<InspectionDetailBean>> getInspectionDetailList(@Query("taskId") long taskId);

    /**
     * 获取任务的巡检数据
     *
     * @param taskId id
     * @return 订阅
     */
    @GET("task/get/task/data.json")
    Observable<Bean<InspectionDataBean>> getInspectionData(@Query("taskId") Long taskId);

    //巡检详情头部
    @GET("task/taskinfo.json")
    Observable<Bean<CheckBean>> getCheckInfo(@Query("taskId") long taskId);

    //开始 结束
    @GET("task/edit/roomstate.json")
    Observable<Bean<String>> uploadStartOrEnd(@Query("taskId") long taskId, @Query("taskRoomId") long taskRoomId, @Query("operation") int operation);
}
