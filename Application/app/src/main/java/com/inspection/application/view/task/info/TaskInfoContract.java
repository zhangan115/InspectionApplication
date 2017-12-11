package com.inspection.application.view.task.info;

import android.support.annotation.NonNull;

import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.task.InspectionDetailBean;

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
    }

    interface Presenter extends BasePresenter {

        void getInspectionDetailList(long taskId);
    }

}
