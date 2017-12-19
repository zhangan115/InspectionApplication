package com.inspection.application.view.task.work.show;

import com.inspection.application.mode.bean.task.DataItemValueListBean;
import com.inspection.application.mode.bean.task.TaskEquipmentBean;
import com.inspection.application.mode.source.task.TaskDataSource;

import rx.subscriptions.CompositeSubscription;

/**
 * 显示数据操作
 * Created by pingan on 2017/12/17.
 */

public class ShowTaskWorkPresenter implements ShowTaskWorkContact.Presenter {

    private final TaskDataSource mTaskDataSource;
    private final ShowTaskWorkContact.View mView;
    private CompositeSubscription mSubscription;

    public ShowTaskWorkPresenter(TaskDataSource mTaskDataSource, ShowTaskWorkContact.View mView) {
        this.mTaskDataSource = mTaskDataSource;
        this.mView = mView;
        mView.setPresenter(this);
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void loadDtaFromDb(long taskId, long roomId, TaskEquipmentBean taskEquipmentBean) {
        mView.showLoading();
        mSubscription.add(mTaskDataSource.loadTaskEquipData(taskId, roomId, taskEquipmentBean, new TaskDataSource.ILoadEquipmentDataCallBack() {

            @Override
            public void success() {
                mView.hideLoading();
                mView.showData();
            }

            @Override
            public void onFail() {

            }
        }));
    }

    @Override
    public void uploadPhoto(DataItemValueListBean dataItemValueListBean) {
        mSubscription.add(mTaskDataSource.uploadTaskPhoto(dataItemValueListBean, new TaskDataSource.IUploadPhotoCallBack() {
            @Override
            public void onSuccess() {
                mView.uploadPhotoSuccess();
            }

            @Override
            public void onFail() {
                mView.uploadPhotoFail();
                mView.showMessage("上传失败");
            }
        }));
    }

    @Override
    public void deletePhoto(DataItemValueListBean dataItemValueListBean) {
        mTaskDataSource.deletePhoto(dataItemValueListBean);
        mView.deletePhoto();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscription.clear();
    }
}
