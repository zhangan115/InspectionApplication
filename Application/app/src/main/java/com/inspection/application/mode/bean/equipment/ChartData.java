package com.inspection.application.mode.bean.equipment;

import java.util.List;

/**
 * Created by zhangan on 2017-06-29.
 */

public class ChartData {


    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        private float dataValue;

        public float getDataValue() {
            return dataValue;
        }

        public void setDataValue(float dataValue) {
            this.dataValue = dataValue;
        }
    }
}
