package com.inspection.application.mode.bean.task.upload;

/**
 * Created by zhangan on 2017-07-11.
 */

public class UploadDataItemValueListBean {
    private int dataItemValueId;
    private String value;

    public UploadDataItemValueListBean() {
    }

    public UploadDataItemValueListBean(int dataItemValueId, String value) {
        this.dataItemValueId = dataItemValueId;
        this.value = value;
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
