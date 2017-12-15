package com.inspection.application.view.mytask;

import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.task.InspectionBean;

import java.util.List;

/**
 * task contract
 * Created by pingan on 2017/12/9.
 */

public interface MyTaskContract {

    interface Presenter extends BasePresenter {

        /**
         * 获取任务列表
         */
        void getTaskList();

        /**
         * 获取任务列表
         */
        void getTaskListMore(Long lstId);

    }

    interface View extends BaseView<Presenter> {

        void showTaskList(List<InspectionBean> been);

        void showLoading();

        void hideLoading();

        void noData();

        void showMessage(String message);

        void noMoreData();

        void hideLoadingMore();

        void showTaskListMore(List<InspectionBean> been);
    }
}
