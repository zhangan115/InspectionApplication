package com.inspection.application.view.setting;

import android.support.annotation.NonNull;


import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.version.NewVersion;

import java.io.File;

/**
 * 设置
 */
interface SettingContract {

    interface Presenter extends BasePresenter {
        void getNewVersion();

        void uploadUserPhoto(File file);

        void cleanCache();
    }

    interface View extends BaseView<Presenter> {

        void newVersionDialog(@NonNull NewVersion version);

        void currentVersion();

        void downLoadApp();

        void uploadUserPhotoSuccess(String url);

        void uploadUserPhotoFail();

        void showUploadProgress();

        void hideProgress();
    }

}