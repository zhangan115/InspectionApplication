package com.inspection.application.mode.source.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.inspection.application.app.App;
import com.inspection.application.common.ConstantInt;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.api.Api;
import com.inspection.application.mode.api.ApiCallBackList;
import com.inspection.application.mode.api.ApiCallBackObject;
import com.inspection.application.mode.api.ApplicationApi;
import com.inspection.application.mode.api.UploadApi;
import com.inspection.application.mode.api.UserApi;
import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.customer.StandBean;
import com.inspection.application.mode.bean.version.NewVersion;
import com.inspection.application.mode.callback.IObjectCallBack;
import com.inspection.application.mode.source.FilePartManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;

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
    public Subscription getStandData(final IObjectCallBack<StandBean> callBack) {
        return new ApiCallBackObject<StandBean>(Api.createRetrofit().create(ApplicationApi.class).getStandInfo(ConstantInt.MAX_PAGE_SIZE)) {
            @Override
            public void onData(@NonNull StandBean data) {
                if (data.getList() == null || data.getList().size() == 0) {
                    callBack.noData();
                } else {
                    callBack.onData(data);
                }
            }

            @Override
            public void onSuccess() {
                callBack.onSuccess();
            }

            @Override
            public void onFail(@NonNull String message) {
                callBack.onError(message);
            }

            @Override
            public void onFinish() {
                callBack.onFinish();
            }

            @Override
            public void noData() {
                callBack.noData();
            }
        }.execute().subscribe();
    }

    @NonNull
    @Override
    public Subscription getNewVersion(final @NonNull NewVersionCallBack callBack) {
        Observable<Bean<NewVersion>> observable = Api.createRetrofit().create(ApplicationApi.class).newVersion();
        return new ApiCallBackObject<NewVersion>(observable) {

            @Override
            public void onData(@NonNull NewVersion result) {
                if (result.getVersion() > ConstantStr.VERSION_NO && result.getVersion() != userSp.getInt(ConstantStr.cancelVersion, -1)) {
                    callBack.newVersion(result);
                } else {
                    callBack.noNewVersion();
                }
            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFail(@NonNull String message) {
                callBack.noNewVersion();
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void noData() {
                callBack.noNewVersion();
            }
        }.execute().subscribe();
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
        return new ApiCallBackObject<String>(observable) {
            @Override
            public void onData(@NonNull String d) {

            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFail(@NonNull String message) {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void noData() {

            }
        }.execute().subscribe();
    }

    private String mUserPhotoUrl;

    @NonNull
    @Override
    public Subscription uploadUserPhoto(@NonNull File file, @NonNull final IObjectCallBack<String> callBack) {
        Observable<Bean<List<String>>> observable = Api.createRetrofit().create(UploadApi.class)
                .postFile(FilePartManager.getPostFileParts("user", "image", file));
        return new ApiCallBackList<String>(observable) {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(List<String> strings) {

            }

            @Override
            public void onFail(@NonNull String message) {
                callBack.onError(message);
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void noData() {

            }
        }.execute().flatMap(new Func1<List<String>, Observable<String>>() {
            @Override
            public Observable<String> call(List<String> strings) {
                String userPhotoUrl = strings.get(0);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("portraitUrl", userPhotoUrl);
                    mUserPhotoUrl = userPhotoUrl;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Observable<Bean<String>> observable1 = Api.createRetrofit().create(UserApi.class).updateUserInfo(jsonObject.toString());
                return new ApiCallBackObject<String>(observable1) {

                    @Override
                    public void onData(@NonNull String s) {

                    }

                    @Override
                    public void onSuccess() {
                        App.getInstance().getCurrentUser().setPortraitUrl(mUserPhotoUrl);
                        callBack.onSuccess();
                    }

                    @Override
                    public void onFail(@NonNull String message) {

                    }

                    @Override
                    public void onFinish() {
                        callBack.onFinish();
                    }

                    @Override
                    public void noData() {

                    }
                }.execute();
            }
        }).subscribe();
    }


    @NonNull
    @Override
    public Subscription postQuestion(String title, String content, @NonNull final IObjectCallBack<String> callBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("feedbackTitle", title);
            jsonObject.put("feedbackText", content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Observable<Bean<String>> observable = Api.createRetrofit().create(ApplicationApi.class).postSuggest(jsonObject.toString());
        return new ApiCallBackObject<String>(observable) {
            @Override
            public void onData(@NonNull String d) {
                callBack.onData(d);
            }

            @Override
            public void onSuccess() {
                callBack.onSuccess();
            }

            @Override
            public void onFail(@NonNull String message) {
                callBack.onError(message);
            }

            @Override
            public void onFinish() {
                callBack.onFinish();
            }

            @Override
            public void noData() {
                callBack.noData();
            }
        }.execute().subscribe();
    }
}
