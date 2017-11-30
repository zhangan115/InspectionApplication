package com.inspection.application.view.splash;


import com.library.base.BasePresenter;
import com.library.base.BaseView;

/**
 * 启动屏幕
 * Created by zhangan on 2017-07-24.
 */

public interface SplashContract {

    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<Presenter> {

        //需要登陆
        void needLogin();

        //显示欢迎页
        void showWelcome();

        //进入首页
        void openHome();
    }
}
