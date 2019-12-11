package com.inspection.application.view.task.info;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.common.ConstantInt;
import com.inspection.application.mode.bean.task.InspectionDetailBean;
import com.inspection.application.mode.bean.task.RoomListBean;
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
    public void startTask(RoomListBean data, long taskId) {
        data.setTaskRoomState(ConstantInt.ROOM_STATE_2);
        data.setStartTime(System.currentTimeMillis());
        mView.startWork(data);
//        mSubscription.add(mTaskDataSource.startTask(data, taskId, new TaskDataSource.IStartTaskCallBack() {
//
//            @Override
//            public void onSuccess(RoomListBean roomListBean) {
//
//            }
//
//            @Override
//            public void onFail(String message) {
//                mView.showMessage(message);
//            }
//        }));
    }

    @Override
    public void checkTaskFinish(final RoomListBean data, long taskId) {
        mTaskDataSource.checkTaskFinish(data, taskId, new TaskDataSource.ICheckTaskCallBack() {
            @Override
            public void onFinish(RoomListBean roomListBean) {
                mView.startWork(roomListBean);
            }

            @Override
            public void onNotFinish(RoomListBean roomListBean) {
                mView.showMessage("还没有完成点检任务");
                mView.startWork(roomListBean);
            }
        });
    }

    @Override
    public void finishAllTask(long taskId, String info) {
        mTaskDataSource.finishAllTask(taskId, info, new IObjectCallBack<String>() {
            @Override
            public void onSuccess() {
                mView.finishAll();
            }

            @Override
            public void onData(@NonNull String s) {

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
        });
    }

    @Override
    public void saveRoomDataToCache(RoomListBean roomListBean, long taskId) {
        mTaskDataSource.saveRoomData(roomListBean, taskId);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscription.clear();
    }
}
