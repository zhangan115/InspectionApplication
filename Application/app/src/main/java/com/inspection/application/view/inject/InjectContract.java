package com.inspection.application.view.inject;


import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.inject.InjectEquipment;
import com.inspection.application.mode.bean.inject.InjectRoomBean;

import java.util.List;

/**
 * 注油管理合约
 * Created by Administrator on 2017/6/22.
 */
interface InjectContract {

    interface Presenter extends BasePresenter {

        void searchEquipment(List<InjectEquipment> allEquipment, String searchText);

        void getRoomList();

        void getRoomEquipmentList(long roomId);

        void getNeedInjectEqu(List<InjectEquipment> injectEquipments);

        void injectionEquipment(long equipmentId, Integer cycle, int position);
    }

    interface View extends BaseView<Presenter> {

        void showLoading();

        void hideLoading();

        void noData();

        void showRoomList(List<InjectRoomBean> roomBeanList);

        void getRoomListError();

        void showRoomEquipment(List<InjectEquipment> injectEquipments);

        void getEquipmentError();

        void injectSuccess(int position);

        void injectFail(int position);

        void showNeedInjectEqu(List<InjectEquipment> injectEquipments);
    }

}