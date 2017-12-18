package com.inspection.application.view.alarm;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.mode.bean.fault.FaultList;
import com.inspection.application.mode.bean.task.AlarmList;
import com.inspection.application.mode.callback.IListCallBack;
import com.inspection.application.mode.source.task.TaskDataSource;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

/**
 * 故障报警
 * Created by pingan on 2017/12/18.
 */

class AlarmPresenter implements AlarmContract.Presenter {

    private final TaskDataSource mTaskDataSource;
    private final AlarmContract.View mView;
    private CompositeSubscription subscription;

    AlarmPresenter(TaskDataSource mTaskDataSource, AlarmContract.View mView) {
        this.mTaskDataSource = mTaskDataSource;
        this.mView = mView;
        mView.setPresenter(this);
        subscription = new CompositeSubscription();
    }

    @Override
    public void getAlarmList(String info) {
        subscription.add(mTaskDataSource.getAlarmList(info, new IListCallBack<AlarmList>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<AlarmList> list) {
                mView.showAlarmList(list);
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
    public void getAlarmListMore(String info) {
        subscription.add(mTaskDataSource.getAlarmList(info, new IListCallBack<AlarmList>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<AlarmList> list) {
                mView.showMoreAlarmList(list);
            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void onFinish() {
                mView.hideLoadingMore();
            }

            @Override
            public void noData() {
                mView.noMoreData();
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
