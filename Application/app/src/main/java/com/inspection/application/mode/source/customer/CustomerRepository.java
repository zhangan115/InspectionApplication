package com.inspection.application.mode.source.customer;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.mode.api.Api;
import com.inspection.application.mode.api.ApiCallBackList;
import com.inspection.application.mode.api.CustomerApi;
import com.inspection.application.mode.bean.customer.DepartmentBean;
import com.inspection.application.mode.callback.IListCallBack;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * 联系人
 * Created by pingan on 2017/12/3.
 */
@Singleton
public class CustomerRepository implements CustomerDataSource {

    @Inject
    public CustomerRepository() {

    }

    private List<DepartmentBean> departmentBeans;

    @NonNull
    @Override
    public Subscription getEmployeeList(@NonNull final IListCallBack<DepartmentBean> callBack) {
        if (departmentBeans != null) {
            return Observable.just(departmentBeans).subscribe(new Action1<List<DepartmentBean>>() {
                @Override
                public void call(List<DepartmentBean> departmentBeans) {
                    callBack.onFinish();
                    callBack.onSuccess(departmentBeans);
                }
            });
        }
        return new ApiCallBackList<>(Api.createRetrofit().create(CustomerApi.class).getEmployeeList()).execute(new IListCallBack<DepartmentBean>() {
            @Override
            public void onSuccess(@NonNull List<DepartmentBean> list) {
                callBack.onSuccess(list);
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
                callBack.noData();
            }
        }).subscribe();
    }
}
