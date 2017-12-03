package com.inspection.application.mode.bean.equipment;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 配电室类
 * Created by pingan on 2017/11/30.
 */

public class RoomListBean implements Parcelable {

    private long roomId;
    private String createTime;
    private String roomName;
    private String roomRemark;
    private List<EquipmentBean> equipments;

    protected RoomListBean(Parcel in) {
        roomId = in.readLong();
        createTime = in.readString();
        roomName = in.readString();
        roomRemark = in.readString();
        equipments = in.createTypedArrayList(EquipmentBean.CREATOR);
    }

    public static final Creator<RoomListBean> CREATOR = new Creator<RoomListBean>() {
        @Override
        public RoomListBean createFromParcel(Parcel in) {
            return new RoomListBean(in);
        }

        @Override
        public RoomListBean[] newArray(int size) {
            return new RoomListBean[size];
        }
    };

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomRemark() {
        return roomRemark;
    }

    public void setRoomRemark(String roomRemark) {
        this.roomRemark = roomRemark;
    }

    public List<EquipmentBean> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<EquipmentBean> equipments) {
        this.equipments = equipments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(roomId);
        parcel.writeString(createTime);
        parcel.writeString(roomName);
        parcel.writeString(roomRemark);
        parcel.writeTypedList(equipments);
    }
}
