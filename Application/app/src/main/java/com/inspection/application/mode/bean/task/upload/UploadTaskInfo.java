package com.inspection.application.mode.bean.task.upload;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangan on 2017-07-11.
 */

public class UploadTaskInfo implements Parcelable{
    private long endTime;
    private int isManualCreated;
    private long planEndTime;
    private long planStartTime;
    private long startTime;
    private long taskId;
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

    public UploadTaskInfo(long endTime, int isManualCreated, long planEndTime, long planStartTime, long startTime, long taskId, String taskName, int taskState) {
        this.endTime = endTime;
        this.isManualCreated = isManualCreated;
        this.planEndTime = planEndTime;
        this.planStartTime = planStartTime;
        this.startTime = startTime;
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskState = taskState;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.endTime);
        dest.writeInt(this.isManualCreated);
        dest.writeLong(this.planEndTime);
        dest.writeLong(this.planStartTime);
        dest.writeLong(this.startTime);
        dest.writeLong(this.taskId);
        dest.writeString(this.taskName);
        dest.writeInt(this.taskState);
        dest.writeList(this.roomList);
    }

    protected UploadTaskInfo(Parcel in) {
        this.endTime = in.readLong();
        this.isManualCreated = in.readInt();
        this.planEndTime = in.readLong();
        this.planStartTime = in.readLong();
        this.startTime = in.readLong();
        this.taskId = in.readLong();
        this.taskName = in.readString();
        this.taskState = in.readInt();
        this.roomList = new ArrayList<UploadRoomListBean>();
        in.readList(this.roomList, UploadRoomListBean.class.getClassLoader());
    }

    public static final Creator<UploadTaskInfo> CREATOR = new Creator<UploadTaskInfo>() {
        @Override
        public UploadTaskInfo createFromParcel(Parcel source) {
            return new UploadTaskInfo(source);
        }

        @Override
        public UploadTaskInfo[] newArray(int size) {
            return new UploadTaskInfo[size];
        }
    };
}
