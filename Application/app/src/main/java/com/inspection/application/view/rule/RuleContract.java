package com.inspection.application.view.rule;

import android.support.annotation.NonNull;

import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.customer.StandBean;

/**
 * 规章制度
 * Created by pingan on 2017/12/12.
 */

public interface RuleContract {

    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<Presenter> {

        void showData(@NonNull StandBean standBean);

        void showLoading();

        void hideLoading();

        void noData();
    }
}
