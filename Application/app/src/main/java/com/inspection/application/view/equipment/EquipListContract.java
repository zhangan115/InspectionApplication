package com.inspection.application.view.equipment;


import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.equipment.RoomListBean;

import java.util.List;

/**
 * 设备
 */
interface EquipListContract {

    interface Presenter extends BasePresenter {

        void getEquipList();

    }

    interface View extends BaseView<Presenter> {

        void showRoomList(List<RoomListBean> list);

        void showLoading();

        void hideLoading();

        void noData();
    }

}