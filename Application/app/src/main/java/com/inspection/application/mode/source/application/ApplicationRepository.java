package com.inspection.application.mode.source.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.api.Api;
import com.inspection.application.mode.api.ApiCallBackObject;
import com.inspection.application.mode.api.ApplicationApi;
import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.version.NewVersion;
import com.inspection.application.mode.callback.IObjectCallBack;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscription;

/**
 * 系统mode repository
 * Created by pingan on 2017/11/30.
 */
@Singleton
public class ApplicationRepository implements ApplicationDataSource {

    private Context mContext;
    private SharedPreferences userSp;//用户信息sp
    private SharedPreferences dataSp;//一般数据保存sp

    @Inject
    public ApplicationRepository(@NonNull Context context) {
        userSp = context.getSharedPreferences(ConstantStr.USER_INFO, Context.MODE_PRIVATE);
        dataSp = context.getSharedPreferences(ConstantStr.USER_DATA, Context.MODE_PRIVATE);
        mContext = context;
    }

    @NonNull
    @Override
    public Subscription getNewVersion(final @NonNull NewVersionCallBack callBack) {
        Observable<Bean<NewVersion>> observable = Api.createRetrofit().create(ApplicationApi.class).newVersion();
        return new ApiCallBackObject<>(observable).execute(new IObjectCallBack<NewVersion>() {
            @Override
            public void onSuccess(@NonNull NewVersion result) {
                if (result.getVersion() > ConstantStr.VERSION_NO && result.getVersion() != userSp.getInt(ConstantStr.cancelVersion, -1)) {
                    callBack.newVersion(result);
                }
            }

            @Override
            public void onError(@Nullable String message) {

            }

            @Override
            public void noData() {

            }

            @Override
            public void onFinish() {

            }
        }).subscribe();
    }

    @NonNull
    @Override
    public Subscription cleanCache(@NonNull CleanCacheCallBack cacheCallBack) {
        return null;
    }

    @NonNull
    @Override
    public Subscription exitApp() {
        Observable<Bean<String>> observable = Api.createRetrofit().create(ApplicationApi.class).exitApp();
        return new ApiCallBackObject<>(observable).execute(null).subscribe();
    }

    @NonNull
    @Override
    public Subscription uploadUserPhoto(@NonNull File file, @NonNull IObjectCallBack<String> callBack) {
        return null;
    }

    @NonNull
    @Override
    public Subscription postQuestion(String title, String content, @NonNull IObjectCallBack<String> callBack) {
        return null;
    }
}
