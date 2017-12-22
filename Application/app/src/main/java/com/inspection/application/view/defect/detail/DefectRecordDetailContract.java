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

        void pointFault(String info);

        void sureFault(long faultId, String flowRemark);

        void closeFault(long faultId, String content);
    }

    interface View extends BaseView<Presenter> {

        void showLoading();

        void showActionLoading();

        void hideActionLoading();

        void hideLoading();

        void noData();

        void showData(FaultDetail faultDetail);

        void showMessage(String message);

        void success();

    }

}
