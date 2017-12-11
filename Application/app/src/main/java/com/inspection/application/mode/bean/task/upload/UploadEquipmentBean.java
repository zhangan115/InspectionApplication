package com.inspection.application.mode.bean.task.upload;

/**
 * Created by zhangan on 2017-07-11.
 */

public class UploadEquipmentBean {
    private long createTime;
    private int deleteState;
    private long equipmentId;
    private String equipmentName;
    private String equipmentNumber;
    private String equipmentRemark;
    private long manufactureTime;
    private String manufacturer;
    private String supplier;

    public UploadEquipmentBean() {
    }

    public UploadEquipmentBean(int deleteState, long equipmentId, String equipmentName, String equipmentNumber, String equipmentRemark, long manufactureTime, String manufacturer, String supplier) {
        this.deleteState = deleteState;
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.equipmentNumber = equipmentNumber;
        this.equipmentRemark = equipmentRemark;
        this.manufactureTime = manufactureTime;
        this.manufacturer = manufacturer;
        this.supplier = supplier;
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

    public long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public String getEquipmentRemark() {
        return equipmentRemark;
    }

    public void setEquipmentRemark(String equipmentRemark) {
        this.equipmentRemark = equipmentRemark;
    }

    public long getManufactureTime() {
        return manufactureTime;
    }

    public void setManufactureTime(long manufactureTime) {
        this.manufactureTime = manufactureTime;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}
