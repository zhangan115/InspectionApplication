package com.inspection.application.view.count.fault.count;

import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.count.FaultNumber;

import java.util.List;

/**
 * 缺陷数量
 * Created by pingan on 2017/12/22.
 */

public interface FaultNumberCountContact {

    interface Presenter extends BasePresenter {

        void getFaultNumberData(String time);

    }

    interface View extends BaseView<Presenter> {

        void showLoading();

        void hideLoading();

        void noData();

        void showFaultNumber(List<FaultNumber> faultNumbers);

        void showMessage(String message);
    }
}
