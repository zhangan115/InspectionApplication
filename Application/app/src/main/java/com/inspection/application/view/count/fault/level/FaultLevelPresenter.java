package com.inspection.application.view.count.fault.level;

import android.support.annotation.NonNull;

import com.inspection.application.mode.bean.count.FaultLevel;
import com.inspection.application.mode.callback.IListCallBack;
import com.inspection.application.mode.source.count.CountDataSource;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Yangzb on 2017/7/4 15:48
 * E-mail：yangzongbin@si-top.com
 */
final class FaultLevelPresenter implements FaultLevelContract.Presenter {
    private CountDataSource mRepository;
    private FaultLevelContract.View mView;
    @NonNull
    private CompositeSubscription subscription;

    FaultLevelPresenter(CountDataSource repository, FaultLevelContract.View view) {
        this.mRepository = repository;
        this.mView = view;
        mView.setPresenter(this);
        subscription = new CompositeSubscription();
    }

    @Override
    public void getChartData(String time) {
        mView.showLoading();
        subscription.add(mRepository.getFaultLevel(time, new IListCallBack<FaultLevel>() {

            @Override
            public void onSuccess() {
            }

            @Override
            public void onData(@NonNull List<FaultLevel> list) {
                mView.showChartData(list);
            }

            @Override
            public void onError(String message) {
                mView.noData();
            }

            @Override
            public void onFinish() {
                mView.hideLoading();
            }

            @Override
            public void noData() {

            }
        }));
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        subscription.clear();
    }
}