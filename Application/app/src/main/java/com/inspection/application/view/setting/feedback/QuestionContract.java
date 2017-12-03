package com.inspection.application.view.setting.feedback;


import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;

/**
 * 提交意见反馈
 * Created by Administrator on 2017/6/14.
 */
interface QuestionContract {

    interface Presenter extends BasePresenter {

        void postQuestion(String title, String content);
    }

    interface View extends BaseView<Presenter> {

        void postSuccess();

        void postFail();

        void postFinish();

        void showLoading();

        void hideLoading();
    }

}