package com.inspection.application.mode.bean.task;


import com.inspection.application.mode.bean.user.User;

import java.util.List;

/**
 * 巡检列表对象
 * Created by zhangan on 2017-07-05.
 */

public class InspectionBean {

    private int count;
    private long planEndTime;
    private long planStartTime;
    private long startTime;
    private long endTime;
    private long taskId;
    private String taskName;
    private int taskState;
    private int uploadCount;
    private List<String> rooms;
    private int planPeriodType;//检测周期类型 1：每日 2：每周 3：每月
    private int isManualCreated;//是否是人工创建的 1：是 0：不是 人工创建的均视为特检任务
    private User receiveUser;
    private List<User> users;
    private List<ExecutorUserList> executorUserList;//指定的执行人
    private List<ExecutorDeptList> executorDeptList;//指定的执行人

    public List<ExecutorUserList> getExecutorUserList() {
        return executorUserList;
    }

    public void setExecutorUserList(List<ExecutorUserList> executorUserList) {
        this.executorUserList = executorUserList;
    }

    private SecurityPackage securityPackage;

    public SecurityPackage getSecurityPackage() {
        return securityPackage;
    }

    public void setSecurityPackage(SecurityPackage securityPackage) {
        this.securityPackage = securityPackage;
    }

    public int getPlanPeriodType() {
        return planPeriodType;
    }

    public void setPlanPeriodType(int planPeriodType) {
        this.planPeriodType = planPeriodType;
    }

    public User getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(User receiveUser) {
        this.receiveUser = receiveUser;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public static class SecurityPackage {

        private long createTime;
        private long securityId;
        private String securityName;
        private String securityRemark;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getSecurityId() {
            return securityId;
        }

        public void setSecurityId(long securityId) {
            this.securityId = securityId;
        }

        public String getSecurityName() {
            return securityName;
        }

        public void setSecurityName(String securityName) {
            this.securityName = securityName;
        }

        public String getSecurityRemark() {
            return securityRemark;
        }

        public void setSecurityRemark(String securityRemark) {
            this.securityRemark = securityRemark;
        }
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public int getUploadCount() {
        return uploadCount;
    }

    public void setUploadCount(int uploadCount) {
        this.uploadCount = uploadCount;
    }

    public List<String> getRooms() {
        return rooms;
    }

    public void setRooms(List<String> rooms) {
        this.rooms = rooms;
    }

    public List<ExecutorDeptList> getExecutorDeptList() {
        return executorDeptList;
    }

    public void setExecutorDeptList(List<ExecutorDeptList> executorDeptList) {
        this.executorDeptList = executorDeptList;
    }
}
