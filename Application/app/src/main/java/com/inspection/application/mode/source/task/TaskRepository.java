package com.inspection.application.mode.source.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.inspection.application.common.ConstantInt;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.api.Api;
import com.inspection.application.mode.api.ApiCallBackList;
import com.inspection.application.mode.api.ApiCallBackObject;
import com.inspection.application.mode.api.FaultApi;
import com.inspection.application.mode.api.TaskApi;
import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.secure.SecureBean;
import com.inspection.application.mode.bean.task.InspectionBean;
import com.inspection.application.mode.bean.task.InspectionDetailBean;
import com.inspection.application.mode.callback.IListCallBack;
import com.inspection.application.mode.callback.IObjectCallBack;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscription;

/**
 * task repository
 * Created by pingan on 2017/12/9.
 */
@Singleton
public class TaskRepository implements TaskDataSource {

    private SharedPreferences dataSp;

    @Inject
    TaskRepository(Context context) {
        dataSp = context.getSharedPreferences(ConstantStr.SECURE_INFO, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public Subscription getSecureInfo(long securityId, @NonNull final IObjectCallBack<SecureBean> callBack) {
        Observable<Bean<SecureBean>> observable = Api.createRetrofit().create(TaskApi.class)
                .getSecureInfo(securityId);
        return new ApiCallBackObject<SecureBean>(observable) {
            @Override
            public void onData(@NonNull SecureBean data) {
                callBack.onData(data);
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
    public Subscription getTaskList(String date, @NonNull final IListCallBack<InspectionBean> callBack) {
        Observable<Bean<List<InspectionBean>>> observable = Api.createRetrofit().create(TaskApi.class)
                .getInspectionList(ConstantInt.INSPECTION_TYPE, date, ConstantInt.MAX_PAGE_SIZE);
        return new ApiCallBackList<InspectionBean>(observable) {
            @Override
            public void onSuccess() {
                callBack.onSuccess();
            }

            @Override
            public void onData(List<InspectionBean> data) {
                callBack.onData(data);
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
    public Subscription getTask(final int position, final long taskId, @NonNull final GetTaskCallBack callBack) {
        return new ApiCallBackObject<String>(Api.createRetrofit()
                .create(TaskApi.class).operationTask(taskId, ConstantInt.OPERATION_STATE_1)) {
            @Override
            public void onData(@NonNull String data) {

            }

            @Override
            public void onSuccess() {
                callBack.onSuccess(position);
            }

            @Override
            public void onFail(@NonNull String message) {
                callBack.onFail(message);
            }

            @Override
            public void onFinish() {
                callBack.onFinish();
            }

            @Override
            public void noData() {

            }
        }.execute().subscribe();
    }

    @NonNull
    @Override
    public Subscription getTaskInfo(long taskId, final IObjectCallBack<InspectionDetailBean> callBack) {

        return new ApiCallBackObject<InspectionDetailBean>(Api.createRetrofit().create(TaskApi.class)
                .getInspectionDetailList(taskId)) {
            @Override
            public void onData(@NonNull InspectionDetailBean data) {
                callBack.onData(data);
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

    @Override
    public void checkSecure(int position, long taskId, @NonNull CheckSecureInfoCallBack callBack) {
        if (!dataSp.getBoolean(String.valueOf(taskId), false)) {
            callBack.onShowSecure(position);
        } else {
            callBack.onShowTask(position);
        }
    }
}
