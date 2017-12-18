package com.inspection.application.mode.bean.equipment;

import java.util.List;

/**
 * Created by Yangzb on 2017/7/19 16:57
 * E-mail：yangzongbin@si-top.com
 */
public class CheckValue {

    /**
     * inspectionId : 10
     * quantityUplimit : 24.5
     * valueList : [{"commitTime":1499767338000,"dataItemValueId":259,"value":"100"},{"commitTime":1499740521000,"dataItemValueId":288,"value":"63.5"},{"commitTime":1499740395000,"dataItemValueId":278,"value":"23.5"}]
     * inspectionType : 2
     * quantityUnit : ℃
     * inspectionName : 温度
     * quantityLowlimit : 11.7
     */

    private int inspectionId;
    private double quantityUplimit;
    private int inspectionType;
    private String quantityUnit;
    private String inspectionName;
    private double quantityLowlimit;
    private List<ValueListBean> valueList;

    public int getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(int inspectionId) {
        this.inspectionId = inspectionId;
    }

    public double getQuantityUplimit() {
        return quantityUplimit;
    }

    public void setQuantityUplimit(double quantityUplimit) {
        this.quantityUplimit = quantityUplimit;
    }

    public int getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(int inspectionType) {
        this.inspectionType = inspectionType;
    }

    public String getQuantityUnit() {
        return quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public String getInspectionName() {
        return inspectionName;
    }

    public void setInspectionName(String inspectionName) {
        this.inspectionName = inspectionName;
    }

    public double getQuantityLowlimit() {
        return quantityLowlimit;
    }

    public void setQuantityLowlimit(double quantityLowlimit) {
        this.quantityLowlimit = quantityLowlimit;
    }

    public List<ValueListBean> getValueList() {
        return valueList;
    }

    public void setValueList(List<ValueListBean> valueList) {
        this.valueList = valueList;
    }

    public static class ValueListBean {
        /**
         * commitTime : 1499767338000
         * dataItemValueId : 259
         * value : 100
         */

        private long commitTime;
        private int dataItemValueId;
        private String value;

        public long getCommitTime() {
            return commitTime;
        }

        public void setCommitTime(long commitTime) {
            this.commitTime = commitTime;
        }

        public int getDataItemValueId() {
            return dataItemValueId;
        }

        public void setDataItemValueId(int dataItemValueId) {
            this.dataItemValueId = dataItemValueId;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
