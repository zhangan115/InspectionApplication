package com.inspection.application.view.task;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.mode.bean.task.InspectionBean;
import com.inspection.application.mode.callback.IListCallBack;
import com.inspection.application.mode.source.task.TaskDataSource;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

/**
 * task presenter
 * Created by pingan on 2017/12/9.
 */

public class TaskPresenter implements TaskContract.Presenter {

    private final TaskDataSource mTaskDataSource;
    private final TaskContract.View mView;
    private CompositeSubscription mSubscription;

    TaskPresenter(TaskDataSource mTaskDataSource, TaskContract.View mView) {
        this.mTaskDataSource = mTaskDataSource;
        this.mView = mView;
        mView.setPresenter(this);
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void getTaskList(String date) {
        mView.showLoading();
        mSubscription.add(mTaskDataSource.getTaskList(date, new IListCallBack<InspectionBean>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<InspectionBean> list) {
                mView.showTaskList(list);
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
    public void getTask(int position, long taskId) {
        mSubscription.add(mTaskDataSource.getTask(position, taskId, new TaskDataSource.GetTaskCallBack() {
            @Override
            public void onSuccess(int position) {
                mView.getTaskSuccess(position);
            }

            @Override
            public void onFail(String message) {
                mView.showMessage(message);
            }

            @Override
            public void onFinish() {

            }
        }));
    }

    @Override
    public void checkSecure(int position, long taskId) {
        mTaskDataSource.checkSecure(position,taskId, new TaskDataSource.CheckSecureInfoCallBack() {
            @Override
            public void onShowTask(int position) {
                mView.showTask(position);
            }

            @Override
            public void onShowSecure(int position) {
                mView.showSecure(position);
            }

        });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscription.clear();
    }
}
