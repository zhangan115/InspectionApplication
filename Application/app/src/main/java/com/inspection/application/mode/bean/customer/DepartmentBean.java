package com.inspection.application.mode.bean.customer;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangan on 2017-06-26.
 */

public class DepartmentBean implements Parcelable {
    private String name;
    private String deptName;
    private long deptId;

    private List<EmployeeBean> userList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EmployeeBean> getUserList() {
        return userList;
    }

    public void setUserList(List<EmployeeBean> userList) {
        this.userList = userList;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    public DepartmentBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.deptName);
        dest.writeLong(this.deptId);
        dest.writeTypedList(this.userList);
    }

    protected DepartmentBean(Parcel in) {
        this.name = in.readString();
        this.deptName = in.readString();
        this.deptId = in.readLong();
        this.userList = in.createTypedArrayList(EmployeeBean.CREATOR);
    }

    public static final Creator<DepartmentBean> CREATOR = new Creator<DepartmentBean>() {
        @Override
        public DepartmentBean createFromParcel(Parcel source) {
            return new DepartmentBean(source);
        }

        @Override
        public DepartmentBean[] newArray(int size) {
            return new DepartmentBean[size];
        }
    };
}
