package com.inspection.application.view.task;

import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;

/**
 * task contract
 * Created by pingan on 2017/12/9.
 */

public interface TaskContract {

    interface Presenter extends BasePresenter {

        void getTaskList(String date);
    }

    interface View extends BaseView<Presenter> {

        void showTaskList();

        void showLoading();

        void hideLoading();

        void noData();
    }
}
