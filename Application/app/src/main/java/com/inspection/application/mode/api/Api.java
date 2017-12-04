package com.inspection.application.mode.api;

import android.text.TextUtils;

import com.inspection.application.BuildConfig;
import com.inspection.application.app.App;
import com.inspection.application.common.ConstantStr;
import com.library.utils.SPHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.protobuf.ProtoConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * api
 * Created by zhangan on 2017-06-21.
 */

public final class Api {

    public static final String HOST = BuildConfig.HOST;
    private static Retrofit mRetrofit;
    private static final int CONNECT_TIME = 5;
    private static final int READ_TIME = 30;
    private static final int WRITE_TIME = 30;
    private static Cookie cookie;

    public static Retrofit createRetrofit() {
        if (mRetrofit == null) {
            initRetrofit();
        }
        return mRetrofit;
    }

    private static void initRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIME, TimeUnit.SECONDS)
                .readTimeout(READ_TIME, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        builder.cookieJar(new CookieJar() {

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                if (cookies != null && cookies.size() > 0) {
                    cookie = cookies.get(0);
                    SPHelper.write(App.getInstance().getApplicationContext(), ConstantStr.USER_INFO, ConstantStr.COOKIE_DOMAIN, cookie.domain());
                    SPHelper.write(App.getInstance().getApplicationContext(), ConstantStr.USER_INFO, ConstantStr.COOKIE_NAME, cookie.name());
                    SPHelper.write(App.getInstance().getApplicationContext(), ConstantStr.USER_INFO, ConstantStr.COOKIE_VALUE, cookie.value());
                }
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = new ArrayList<>();
                if (cookie == null) {
                    String doMin = SPHelper.readString(App.getInstance().getApplicationContext(), ConstantStr.USER_INFO, ConstantStr.COOKIE_DOMAIN);
                    String name = SPHelper.readString(App.getInstance().getApplicationContext(), ConstantStr.USER_INFO, ConstantStr.COOKIE_NAME);
                    String value = SPHelper.readString(App.getInstance().getApplicationContext(), ConstantStr.USER_INFO, ConstantStr.COOKIE_VALUE);
                    if (!TextUtils.isEmpty(doMin) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(value)) {
                        cookie = new Cookie.Builder().domain(doMin).name(name).value(value).build();
                    }
                }
                if (cookie != null) {
                    cookies.add(new Cookie.Builder().domain(cookie.domain()).name(cookie.name()).value(cookie.value()).build());
                }
                return cookies;
            }
        });
        String host = Api.HOST;
        if (!host.endsWith("/")) {
            host = host + "/";
        }
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(host)
                .addConverterFactory(ProtoConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }


    public static void clean() {
        cookie = null;
        mRetrofit = null;
        SPHelper.remove(App.getInstance().getApplicationContext(), ConstantStr.USER_INFO, ConstantStr.COOKIE_DOMAIN);
        SPHelper.remove(App.getInstance().getApplicationContext(), ConstantStr.USER_INFO, ConstantStr.COOKIE_NAME);
        SPHelper.remove(App.getInstance().getApplicationContext(), ConstantStr.USER_INFO, ConstantStr.COOKIE_VALUE);
    }

}
