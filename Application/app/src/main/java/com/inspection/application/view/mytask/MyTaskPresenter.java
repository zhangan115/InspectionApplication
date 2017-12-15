package com.inspection.application.view.mytask;

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

public class MyTaskPresenter implements MyTaskContract.Presenter {

    private final TaskDataSource mTaskDataSource;
    private final MyTaskContract.View mView;
    private CompositeSubscription mSubscription;

    MyTaskPresenter(TaskDataSource mTaskDataSource, MyTaskContract.View mView) {
        this.mTaskDataSource = mTaskDataSource;
        this.mView = mView;
        mView.setPresenter(this);
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void getTaskList() {
        mView.showLoading();
        mSubscription.add(mTaskDataSource.getMyTaskList(null, new IListCallBack<InspectionBean>() {
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
    public void getTaskListMore(Long lastId) {
        mSubscription.add(mTaskDataSource.getMyTaskList(lastId, new IListCallBack<InspectionBean>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<InspectionBean> list) {
                mView.showTaskListMore(list);
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
        mSubscription.clear();
    }
}
