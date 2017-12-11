package com.inspection.application.mode.bean.task.upload;


import com.inspection.application.mode.bean.task.RoomBean;

import java.util.List;

/**
 * Created by zhangan on 2017-07-11.
 */

public class UploadRoomListBean {
    private RoomBean room;
    private long startTime;
    private long taskRoomId;
    private int taskRoomState;
    private long endTime;
    private List<UploadTaskEquipmentBean> taskEquipment;

    public UploadRoomListBean(RoomBean room, long startTime, long taskRoomId, int taskRoomState, long endTime, List<UploadTaskEquipmentBean> taskEquipment) {
        this.room = room;
        this.startTime = startTime;
        this.taskRoomId = taskRoomId;
        this.taskRoomState = taskRoomState;
        this.endTime = endTime;
        this.taskEquipment = taskEquipment;
    }

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

    public List<UploadTaskEquipmentBean> getTaskEquipment() {
        return taskEquipment;
    }

    public void setTaskEquipment(List<UploadTaskEquipmentBean> taskEquipment) {
        this.taskEquipment = taskEquipment;
    }
}
