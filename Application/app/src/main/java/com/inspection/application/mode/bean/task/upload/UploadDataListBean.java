package com.inspection.application.mode.bean.task.upload;

import java.util.List;

/**
 * Created by zhangan on 2017-07-11.
 */

public class UploadDataListBean {
    private int dataId;
    private List<UploadDataItemValueListBean> dataItemValueList;

    public UploadDataListBean() {
    }

    public UploadDataListBean(int dataId, List<UploadDataItemValueListBean> dataItemValueList) {
        this.dataId = dataId;
        this.dataItemValueList = dataItemValueList;
    }

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public List<UploadDataItemValueListBean> getDataItemValueList() {
        return dataItemValueList;
    }

    public void setDataItemValueList(List<UploadDataItemValueListBean> dataItemValueList) {
        this.dataItemValueList = dataItemValueList;
    }
}
