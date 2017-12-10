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

        void getTaskList(String date);
    }

    interface View extends BaseView<Presenter> {

        void showTaskList(List<InspectionBean> been);

        void showLoading();

        void hideLoading();

        void noData();

        void showMessage(String message);
    }
}
