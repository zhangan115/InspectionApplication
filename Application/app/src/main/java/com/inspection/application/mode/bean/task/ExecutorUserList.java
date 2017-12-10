package com.inspection.application.mode.bean.task;


import com.inspection.application.mode.bean.user.User;

/**
 * Created by zhangan on 2017/11/10.
 */

public class ExecutorUserList {
    private long taskId;
    private User user;

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
