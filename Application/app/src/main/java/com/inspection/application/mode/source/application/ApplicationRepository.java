package com.inspection.application.mode.source.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.app.App;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.api.Api;
import com.inspection.application.mode.api.ApiCallBackList;
import com.inspection.application.mode.api.ApiCallBackObject;
import com.inspection.application.mode.api.ApplicationApi;
import com.inspection.application.mode.api.CustomerApi;
import com.inspection.application.mode.api.UploadApi;
import com.inspection.application.mode.api.UserApi;
import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.version.NewVersion;
import com.inspection.application.mode.callback.IListCallBack;
import com.inspection.application.mode.callback.IObjectCallBack;
import com.inspection.application.mode.source.FilePartManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
        return new ApiCallBackObject<NewVersion>(observable) {
            @Override
            public void onData(@NonNull NewVersion d) {

            }
        }.execute(new IObjectCallBack<NewVersion>() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull NewVersion result) {
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
        return new ApiCallBackObject<String>(observable) {
            @Override
            public void onData(@NonNull String d) {

            }
        }.execute(null).subscribe();
    }

    private String mUserPhotoUrl;

    @NonNull
    @Override
    public Subscription uploadUserPhoto(@NonNull File file, @NonNull final IObjectCallBack<String> callBack) {
        Observable<Bean<List<String>>> observable = Api.createRetrofit().create(UploadApi.class)
                .postFile(FilePartManager.getPostFileParts("user", "image", file));
        return new ApiCallBackList<String>(observable) {
            @Override
            public void onData(List<String> strings) {
                String userPhotoUrl = strings.get(0);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("portraitUrl", userPhotoUrl);
                    mUserPhotoUrl = userPhotoUrl;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Observable<Bean<String>> observable1 = Api.createRetrofit().create(UserApi.class).updateUserInfo(jsonObject.toString());
                new ApiCallBackObject<String>(observable1) {
                    @Override
                    public void onData(@NonNull String s) {

                    }
                }.execute(new IObjectCallBack<String>() {

                    @Override
                    public void onSuccess() {
                        callBack.onData(mUserPhotoUrl);
                    }

                    @Override
                    public void onData(@NonNull String s) {

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
        }.execute(new IListCallBack<String>() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<String> strings) {

            }

            @Override
            public void onError(@Nullable String message) {
                callBack.onError(message);
            }

            @Override
            public void onFinish() {
                callBack.onFinish();
            }

            @Override
            public void noData() {

            }
        }).subscribe();
    }


    @NonNull
    @Override
    public Subscription postQuestion(String title, String content, @NonNull IObjectCallBack<String> callBack) {
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

            }
        }.execute(callBack).subscribe();
    }
}
