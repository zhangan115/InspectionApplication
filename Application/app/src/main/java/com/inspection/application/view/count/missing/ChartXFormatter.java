package com.inspection.application.view.count.missing;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.inspection.application.mode.bean.count.MissCountBean;

import java.util.List;

/**
 * Created by pingan on 2017/12/22.
 */

class ChartXFormatter implements IAxisValueFormatter {

    List<MissCountBean> countBeans;

    ChartXFormatter(List<MissCountBean> countBeans) {
        this.countBeans = countBeans;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if (countBeans != null && countBeans.size() - 1 <= (int) value) {
            return countBeans.get((int) value).getRealName();
        } else {
            return "";
        }
    }
}
