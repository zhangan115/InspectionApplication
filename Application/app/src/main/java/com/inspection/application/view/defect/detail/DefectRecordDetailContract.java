package com.inspection.application.view.defect.detail;

import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.fault.FaultDetail;

/**
 * 缺陷详情
 * Created by pingan on 2017/12/13.
 */

public interface DefectRecordDetailContract {

    interface Presenter extends BasePresenter {

        void getFaultDetail(long id);
    }

    interface View extends BaseView<Presenter> {

        void showLoading();

        void hideLoading();

        void noData();

        void showData(FaultDetail faultDetail);

        void showMessage(String message);

    }

}
