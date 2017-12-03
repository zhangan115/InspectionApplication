package com.inspection.application.mode.source.customer;

import android.support.annotation.NonNull;

import com.inspection.application.mode.bean.customer.DepartmentBean;
import com.inspection.application.mode.callback.IListCallBack;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Subscription;

/**
 * 联系人
 * Created by pingan on 2017/12/3.
 */
@Singleton
public class CustomerRepository implements CustomerDataSource {

    @Inject
    public CustomerRepository() {

    }

    @NonNull
    @Override
    public Subscription getEmployeeList(@NonNull IListCallBack<DepartmentBean> callBack) {
        return null;
    }
}
