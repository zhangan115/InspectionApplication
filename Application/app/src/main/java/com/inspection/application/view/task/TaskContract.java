package com.inspection.application.view.task;

import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.task.InspectionBean;

import java.util.List;

/**
 * task contract
 * Created by pingan on 2017/12/9.
 */

public interface TaskContract {

    interface Presenter extends BasePresenter {

        /**
         * 获取任务列表
         *
         * @param date 时间
         */
        void getTaskList(String date);

        /**
         * 领取任务
         *
         * @param position 位置
         * @param taskId   任务id
         */
        void getTask(int position, long taskId);

        void checkSecure(int position, long taskId);
    }

    interface View extends BaseView<Presenter> {

        void showTaskList(List<InspectionBean> been);

        void showLoading();

        void hideLoading();

        void noData();

        void showMessage(String message);

        void getTaskSuccess(int position);

        void showTask(int position);

        void showSecure(int position);
    }
}
