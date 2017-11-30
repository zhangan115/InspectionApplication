package com.inspection.application.mode.api;

import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.User;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 *
 * Created by pingan on 2017/11/30.
 */

public interface LoginApi {

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("user/login.json")
    Observable<Bean<User>> userLogin(@Body() String info);
}
