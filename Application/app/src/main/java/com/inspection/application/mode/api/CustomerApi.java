package com.inspection.application.mode.api;

import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.customer.DepartmentBean;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * 部门api
 * Created by pingan on 2017/12/2.
 */

public interface CustomerApi {

    /**
     * 获取检修人员
     *
     * @return 订阅
     */
    @GET("dept/deptuser/list.json")
    Observable<Bean<List<DepartmentBean>>> getEmployeeList();
}
