package com.inspection.application.mode.bean.equipment.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.inspection.application.app.App;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 区域数据库对象
 * Created by zhangan on 2017-07-10.
 */
@Entity(nameInDb = "room")
public class RoomDb implements Parcelable {

    @Id(autoincrement = true)
    private Long _id;
    private long taskId; //任务id
    private long roomId;//位置id
    private String roomName;//区域名称
    private int state;//状态 0 未开始，1进行中，2已完成
    private long lastSaveTime;//最后保存时间
    private long userId = App.getInstance().getCurrentUser().getUserId();

    protected RoomDb(Parcel in) {
        if (in.readByte() == 0) {
            _id = null;
        } else {
            _id = in.readLong();
        }
        taskId = in.readLong();
        roomId = in.readLong();
        roomName = in.readString();
        state = in.readInt();
        lastSaveTime = in.readLong();
        userId = in.readLong();
    }

    @Generated(hash = 1645517573)
    public RoomDb(Long _id, long taskId, long roomId, String roomName, int state,
            long lastSaveTime, long userId) {
        this._id = _id;
        this.taskId = taskId;
        this.roomId = roomId;
        this.roomName = roomName;
        this.state = state;
        this.lastSaveTime = lastSaveTime;
        this.userId = userId;
    }

    @Generated(hash = 1777354796)
    public RoomDb() {
    }

    public static final Creator<RoomDb> CREATOR = new Creator<RoomDb>() {
        @Override
        public RoomDb createFromParcel(Parcel in) {
            return new RoomDb(in);
        }

        @Override
        public RoomDb[] newArray(int size) {
            return new RoomDb[size];
        }
    };

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getLastSaveTime() {
        return lastSaveTime;
    }

    public void setLastSaveTime(long lastSaveTime) {
        this.lastSaveTime = lastSaveTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (_id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(_id);
        }
        parcel.writeLong(taskId);
        parcel.writeLong(roomId);
        parcel.writeString(roomName);
        parcel.writeInt(state);
        parcel.writeLong(lastSaveTime);
        parcel.writeLong(userId);
    }
}
