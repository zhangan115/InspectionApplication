package com.inspection.application.mode.source.count;

import android.support.annotation.NonNull;

import com.inspection.application.mode.bean.count.DeptType;
import com.inspection.application.mode.bean.count.FaultLevel;
import com.inspection.application.mode.bean.count.FaultNumber;
import com.inspection.application.mode.bean.count.MissCountBean;
import com.inspection.application.mode.bean.count.MonthCount;
import com.inspection.application.mode.bean.count.WorkCountBean;
import com.inspection.application.mode.callback.IListCallBack;

import rx.Subscription;

/**
 * 统计mode
 * Created by pingan on 2017/12/21.
 */

public interface CountDataSource {

    @NonNull
    Subscription getDeptList(String info, IListCallBack<DeptType> callBack);

    @NonNull
    Subscription getMissCount(long deptId, String time, IListCallBack<MissCountBean> callBack);

    @NonNull
    Subscription getWorkCount(long deptId, String time, IListCallBack<WorkCountBean> callBack);

    @NonNull
    Subscription getStaffMonth(long deptId, String time, IListCallBack<MonthCount> callBack);

    @NonNull
    Subscription getFaultLevel(String time, IListCallBack<FaultLevel> callBack);

    @NonNull
    Subscription getFaultNumber(String time, IListCallBack<FaultNumber> callBack);

    void cleanCache();
}
