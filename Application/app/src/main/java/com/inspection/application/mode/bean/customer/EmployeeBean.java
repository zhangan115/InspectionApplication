package com.inspection.application.mode.bean.customer;

import android.os.Parcel;
import android.os.Parcelable;

import com.inspection.application.mode.bean.user.User;

/**
 * Created by zhangan on 2017-06-26.
 */

public class EmployeeBean implements Parcelable {

    private boolean select;
    private User user;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User userId) {
        this.user = userId;
    }


    public EmployeeBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.user, flags);
    }

    protected EmployeeBean(Parcel in) {
        this.select = in.readByte() != 0;
        this.user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<EmployeeBean> CREATOR = new Creator<EmployeeBean>() {
        @Override
        public EmployeeBean createFromParcel(Parcel source) {
            return new EmployeeBean(source);
        }

        @Override
        public EmployeeBean[] newArray(int size) {
            return new EmployeeBean[size];
        }
    };
}
