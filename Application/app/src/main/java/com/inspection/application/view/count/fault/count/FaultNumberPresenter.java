package com.inspection.application.view.count.fault.count;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.mode.bean.count.FaultNumber;
import com.inspection.application.mode.callback.IListCallBack;
import com.inspection.application.mode.source.count.CountDataSource;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

/**
 * 缺陷数
 * Created by pingan on 2017/12/22.
 */

class FaultNumberPresenter implements FaultNumberCountContact.Presenter {
    private final CountDataSource mRepository;
    private final FaultNumberCountContact.View mView;
    private CompositeSubscription subscription;

    FaultNumberPresenter(CountDataSource mRepository, FaultNumberCountContact.View mView) {
        this.mRepository = mRepository;
        this.mView = mView;
        mView.setPresenter(this);
        subscription = new CompositeSubscription();
    }

    @Override
    public void getFaultNumberData(String time) {
        subscription.add(mRepository.getFaultNumber(time, new IListCallBack<FaultNumber>() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<FaultNumber> list) {
                mView.showFaultNumber(list);
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
        }));
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
