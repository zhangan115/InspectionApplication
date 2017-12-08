package com.inspection.application.view.main.mine;

import android.support.annotation.NonNull;

import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.version.NewVersion;

import java.io.File;

/**
 * 设置
 */
interface MineContract {

    interface Presenter extends BasePresenter {

        void getNewVersion();

        void uploadUserPhoto(File file);

        void cleanCache();

        void exitApp();
    }

    interface View extends BaseView<Presenter> {

        void newVersionDialog(@NonNull NewVersion version);

        void currentVersion();

        void downLoadApp();

        void uploadUserPhotoSuccess();

        void uploadUserPhotoFail();

        void showUploadProgress();

        void hideProgress();
    }

}