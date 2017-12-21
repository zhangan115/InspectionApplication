package com.inspection.application.mode.source.count;

import android.support.annotation.NonNull;

import com.inspection.application.mode.api.Api;
import com.inspection.application.mode.api.ApiCallBackList;
import com.inspection.application.mode.api.CountApi;
import com.inspection.application.mode.bean.count.DeptType;
import com.inspection.application.mode.callback.IListCallBack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscription;

/**
 * 统计
 * Created by pingan on 2017/12/21.
 */
@Singleton
public class CountRepository implements CountDataSource {

    private Map<String, List<DeptType>> deptCache;

    @Inject
    public CountRepository() {
        deptCache = new HashMap<>();
    }

    @NonNull
    @Override
    public Subscription getDeptList(final String info, final IListCallBack<DeptType> callBack) {
        List<DeptType> deptTypes = deptCache.get(info);
        if (deptTypes != null && deptTypes.size() > 0) {
            callBack.onFinish();
            callBack.onData(deptTypes);
            return Observable.just(deptTypes).subscribe();
        }
        return new ApiCallBackList<DeptType>(Api.createRetrofit().create(CountApi.class).getDeptType(info)) {
            @Override
            public void onSuccess() {
                callBack.onSuccess();
            }

            @Override
            public void onData(List<DeptType> data) {
                deptCache.put(info, data);
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
