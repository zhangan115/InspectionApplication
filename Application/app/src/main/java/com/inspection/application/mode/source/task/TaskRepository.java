package com.inspection.application.mode.source.task;

import android.content.Context;
import android.support.annotation.NonNull;

import com.inspection.application.common.ConstantInt;
import com.inspection.application.mode.api.Api;
import com.inspection.application.mode.api.ApiCallBackList;
import com.inspection.application.mode.api.TaskApi;
import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.secure.SecureBean;
import com.inspection.application.mode.bean.task.InspectionBean;
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

    @Inject
    TaskRepository(Context context) {

    }

    @NonNull
    @Override
    public Subscription getSecureInfo(long securityId, @NonNull IObjectCallBack<SecureBean> callBack) {
        return null;
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
}
