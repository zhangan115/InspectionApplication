package com.inspection.application.view.count.fault.level;

import android.support.annotation.Nullable;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

/**
 * x轴显示
 * Created by zhangan on 2017-05-19.
 */

class ChartXFormatterN implements IAxisValueFormatter {

    @Nullable
    private List<String> dataValues;

    ChartXFormatterN(@Nullable List<String> dataValues) {
        this.dataValues = dataValues;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int position = (int) value;
        if (position < dataValues.size()) {
            return dataValues.get(position);
        } else {
            return position + "";
        }
    }
}
