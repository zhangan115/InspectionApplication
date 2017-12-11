package com.inspection.application.view.task.info;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.mode.bean.task.InspectionBean;
import com.inspection.application.mode.bean.task.InspectionDetailBean;
import com.inspection.application.mode.callback.IObjectCallBack;
import com.inspection.application.mode.source.task.TaskDataSource;

import rx.subscriptions.CompositeSubscription;

/**
 * task info presenter
 * Created by pingan on 2017/12/11.
 */
class TaskInfoPresenter implements TaskInfoContract.Presenter {

    private final TaskDataSource mTaskDataSource;
    private final TaskInfoContract.View mView;
    private CompositeSubscription mSubscription;

    TaskInfoPresenter(TaskDataSource mTaskDataSource, TaskInfoContract.View mView) {
        this.mTaskDataSource = mTaskDataSource;
        this.mView = mView;
        mView.setPresenter(this);
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void getInspectionDetailList(long taskId) {
        mView.showLoading();
        mSubscription.add(mTaskDataSource.getTaskInfo(taskId, new IObjectCallBack<InspectionDetailBean>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull InspectionDetailBean inspectionDetailBean) {
                mView.showData(inspectionDetailBean);
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
        mSubscription.clear();
    }
}
