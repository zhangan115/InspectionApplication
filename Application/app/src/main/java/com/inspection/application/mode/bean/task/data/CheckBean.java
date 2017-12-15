package com.inspection.application.mode.bean.task.data;


import com.inspection.application.mode.bean.user.User;

import java.util.List;

/**
 * Created by Yangzb on 2017/7/17 19:18
 * E-mail：yangzongbin@si-top.com
 */
public class CheckBean {

    private int flowingCount;
    private int uploadCount;
    private long planStartTime;
    private int closeCount;
    private int allFault;
    private int count;
    private int repairCount;
    private String taskName;
    private User receiveUser;
    private int taskState;
    private int pendingCount;
    private long planEndTime;
    private long startTime;
    private long endTime;
    private List<User> users;
    private List<String> rooms;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getFlowingCount() {
        return flowingCount;
    }

    public void setFlowingCount(int flowingCount) {
        this.flowingCount = flowingCount;
    }

    public int getUploadCount() {
        return uploadCount;
    }

    public void setUploadCount(int uploadCount) {
        this.uploadCount = uploadCount;
    }

    public long getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(long planStartTime) {
        this.planStartTime = planStartTime;
    }

    public int getCloseCount() {
        return closeCount;
    }

    public void setCloseCount(int closeCount) {
        this.closeCount = closeCount;
    }

    public int getAllFault() {
        return allFault;
    }

    public void setAllFault(int allFault) {
        this.allFault = allFault;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRepairCount() {
        return repairCount;
    }

    public void setRepairCount(int repairCount) {
        this.repairCount = repairCount;
    }

    public User getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(User receiveUser) {
        this.receiveUser = receiveUser;
    }

    public int getTaskState() {
        return taskState;
    }

    public void setTaskState(int taskState) {
        this.taskState = taskState;
    }

    public int getPendingCount() {
        return pendingCount;
    }

    public void setPendingCount(int pendingCount) {
        this.pendingCount = pendingCount;
    }

    public long getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(long planEndTime) {
        this.planEndTime = planEndTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<String> getRooms() {
        return rooms;
    }

    public void setRooms(List<String> rooms) {
        this.rooms = rooms;
    }
}
