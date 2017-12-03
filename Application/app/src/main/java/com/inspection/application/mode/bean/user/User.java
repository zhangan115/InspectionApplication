package com.inspection.application.mode.bean.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用户类
 * Created by zhangan on 2017-06-22.
 */

public class User implements Parcelable {

    private long userId;
    private int deleteState;
    private long joinTime;
    private String realName;
    private int userIdentity;
    private String userName;
    private String userPhone;
    private String userRoleNames;
    private String portraitUrl;
    private int isRepair;
    private int userType;
    private CustomerBean customer;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(int deleteState) {
        this.deleteState = deleteState;
    }

    public long getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(long joinTime) {
        this.joinTime = joinTime;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(int userIdentity) {
        this.userIdentity = userIdentity;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserRoleNames() {
        return userRoleNames;
    }

    public void setUserRoleNames(String userRoleNames) {
        this.userRoleNames = userRoleNames;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public int getIsRepair() {
        return isRepair;
    }

    public void setIsRepair(int isRepair) {
        this.isRepair = isRepair;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }

    public User() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.customer, flags);
        dest.writeInt(this.deleteState);
        dest.writeLong(this.joinTime);
        dest.writeString(this.realName);
        dest.writeLong(this.userId);
        dest.writeInt(this.userIdentity);
        dest.writeString(this.userName);
        dest.writeString(this.userPhone);
        dest.writeInt(this.userType);
        dest.writeInt(this.isRepair);
        dest.writeString(this.userRoleNames);
        dest.writeString(this.portraitUrl);
    }

    protected User(Parcel in) {
        this.customer = in.readParcelable(CustomerBean.class.getClassLoader());
        this.deleteState = in.readInt();
        this.joinTime = in.readLong();
        this.realName = in.readString();
        this.userId = in.readLong();
        this.userIdentity = in.readInt();
        this.userName = in.readString();
        this.userPhone = in.readString();
        this.userType = in.readInt();
        this.isRepair = in.readInt();
        this.userRoleNames = in.readString();
        this.portraitUrl = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
