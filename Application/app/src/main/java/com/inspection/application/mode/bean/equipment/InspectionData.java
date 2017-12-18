package com.inspection.application.mode.bean.equipment;

import java.util.List;

/**
 * 巡检数据
 * Created by zhangan on 2017/10/20.
 */

public class InspectionData {

    private List<CheckData> dataList;
    private List<InspectionPic> picList;

    public List<CheckData> getDataList() {
        return dataList;
    }

    public void setDataList(List<CheckData> dataList) {
        this.dataList = dataList;
    }

    public List<InspectionPic> getPicList() {
        return picList;
    }

    public void setPicList(List<InspectionPic> picList) {
        this.picList = picList;
    }

    public static class InspectionPic {

        private long createTime;
        private int inplaceId;
        private String inplacePicUrl;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getInplaceId() {
            return inplaceId;
        }

        public void setInplaceId(int inplaceId) {
            this.inplaceId = inplaceId;
        }

        public String getInplacePicUrl() {
            return inplacePicUrl;
        }

        public void setInplacePicUrl(String inplacePicUrl) {
            this.inplacePicUrl = inplacePicUrl;
        }
    }
}
