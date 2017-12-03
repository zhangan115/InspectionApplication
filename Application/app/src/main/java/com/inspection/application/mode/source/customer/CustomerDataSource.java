package com.inspection.application.mode.source.customer;

import android.support.annotation.NonNull;

import com.inspection.application.mode.bean.customer.DepartmentBean;
import com.inspection.application.mode.callback.IListCallBack;

import rx.Subscription;

/**
 * 公司部门联系人
 * Created by pingan on 2017/12/3.
 */

public interface CustomerDataSource {
    /**
     * 获取联系人
     *
     * @param callBack 回调
     * @return 订阅
     */
    @NonNull
    Subscription getEmployeeList(@NonNull IListCallBack<DepartmentBean> callBack);
}
