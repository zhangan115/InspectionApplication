package com.inspection.application.view.alarm;

import android.support.annotation.NonNull;

import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.fault.FaultList;
import com.inspection.application.mode.bean.task.AlarmList;

import java.util.List;

/**
 * 故障报警
 * Created by pingan on 2017/12/18.
 */

interface AlarmContract {

    interface Presenter extends BasePresenter {

        void getAlarmList(String info);

        void getAlarmListMore(String info);

    }

    interface View extends BaseView<Presenter> {

        void showMessage(String message);

        void showLoading();

        void hideLoading();

        void noData();

        void noMoreData();

        void hideLoadingMore();

        void showAlarmList(@NonNull List<AlarmList> data);

        void showMoreAlarmList(@NonNull List<AlarmList> data);
    }

}
