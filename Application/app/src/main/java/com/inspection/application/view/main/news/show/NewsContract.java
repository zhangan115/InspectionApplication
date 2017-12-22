package com.inspection.application.view.main.news.show;

import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.news.db.NewsBean;

import java.util.List;

/**
 * 消息逻辑
 * Created by pingan on 2017/12/9.
 */

public interface NewsContract {

    interface Presenter extends BasePresenter {

        void getMessageList(int type, long message);

        void getMessageList(int type);

        void requestNewsMessage();
    }

    interface View extends BaseView<Presenter> {

        void showMessageList(List<NewsBean> newsBeans);

        void showMessageListMore(List<NewsBean> newsBeans);

        void showLoading();

        void requestMessageFinish();

        void hideLoading();

        void noData();

        void showMessage(String message);

        void noMoreData();

        void hideLoadingMore();

    }
}
