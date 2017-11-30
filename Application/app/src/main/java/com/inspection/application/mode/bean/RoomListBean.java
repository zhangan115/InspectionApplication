package com.inspection.application.mode.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pingan on 2017/11/30.
 */

public class RoomListBean implements Parcelable {
    private String createTime;
    private int deleteState;
    private int roomId;
    private String roomName;
    private String roomRemark;

    protected RoomListBean(Parcel in) {
        createTime = in.readString();
        deleteState = in.readInt();
        roomId = in.readInt();
        roomName = in.readString();
        roomRemark = in.readString();
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(int deleteState) {
        this.deleteState = deleteState;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(createTime);
        parcel.writeInt(deleteState);
        parcel.writeInt(roomId);
        parcel.writeString(roomName);
        parcel.writeString(roomRemark);
    }
}
