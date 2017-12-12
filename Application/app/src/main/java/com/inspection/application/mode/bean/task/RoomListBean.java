package com.inspection.application.mode.bean.task;

import android.os.Parcel;
import android.os.Parcelable;

import com.inspection.application.mode.bean.equipment.db.RoomDb;

import java.util.List;

/**
 * Created by zhangan on 2017-07-10.
 */

public class RoomListBean implements Parcelable {

    private RoomBean room;
    private long startTime;
    private long taskRoomId;
    private int taskRoomState;
    private long endTime;
    private List<TaskEquipmentBean> taskEquipment;
    private int state;//本地属性 未开始 0， 进行中 1，已完成2
    private long lastSaveTime;//最后保存时间
    private RoomDb roomDb;

    public RoomBean getRoom() {
        return room;
    }

    public void setRoom(RoomBean room) {
        this.room = room;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getTaskRoomId() {
        return taskRoomId;
    }

    public void setTaskRoomId(long taskRoomId) {
        this.taskRoomId = taskRoomId;
    }

    public int getTaskRoomState() {
        return taskRoomState;
    }

    public void setTaskRoomState(int taskRoomState) {
        this.taskRoomState = taskRoomState;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public List<TaskEquipmentBean> getTaskEquipment() {
        return taskEquipment;
    }

    public void setTaskEquipment(List<TaskEquipmentBean> taskEquipment) {
        this.taskEquipment = taskEquipment;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getLastSaveTime() {
        return lastSaveTime;
    }

    public void setLastSaveTime(long lastSaveTime) {
        this.lastSaveTime = lastSaveTime;
    }

    public RoomDb getRoomDb() {
        return roomDb;
    }

    public void setRoomDb(RoomDb roomDb) {
        this.roomDb = roomDb;
    }

    protected RoomListBean(Parcel in) {
        room = in.readParcelable(RoomBean.class.getClassLoader());
        startTime = in.readLong();
        taskRoomId = in.readLong();
        taskRoomState = in.readInt();
        endTime = in.readLong();
        taskEquipment = in.createTypedArrayList(TaskEquipmentBean.CREATOR);
        state = in.readInt();
        lastSaveTime = in.readLong();
        roomDb = in.readParcelable(RoomDb.class.getClassLoader());
    }

    public static final Creator<RoomListBean> CREATOR = new Creator<RoomListBean>() {
        @Override
        public RoomListBean createFromParcel(Parcel in) {
            return new RoomListBean(in);
        }

        @Override
        public RoomListBean[] newArray(int size) {
            return new RoomListBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(room, i);
        parcel.writeLong(startTime);
        parcel.writeLong(taskRoomId);
        parcel.writeInt(taskRoomState);
        parcel.writeLong(endTime);
        parcel.writeTypedList(taskEquipment);
        parcel.writeInt(state);
        parcel.writeLong(lastSaveTime);
        parcel.writeParcelable(roomDb, i);
    }
}
