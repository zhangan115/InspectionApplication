package com.inspection.application.view.fault;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.equipment.EquipmentBean;
import com.inspection.application.mode.bean.fault.DefaultFlowBean;
import com.inspection.application.mode.bean.image.Image;
import com.inspection.application.mode.bean.user.User;

import org.json.JSONObject;

import java.util.List;

/**
 * upload fault
 * Created by pingan on 2017/12/5.
 */

public interface FaultContract {

    interface View extends BaseView<Presenter> {

        void showUploadProgress();

        void hideUploadProgress();

        void showImageList(@NonNull List<Image> images);

        void uploadImageSuccess();

        void uploadImageFail(Image image);

        void uploadFaultDataSuccess();

        void uploadFaultDataFail();

        void showDefaultFlowData(@NonNull List<DefaultFlowBean> beans);

        void noDefaultFlowData();

        void showMessage(@Nullable String message);
    }

    interface Presenter extends BasePresenter {

        void getCacheFromDb(@Nullable String inspectionTag);

        void uploadPhoto(@NonNull Image image);

        void uploadFaultData(@NonNull JSONObject jsonObject);

        void getUserFlowList();

        void deleteImage(Image image);

        void cleanCache(List<Image> images);
    }
}
