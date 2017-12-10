package com.inspection.application.mode.api;

import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.task.InspectionBean;

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
}
