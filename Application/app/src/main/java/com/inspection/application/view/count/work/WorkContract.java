package com.inspection.application.view.count.work;

import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.count.DeptType;
import com.inspection.application.mode.bean.count.MissCountBean;
import com.inspection.application.mode.bean.count.WorkCountBean;

import java.util.List;

/**
 * 漏检统计
 * Created by pingan on 2017/12/21.
 */

public interface WorkContract {

    interface Presenter extends BasePresenter {

        void getDeptId(String types);

        void getChartData(long deptId, String time);
    }

    interface View extends BaseView<Presenter> {

        void showMessage(String message);

        void showDeptList(List<DeptType> deptTypes);

        void showLoading();

        void hideLoading();

        void showChartData(List<WorkCountBean> countBeans);

        void noData();
    }

}
