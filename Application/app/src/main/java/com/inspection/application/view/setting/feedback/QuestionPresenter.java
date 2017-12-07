package com.inspection.application.view.setting.feedback;

import android.support.annotation.NonNull;

import com.inspection.application.mode.callback.IObjectCallBack;
import com.inspection.application.mode.source.application.ApplicationDataSource;

import rx.subscriptions.CompositeSubscription;

/**
 * 提交意见反馈
 * Created by Administrator on 2017/6/14.
 */
final class QuestionPresenter implements QuestionContract.Presenter {
    private ApplicationDataSource mRepository;
    private QuestionContract.View mView;
    private CompositeSubscription mSubscription;

    QuestionPresenter(ApplicationDataSource repository, QuestionContract.View view) {
        this.mRepository = repository;
        this.mView = view;
        mView.setPresenter(this);
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void postQuestion(String title, String content) {
        mView.showLoading();
        mSubscription.add(mRepository.postQuestion(title, content, new IObjectCallBack<String>() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull String s) {
                mView.postSuccess();
            }

            @Override
            public void onError(String message) {
                mView.postFail();
            }

            @Override
            public void noData() {

            }

            @Override
            public void onFinish() {
                mView.hideLoading();
            }
        }));
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscription.clear();
    }
}
