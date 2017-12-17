package com.inspection.application.view.task.data;

import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.task.data.CheckBean;
import com.inspection.application.mode.bean.task.data.InspectionDataBean;

/**
 * task data contract
 * Created by pingan on 2017/12/15.
 */

public interface TaskDataContract {

    interface Presenter extends BasePresenter {

        void getCheckData(long taskId);

        void getTaskData(long taskId);

    }

    interface View extends BaseView<Presenter> {

        void showLoading();

        void hideLoading();

        void noData();

        void showCheckData(CheckBean checkBean);

        void showTaskData(InspectionDataBean inspectionDataBean);

        void showMessage(String message);
    }

}
