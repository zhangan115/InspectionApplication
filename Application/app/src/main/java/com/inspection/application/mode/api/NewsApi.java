package com.inspection.application.mode.api;

import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.news.MessageContent;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 消息api
 * Created by pingan on 2017/12/2.
 */

public interface NewsApi {

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("message/log/list.json")
    Observable<Bean<List<MessageContent>>> getNewsContent(@Body String body);
}
