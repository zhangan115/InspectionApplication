package com.inspection.application.view.task.work.show;

import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.equipment.db.EquipmentDataDb;
import com.inspection.application.mode.bean.task.DataItemValueListBean;
import com.inspection.application.mode.bean.task.TaskEquipmentBean;

/**
 * 显示数据
 * Created by pingan on 2017/12/17.
 */

public interface ShowTaskWorkContact {

    interface View extends BaseView<Presenter> {

        void showLoading();

        void hideLoading();

        void showData();

        void uploadPhotoSuccess();

        void uploadPhotoFail();

        void showMessage(String message);

        void deletePhoto();
    }

    interface Presenter extends BasePresenter {

        void loadDtaFromDb(long taskId, long roomId, TaskEquipmentBean taskEquipmentBean);

        void uploadPhoto(DataItemValueListBean dataItemValueListBean);

        void deletePhoto(DataItemValueListBean dataItemValueListBean);
    }
}
