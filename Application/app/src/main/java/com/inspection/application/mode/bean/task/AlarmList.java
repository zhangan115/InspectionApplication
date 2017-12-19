package com.inspection.application.mode.bean.task;

import com.inspection.application.mode.bean.equipment.EquipmentBean;

/**
 * Created by pingan on 2017/12/18.
 */

public class AlarmList {

    private long alarmId;
    private long commitTime;
    private DataItem dataItem;
    private EquipmentBean equipment;
    private RoomBean room;
    private Task task;
    private String value;

    public long getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(long alarmId) {
        this.alarmId = alarmId;
    }

    public long getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(long commitTime) {
        this.commitTime = commitTime;
    }

    public DataItem getDataItem() {
        return dataItem;
    }

    public void setDataItem(DataItem dataItem) {
        this.dataItem = dataItem;
    }

    public EquipmentBean getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentBean equipment) {
        this.equipment = equipment;
    }

    public RoomBean getRoom() {
        return room;
    }

    public void setRoom(RoomBean room) {
        this.room = room;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static class DataItem {
        private long createTime;
        private long inspectionId;
        private String inspectionName;
        private int inspectionType;
        private float quantityLowlimit;
        private float quantityUplimit;
        private String quantityUnit;

        public float getQuantityLowlimit() {
            return quantityLowlimit;
        }

        public void setQuantityLowlimit(float quantityLowlimit) {
            this.quantityLowlimit = quantityLowlimit;
        }

        public float getQuantityUplimit() {
            return quantityUplimit;
        }

        public void setQuantityUplimit(float quantityUplimit) {
            this.quantityUplimit = quantityUplimit;
        }

        public String getQuantityUnit() {
            return quantityUnit;
        }

        public void setQuantityUnit(String quantityUnit) {
            this.quantityUnit = quantityUnit;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getInspectionId() {
            return inspectionId;
        }

        public void setInspectionId(long inspectionId) {
            this.inspectionId = inspectionId;
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
    }

    public static class Task {
        private long createTime;
        private long planEndTime;
        private long planStartTime;
        private long taskId;
        private String taskName;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getPlanEndTime() {
            return planEndTime;
        }

        public void setPlanEndTime(long planEndTime) {
            this.planEndTime = planEndTime;
        }

        public long getPlanStartTime() {
            return planStartTime;
        }

        public void setPlanStartTime(long planStartTime) {
            this.planStartTime = planStartTime;
        }

        public long getTaskId() {
            return taskId;
        }

        public void setTaskId(long taskId) {
            this.taskId = taskId;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }
    }
}
