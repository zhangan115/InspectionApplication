package com.inspection.application.mode.bean.task.upload;

/**
 * Created by zhangan on 2017-07-11.
 */

public class UploadDataItemBean {

    private long inspectionId;
    private long createTime;
    private int deleteState;
    private long deleteTime;
    private String inspectionName;
    private int inspectionType;
    private String quantityLowlimit;
    private String quantityUplimit;
    private String quantityUnit;
    private String value;

    public UploadDataItemBean() {
    }

    public UploadDataItemBean(long inspectionId, long createTime, int deleteState, long deleteTime, String inspectionName
            , int inspectionType, String quantityLowlimit, String quantityUplimit, String quantityUnit, String value) {
        this.inspectionId = inspectionId;
        this.createTime = createTime;
        this.deleteState = deleteState;
        this.deleteTime = deleteTime;
        this.inspectionName = inspectionName;
        this.inspectionType = inspectionType;
        this.quantityLowlimit = quantityLowlimit;
        this.quantityUplimit = quantityUplimit;
        this.quantityUnit = quantityUnit;
        this.value = value;
    }

    public long getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(long inspectionId) {
        this.inspectionId = inspectionId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(int deleteState) {
        this.deleteState = deleteState;
    }

    public long getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(long deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getInspectionName() {
        return inspectionName;
    }

    public void setInspectionName(String inspectionName) {
        this.inspectionName = inspectionName;
    }

    public int getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(int inspectionType) {
        this.inspectionType = inspectionType;
    }

    public String getQuantityLowlimit() {
        return quantityLowlimit;
    }

    public void setQuantityLowlimit(String quantityLowlimit) {
        this.quantityLowlimit = quantityLowlimit;
    }

    public String getQuantityUplimit() {
        return quantityUplimit;
    }

    public void setQuantityUplimit(String quantityUplimit) {
        this.quantityUplimit = quantityUplimit;
    }

    public String getQuantityUnit() {
        return quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
