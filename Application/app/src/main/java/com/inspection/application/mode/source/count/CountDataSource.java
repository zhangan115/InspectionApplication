package com.inspection.application.mode.source.count;

import android.support.annotation.NonNull;

import com.inspection.application.mode.bean.count.DeptType;
import com.inspection.application.mode.callback.IListCallBack;

import rx.Subscription;

/**
 * 统计mode
 * Created by pingan on 2017/12/21.
 */

public interface CountDataSource {

    @NonNull
    Subscription getDeptList(String info, IListCallBack<DeptType> callBack);


}
