package com.inspection.application.view.main.news;

import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;

/**
 * 消息逻辑
 * Created by pingan on 2017/12/9.
 */

public interface NewsContract {

    interface Presenter extends BasePresenter {

        void getMessageState();

        void getMessageList();

        void getMoreMessageList();
    }

    interface View extends BaseView<Presenter> {

        void showMessageList();

        void showLoading();

        void hideLoading();

        void noData();

    }
}
