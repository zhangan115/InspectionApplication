package com.inspection.application.mode.bean.task.upload;

import java.util.List;

/**
 * Created by zhangan on 2017-07-11.
 */

public class UploadTaskEquipmentBean {
    private int taskEquipmentId;
    private int taskEquipmentState;
    private UploadEquipmentBean equipment;
    private List<UploadDataListBean> dataList;

    public UploadTaskEquipmentBean() {
    }

    public UploadTaskEquipmentBean(int taskEquipmentId, int taskEquipmentState, UploadEquipmentBean equipment, List<UploadDataListBean> dataList) {
        this.taskEquipmentId = taskEquipmentId;
        this.taskEquipmentState = taskEquipmentState;
        this.equipment = equipment;
        this.dataList = dataList;
    }

    public int getTaskEquipmentId() {
        return taskEquipmentId;
    }

    public void setTaskEquipmentId(int taskEquipmentId) {
        this.taskEquipmentId = taskEquipmentId;
    }

    public int getTaskEquipmentState() {
        return taskEquipmentState;
    }

    public void setTaskEquipmentState(int taskEquipmentState) {
        this.taskEquipmentState = taskEquipmentState;
    }

    public UploadEquipmentBean getEquipment() {
        return equipment;
    }

    public void setEquipment(UploadEquipmentBean equipment) {
        this.equipment = equipment;
    }

    public List<UploadDataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<UploadDataListBean> dataList) {
        this.dataList = dataList;
    }
}
