package com.inspection.application.view.main.news.show;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.mode.bean.news.db.NewsBean;
import com.inspection.application.mode.callback.IListCallBack;
import com.inspection.application.mode.source.news.NewsDataSource;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

/**
 * 消息
 * Created by pingan on 2017/12/9.
 */
public class NewsPresenter implements NewsContract.Presenter {

    private final NewsDataSource mNewsDataSource;
    private final NewsContract.View mView;
    private CompositeSubscription mSubscription;

    public NewsPresenter(NewsDataSource mNewsDataSource, NewsContract.View mView) {
        this.mNewsDataSource = mNewsDataSource;
        this.mView = mView;
        mView.setPresenter(this);
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscription.clear();
    }

    @Override
    public void getMessageList(int type, long message) {
        mNewsDataSource.getNewsData(type, message, new IListCallBack<NewsBean>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<NewsBean> list) {
                mView.showMessageListMore(list);
            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void onFinish() {
                mView.hideLoadingMore();
            }

            @Override
            public void noData() {
                mView.noMoreData();
            }
        });
    }

    @Override
    public void getMessageList(int type) {
        mNewsDataSource.getNewsData(type, -1, new IListCallBack<NewsBean>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<NewsBean> list) {
                mView.showMessageList(list);
            }

            @Override
            public void onError(@Nullable String message) {
                mView.showMessage(message);
            }

            @Override
            public void onFinish() {
                mView.hideLoading();
            }

            @Override
            public void noData() {
                mView.noData();
            }
        });
    }

    @Override
    public void requestNewsMessage() {
        mNewsDataSource.requestMessage(new NewsDataSource.RequestNewsMessageCallBack() {
            @Override
            public void onFinish() {
                mView.requestMessageFinish();
            }
        });
    }
}
