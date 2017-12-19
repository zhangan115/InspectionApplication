package com.inspection.application.view.employee;

import android.support.annotation.NonNull;

import com.inspection.application.mode.bean.customer.DepartmentBean;
import com.inspection.application.mode.callback.IListCallBack;
import com.inspection.application.mode.source.customer.CustomerDataSource;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

/**
 * 联系人
 * Created by zhangan on 2017/9/5.
 */

public class EmployeePresenter implements EmployeeContract.Presenter {

    private CompositeSubscription mSubscriptions;
    private final CustomerDataSource mCustomerDataSource;
    private final EmployeeContract.View mView;

    EmployeePresenter(CustomerDataSource mRepository, EmployeeContract.View mView) {
        this.mCustomerDataSource = mRepository;
        this.mView = mView;
        mView.setPresenter(this);
        mSubscriptions = new CompositeSubscription();
    }


    @Override
    public void getEmployee() {
        mView.showLoading();
        mSubscriptions.add(mCustomerDataSource.getEmployeeList(new IListCallBack<DepartmentBean>() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<DepartmentBean> list) {
                mView.showData(list);
            }

            @Override
            public void onError(String message) {
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
        mSubscriptions.clear();
    }
}
