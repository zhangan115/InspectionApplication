package com.inspection.application.view.equipment.data;


import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.equipment.CheckValue;
import com.inspection.application.mode.bean.equipment.InspectionData;

/**
 * Created by pingan on 2017/7/3.
 */

interface EquipmentDataContact {

    interface Presenter extends BasePresenter {

        void getCheckData(long equipId);

        void getCheckValue(long equipId, int inspectId);
    }

    interface View extends BaseView<Presenter> {

        void showCheckData(InspectionData checkDatas);

        void showCheckValue(CheckValue checkValue);

        void showLoading();

        void hideLoading();

        void noData();
    }
}
