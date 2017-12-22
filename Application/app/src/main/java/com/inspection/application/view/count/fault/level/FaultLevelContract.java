package com.inspection.application.view.count.fault.level;


import com.inspection.application.base.BasePresenter;
import com.inspection.application.base.BaseView;
import com.inspection.application.mode.bean.count.FaultLevel;
import com.inspection.application.mode.bean.chart.ChartData;

import java.util.List;

/**
 * Created by Yangzb on 2017/7/4 15:44
 * E-mail：yangzongbin@si-top.com
 */
interface FaultLevelContract {

    interface Presenter extends BasePresenter {

        void getChartData(String time);

    }

    interface View extends BaseView<Presenter> {
        void showData(List<ChartData> chartDatas);

        void showChartData(List<FaultLevel> faultLevels);

        void showLoading();

        void hideLoading();

        void noData();
    }

}