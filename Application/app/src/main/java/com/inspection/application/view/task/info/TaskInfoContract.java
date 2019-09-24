package com.inspection.application.view.task.info;

import android.support.annotation.NonNull;

import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.task.InspectionDetailBean;
import com.inspection.application.mode.bean.task.RoomListBean;

/**
 * 任务信息
 * Created by pingan on 2017/12/11.
 */

public interface TaskInfoContract {

    interface View extends BaseView<Presenter> {

        void showLoading();

        void hideLoading();

        void showMessage(String message);

        void noData();

        void showData(InspectionDetailBean inspectionBeen);

        void startWork(RoomListBean data);

        void finishAll();
    }

    interface Presenter extends BasePresenter {

        void getInspectionDetailList(long taskId);

        void startTask(RoomListBean data, long taskId);

        void checkTaskFinish(RoomListBean data, long taskId);

        void finishAllTask(long taskId, String info);

        void saveRoomDataToCache(RoomListBean roomListBean, long taskId);
    }

}
