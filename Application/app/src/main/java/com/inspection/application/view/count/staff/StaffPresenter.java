package com.inspection.application.view.count.staff;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.mode.bean.count.DeptType;
import com.inspection.application.mode.bean.count.MissCountBean;
import com.inspection.application.mode.bean.count.MonthCount;
import com.inspection.application.mode.callback.IListCallBack;
import com.inspection.application.mode.source.count.CountDataSource;
import com.inspection.application.view.count.missing.MissingContract;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

/**
 * 漏检统计
 * Created by pingan on 2017/12/21.
 */
class StaffPresenter implements StaffContract.Presenter {
    private final CountDataSource mCountDataSource;
    private final StaffContract.View mView;
    private CompositeSubscription subscription;

    StaffPresenter(CountDataSource mCountDataSource, StaffContract.View mView) {
        this.mCountDataSource = mCountDataSource;
        this.mView = mView;
        mView.setPresenter(this);
        subscription = new CompositeSubscription();
    }

    @Override
    public void getDeptId(String types) {
        mView.showLoading();
        subscription.add(mCountDataSource.getDeptList(types, new IListCallBack<DeptType>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<DeptType> list) {
                mView.showDeptList(list);
            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void onFinish() {
                mView.hideLoading();
            }

            @Override
            public void noData() {
                mView.noData();
            }
        }));
    }

    @Override
    public void getChartData(long deptId, String time) {
        mView.showLoading();
        subscription.add(mCountDataSource.getStaffMonth(deptId, time, new IListCallBack<MonthCount>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<MonthCount> list) {
                mView.showChartData(list);
            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void onFinish() {
                mView.hideLoading();
            }

            @Override
            public void noData() {
                mView.noData();
            }
        }));
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        subscription.clear();
    }
}
