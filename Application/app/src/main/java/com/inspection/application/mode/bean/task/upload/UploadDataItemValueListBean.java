package com.inspection.application.mode.bean.task.upload;

/**
 * Created by zhangan on 2017-07-11.
 */

public class UploadDataItemValueListBean {
    private long dataItemValueId;
    private String value;

    public UploadDataItemValueListBean() {
    }

    public UploadDataItemValueListBean(long dataItemValueId, String value) {
        this.dataItemValueId = dataItemValueId;
        this.value = value;
    }

    public long getDataItemValueId() {
        return dataItemValueId;
    }

    public void setDataItemValueId(long dataItemValueId) {
        this.dataItemValueId = dataItemValueId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
