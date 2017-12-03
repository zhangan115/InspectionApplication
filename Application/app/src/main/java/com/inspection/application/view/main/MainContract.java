package com.inspection.application.view.main;

import android.support.annotation.Nullable;

import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.version.NewVersion;

/**
 * main
 * Created by pingan on 2017/12/2.
 */

public interface MainContract {

    interface Presenter extends BasePresenter {
        /**
         * 检测新版本
         */
        void getNewVersion();

    }

    interface View extends BaseView<Presenter> {

        /**
         * 显示新版本
         *
         * @param newVersion 新版本
         */
        void showNewVersion(NewVersion newVersion);

        //显示异常信息
        void showMessage(@Nullable String message);
    }
}
