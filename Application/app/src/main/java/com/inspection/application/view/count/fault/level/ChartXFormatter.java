package com.inspection.application.view.count.fault.level;

import android.support.annotation.Nullable;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.inspection.application.mode.bean.chart.ChartData;

import java.util.List;

/**
 * x轴显示
 * Created by zhangan on 2017-05-19.
 */

class ChartXFormatter implements IAxisValueFormatter {

    @Nullable
    private List<ChartData> dataValues;

    ChartXFormatter(@Nullable List<ChartData> dataValues) {
        this.dataValues = dataValues;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int position = (int) value;
        return String.valueOf(position);
    }
}
