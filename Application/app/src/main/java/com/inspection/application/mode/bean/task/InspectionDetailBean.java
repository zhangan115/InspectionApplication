package com.inspection.application.mode.bean.task;

import android.os.Parcel;
import android.os.Parcelable;

import com.inspection.application.mode.bean.equipment.RoomListBean;

import java.util.List;

/**
 * 巡检数据
 * Created by zhangan on 2017-07-07.
 */

public class InspectionDetailBean implements Parcelable {

    private long endTime;
    private int isManualCreated;
    private long planEndTime;
    private long planStartTime;
    private long startTime;
    private long taskId;
    private String taskName;
    private int taskState;
    private List<ExecutorUserList> executorUserList;
    private List<RoomListBean> roomList;

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

    public List<ExecutorUserList> getExecutorUserList() {
        return executorUserList;
    }

    public void setExecutorUserList(List<ExecutorUserList> executorUserList) {
        this.executorUserList = executorUserList;
    }

    public List<RoomListBean> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<RoomListBean> roomList) {
        this.roomList = roomList;
    }

    protected InspectionDetailBean(Parcel in) {
        endTime = in.readLong();
        isManualCreated = in.readInt();
        planEndTime = in.readLong();
        planStartTime = in.readLong();
        startTime = in.readLong();
        taskId = in.readLong();
        taskName = in.readString();
        taskState = in.readInt();
        executorUserList = in.createTypedArrayList(ExecutorUserList.CREATOR);
        roomList = in.createTypedArrayList(RoomListBean.CREATOR);
    }

    public static final Creator<InspectionDetailBean> CREATOR = new Creator<InspectionDetailBean>() {
        @Override
        public InspectionDetailBean createFromParcel(Parcel in) {
            return new InspectionDetailBean(in);
        }

        @Override
        public InspectionDetailBean[] newArray(int size) {
            return new InspectionDetailBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(endTime);
        parcel.writeInt(isManualCreated);
        parcel.writeLong(planEndTime);
        parcel.writeLong(planStartTime);
        parcel.writeLong(startTime);
        parcel.writeLong(taskId);
        parcel.writeString(taskName);
        parcel.writeInt(taskState);
        parcel.writeTypedList(executorUserList);
        parcel.writeTypedList(roomList);
    }
}
