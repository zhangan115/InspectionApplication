package com.inspection.application.view.taskdata;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.mode.bean.task.data.CheckBean;
import com.inspection.application.mode.bean.task.data.InspectionDataBean;
import com.inspection.application.mode.callback.IObjectCallBack;
import com.inspection.application.mode.source.task.TaskDataSource;

import rx.subscriptions.CompositeSubscription;

/**
 * task data p
 * Created by pingan on 2017/12/15.
 */

class TaskDataPresenter implements TaskDataContract.Presenter {

    private final TaskDataSource mTaskDataSource;
    private final TaskDataContract.View mView;
    private CompositeSubscription mSubscription;

    TaskDataPresenter(TaskDataSource mTaskDataSource, TaskDataContract.View mView) {
        this.mTaskDataSource = mTaskDataSource;
        this.mView = mView;
        mView.setPresenter(this);
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void getCheckData(long taskId) {
        mSubscription.add(mTaskDataSource.getCheckData(taskId, new IObjectCallBack<CheckBean>() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull CheckBean checkBean) {
                mView.showCheckData(checkBean);
            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void noData() {

            }

            @Override
            public void onFinish() {

            }
        }));
    }

    @Override
    public void getTaskData(long taskId) {
        mView.showLoading();
        mSubscription.add(mTaskDataSource.getTaskData(taskId, new IObjectCallBack<InspectionDataBean>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull InspectionDataBean inspectionDataBean) {
                mView.showTaskData(inspectionDataBean);
            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void noData() {
                mView.noData();
            }

            @Override
            public void onFinish() {
                mView.hideLoading();
            }
        }));
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
