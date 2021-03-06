package com.inspection.application.mode.bean.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.inspection.application.mode.bean.equipment.RoomListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户类
 * Created by pingan on 2017/11/30.
 */

public class CustomerBean implements Parcelable {

    private long customerId;
    private int isOpen;
    private long createTime;
    private String contractTime;
    private String customerAddress;
    private String customerImage;
    private String customerLinkman;
    private String customerName;
    private String customerNumber;
    private String customerPhone;
    private String customerRemark;
    private String inTime;
    private List<RoomListBean> roomList;

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public String getContractTime() {
        return contractTime;
    }

    public void setContractTime(String contractTime) {
        this.contractTime = contractTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerImage() {
        return customerImage;
    }

    public void setCustomerImage(String customerImage) {
        this.customerImage = customerImage;
    }

    public String getCustomerLinkman() {
        return customerLinkman;
    }

    public void setCustomerLinkman(String customerLinkman) {
        this.customerLinkman = customerLinkman;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerRemark() {
        return customerRemark;
    }

    public void setCustomerRemark(String customerRemark) {
        this.customerRemark = customerRemark;
    }


    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public List<RoomListBean> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<RoomListBean> roomList) {
        this.roomList = roomList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.contractTime);
        dest.writeLong(this.createTime);
        dest.writeString(this.customerAddress);
        dest.writeLong(this.customerId);
        dest.writeString(this.customerImage);
        dest.writeString(this.customerLinkman);
        dest.writeString(this.customerName);
        dest.writeString(this.customerNumber);
        dest.writeString(this.customerPhone);
        dest.writeString(this.customerRemark);
        dest.writeString(this.inTime);
        dest.writeList(this.roomList);
    }

    public CustomerBean() {
    }

    protected CustomerBean(Parcel in) {
        this.contractTime = in.readString();
        this.createTime = in.readLong();
        this.customerAddress = in.readString();
        this.customerId = in.readLong();
        this.customerImage = in.readString();
        this.customerLinkman = in.readString();
        this.customerName = in.readString();
        this.customerNumber = in.readString();
        this.customerPhone = in.readString();
        this.customerRemark = in.readString();
        this.inTime = in.readString();
        this.roomList = new ArrayList<>();
        in.readList(this.roomList, RoomListBean.class.getClassLoader());
    }

    public static final Creator<CustomerBean> CREATOR = new Creator<CustomerBean>() {
        @Override
        public CustomerBean createFromParcel(Parcel source) {
            return new CustomerBean(source);
        }

        @Override
        public CustomerBean[] newArray(int size) {
            return new CustomerBean[size];
        }
    };
}
