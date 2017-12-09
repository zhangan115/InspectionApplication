package com.inspection.application.view.main.news;

import com.inspection.application.mode.source.news.NewsDataSource;

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

    }

    @Override
    public void getMessageState() {

    }

    @Override
    public void getMessageList() {

    }

    @Override
    public void getMoreMessageList() {

    }
}
