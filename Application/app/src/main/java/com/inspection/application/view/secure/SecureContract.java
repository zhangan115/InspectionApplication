package com.inspection.application.view.secure;


import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.secure.SecureBean;

/**
 * Created by Yangzb on 2017/7/23 16:50
 * E-mail：yangzongbin@si-top.com
 */
interface SecureContract {

    interface Presenter extends BasePresenter {

        void getSecureInfo(long securityId);
    }

    interface View extends BaseView<Presenter> {

        void showData(SecureBean secureBean);

        void showLoading();

        void hideLoading();

        void noData();

        void showMessage(String message);
    }

}