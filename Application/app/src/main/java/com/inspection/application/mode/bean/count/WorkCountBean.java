package com.inspection.application.mode.bean.count;

/**
 * Created by pingan on 2017/12/22.
 */

public class WorkCountBean {

    private String realName;
    private int taskCount;
    private long userId;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
