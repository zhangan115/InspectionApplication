package com.inspection.application.view.defect;

import android.support.annotation.NonNull;

import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.fault.FaultList;

import java.util.List;

/**
 * 缺陷记录
 * Created by pingan on 2017/12/13.
 */

interface DefectRecordContract {

    interface Presenter extends BasePresenter {

        void getFaultList(@NonNull String info);

        void getFaultListMore(@NonNull String info);
    }

    interface View extends BaseView<Presenter> {

        void showLoading();

        void hideLoading();

        void showFaultList(@NonNull List<FaultList> data);

        void showMoreFaultList(@NonNull List<FaultList> data);

        void noData();

        void noMoreData();

        void hideLoadingMore();

        void showMessage(String message);
    }

}
