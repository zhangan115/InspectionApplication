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
    private int state;//状态 0 未开始，1进行中，2已完成
    private long lastSaveTime;//最后保存时间
    private int takePhotoPosition = -1;
    private String localPhoto;
    private String photoUrl;
    private long userId = App.getInstance().getCurrentUser().getUserId();


    protected RoomDb(Parcel in) {
        if (in.readByte() == 0) {
            _id = null;
        } else {
            _id = in.readLong();
        }
        taskId = in.readLong();
        roomId = in.readLong();
        state = in.readInt();
        lastSaveTime = in.readLong();
        takePhotoPosition = in.readInt();
        localPhoto = in.readString();
        photoUrl = in.readString();
        userId = in.readLong();
    }

    @Generated(hash = 977374409)
    public RoomDb(Long _id, long taskId, long roomId, int state, long lastSaveTime,
            int takePhotoPosition, String localPhoto, String photoUrl,
            long userId) {
        this._id = _id;
        this.taskId = taskId;
        this.roomId = roomId;
        this.state = state;
        this.lastSaveTime = lastSaveTime;
        this.takePhotoPosition = takePhotoPosition;
        this.localPhoto = localPhoto;
        this.photoUrl = photoUrl;
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
        parcel.writeInt(state);
        parcel.writeLong(lastSaveTime);
        parcel.writeInt(takePhotoPosition);
        parcel.writeString(localPhoto);
        parcel.writeString(photoUrl);
        parcel.writeLong(userId);
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public long getTaskId() {
        return this.taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public long getRoomId() {
        return this.roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getLastSaveTime() {
        return this.lastSaveTime;
    }

    public void setLastSaveTime(long lastSaveTime) {
        this.lastSaveTime = lastSaveTime;
    }

    public int getTakePhotoPosition() {
        return this.takePhotoPosition;
    }

    public void setTakePhotoPosition(int takePhotoPosition) {
        this.takePhotoPosition = takePhotoPosition;
    }

    public String getLocalPhoto() {
        return this.localPhoto;
    }

    public void setLocalPhoto(String localPhoto) {
        this.localPhoto = localPhoto;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
