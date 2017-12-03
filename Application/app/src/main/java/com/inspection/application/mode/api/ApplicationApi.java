package com.inspection.application.mode.api;

import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.version.NewVersion;
import com.inspection.application.mode.bean.option.OptionBean;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 系统类api
 * Created by pingan on 2017/12/2.
 */

public interface ApplicationApi {

    /**
     * 请求字典
     *
     * @return 请求对象
     */
    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("base/option/item.json")
    Observable<Bean<List<OptionBean>>> getOptionInfo();

    /**
     * 检测新版本
     *
     * @return 请求对象
     */
    @GET("version/latestCustomerApp.json")
    Observable<Bean<NewVersion>> newVersion();

    /**
     * 退出app
     *
     * @return 请求对象
     */
    @GET("user/logout.json")
    Observable<Bean<String>> exitApp();

    /**
     * 提交意见反馈
     *
     * @param info 信息
     * @return 请求对象
     */
    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("feedback/add.json")
    Observable<Bean<String>> postSuggest(@Body() String info);
}
