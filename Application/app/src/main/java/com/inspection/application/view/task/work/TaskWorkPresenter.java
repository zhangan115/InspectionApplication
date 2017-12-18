package com.inspection.application.view.task.work;

import com.inspection.application.mode.bean.task.RoomListBean;
import com.inspection.application.mode.bean.task.upload.UploadTaskInfo;
import com.inspection.application.mode.source.task.TaskDataSource;

import rx.subscriptions.CompositeSubscription;

/**
 * 点检工作
 * Created by pingan on 2017/12/18.
 */

class TaskWorkPresenter implements TaskWorkContract.Presenter {

    private final TaskDataSource mTaskDataSource;
    private final TaskWorkContract.View mView;
    private CompositeSubscription mSubscription;

    TaskWorkPresenter(TaskDataSource mTaskDataSource, TaskWorkContract.View mView) {
        this.mTaskDataSource = mTaskDataSource;
        this.mView = mView;
        mView.setPresenter(this);
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void uploadTaskData(UploadTaskInfo uploadTaskInfo, RoomListBean roomListBean, int position) {
        mSubscription.add(mTaskDataSource.uploadTaskData(uploadTaskInfo, roomListBean, position, new TaskDataSource.IUploadTaskDataCallBack() {
            @Override
            public void onSuccess() {
                mView.uploadTaskDataSuccess();
            }

            @Override
            public void onFail() {
                mView.uploadTaskDataFail();
            }
        }));
    }

    @Override
    public void finishTask(long taskId, RoomListBean roomListBean) {
        mSubscription.add(mTaskDataSource.finishTaskData(taskId, roomListBean, new TaskDataSource.IFinishTaskDataCallBack() {
            @Override
            public void onSuccess() {
                mView.finishSuccess();
            }

            @Override
            public void onFail(String message) {
                mView.finishFail();
            }
        }));
    }

    @Override
    public void loadEquipmentDb(long taskId, RoomListBean roomListBean) {
        mTaskDataSource.loadTaskEquipData(taskId, roomListBean, new TaskDataSource.ILoadEquipmentDataCallBack() {
            @Override
            public void success() {
                mView.loadEquipSuccess();
            }

            @Override
            public void onFail() {
                mView.loadEquipFail();
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
