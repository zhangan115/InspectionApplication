package com.inspection.application.mode.bean.task.upload;

import java.util.List;

/**
 * Created by zhangan on 2017-07-11.
 */

public class UploadTaskInfo {
    private long endTime;
    private int isManualCreated;
    private long planEndTime;
    private long planStartTime;
    private long startTime;
    private int taskId;
    private String taskName;
    private int taskState;
    private List<UploadRoomListBean> roomList;

    public UploadTaskInfo() {
    }

    public UploadTaskInfo(long endTime, int isManualCreated, long planEndTime, long planStartTime, long startTime, int taskId, String taskName, int taskState, List<UploadRoomListBean> roomList) {
        this.endTime = endTime;
        this.isManualCreated = isManualCreated;
        this.planEndTime = planEndTime;
        this.planStartTime = planStartTime;
        this.startTime = startTime;
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskState = taskState;
        this.roomList = roomList;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getIsManualCreated() {
        return isManualCreated;
    }

    public void setIsManualCreated(int isManualCreated) {
        this.isManualCreated = isManualCreated;
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

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTaskState() {
        return taskState;
    }

    public void setTaskState(int taskState) {
        this.taskState = taskState;
    }

    public List<UploadRoomListBean> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<UploadRoomListBean> roomList) {
        this.roomList = roomList;
    }
}
