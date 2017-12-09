package com.inspection.application.view.task;

import com.inspection.application.mode.source.task.TaskDataSource;

import rx.subscriptions.CompositeSubscription;

/**
 * task presenter
 * Created by pingan on 2017/12/9.
 */

public class TaskPresenter implements TaskContract.Presenter {

    private final TaskDataSource mTaskDataSource;
    private final TaskContract.View mView;
    private CompositeSubscription mSubscription;

    public TaskPresenter(TaskDataSource mTaskDataSource, TaskContract.View mView) {
        this.mTaskDataSource = mTaskDataSource;
        this.mView = mView;
        mView.setPresenter(this);
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void getTaskList(String date) {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscription.clear();
    }
}
