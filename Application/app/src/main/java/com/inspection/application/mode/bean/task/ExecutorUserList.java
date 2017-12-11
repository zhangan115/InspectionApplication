package com.inspection.application.mode.bean.task;


import android.os.Parcel;
import android.os.Parcelable;

import com.inspection.application.mode.bean.user.User;

/**
 * Created by zhangan on 2017/11/10.
 */

public class ExecutorUserList implements Parcelable {
    private long taskId;
    private User user;

    protected ExecutorUserList(Parcel in) {
        taskId = in.readLong();
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<ExecutorUserList> CREATOR = new Creator<ExecutorUserList>() {
        @Override
        public ExecutorUserList createFromParcel(Parcel in) {
            return new ExecutorUserList(in);
        }

        @Override
        public ExecutorUserList[] newArray(int size) {
            return new ExecutorUserList[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(taskId);
        parcel.writeParcelable(user, i);
    }
}
