package com.inspection.application.view.employee;


import android.support.annotation.Nullable;

import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.customer.DepartmentBean;

import java.util.List;

/**
 * 获取部门人员
 * Created by zhangan on 2017-06-26.
 */

interface EmployeeContract {

    interface View extends BaseView<Presenter> {

        void showData(List<DepartmentBean> list);

        void noData();

        void showLoading();

        void hideLoading();

        void showMessage(@Nullable String message);
    }

    interface Presenter extends BasePresenter {

        void getEmployee();
    }
}
