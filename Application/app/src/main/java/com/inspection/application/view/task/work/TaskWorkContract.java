package com.inspection.application.view.task.work;

import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.task.RoomListBean;
import com.inspection.application.mode.bean.task.upload.UploadTaskInfo;

/**
 * 任务工作
 * Created by pingan on 2017/12/18.
 */

public interface TaskWorkContract {

    interface Presenter extends BasePresenter {

        void uploadTaskData(UploadTaskInfo uploadTaskInfo, RoomListBean roomListBean, int position);

        void finishTask(UploadTaskInfo uploadTaskInfo, RoomListBean roomListBean);

        void loadEquipmentDb(long taskId, RoomListBean roomListBean);

    }

    interface View extends BaseView<Presenter> {

        void uploadTaskDataSuccess();

        void uploadTaskDataFail();

        void finishSuccess();

        void showMessage(String message);

        void loadEquipFail();

        void loadEquipSuccess();
    }
}
