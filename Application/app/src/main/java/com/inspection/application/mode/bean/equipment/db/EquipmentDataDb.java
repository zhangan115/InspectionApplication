package com.inspection.application.mode.bean.equipment.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.inspection.application.app.App;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 设备巡检的数据
 * Created by zhangan on 2017-07-10.
 */
@Entity(nameInDb = "equipment_data")
public class EquipmentDataDb implements Parcelable {

    @Id(autoincrement = true)
    private Long _id;
    private int type;//类型
    private String value;//值
    private long taskId;
    private long roomId;
    private long equipmentId;
    private long dataItemId;
    private String localPhoto;
    private String chooseInspectionName;
    private long currentUserId = App.getInstance().getCurrentUser().getUserId();

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getTaskId() {
        return this.taskId;
    }

    public String getChooseInspectionName() {
        return chooseInspectionName;
    }

    public void setChooseInspectionName(String chooseInspectionName) {
        this.chooseInspectionName = chooseInspectionName;
    }

    public long getDataItemId() {
        return dataItemId;
    }

    public void setDataItemId(long dataItemId) {
        this.dataItemId = dataItemId;
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

    public long getEquipmentId() {
        return this.equipmentId;
    }

    public void setEquipmentId(long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public long getCurrentUserId() {
        return this.currentUserId;
    }

    public void setCurrentUserId(long currentUserId) {
        this.currentUserId = currentUserId;
    }

    public EquipmentDataDb() {
    }


    @Generated(hash = 1721718177)
    public EquipmentDataDb(Long _id, int type, String value, long taskId, long roomId, long equipmentId,
            long dataItemId, String localPhoto, String chooseInspectionName, long currentUserId) {
        this._id = _id;
        this.type = type;
        this.value = value;
        this.taskId = taskId;
        this.roomId = roomId;
        this.equipmentId = equipmentId;
        this.dataItemId = dataItemId;
        this.localPhoto = localPhoto;
        this.chooseInspectionName = chooseInspectionName;
        this.currentUserId = currentUserId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this._id);
        dest.writeInt(this.type);
        dest.writeString(this.value);
        dest.writeLong(this.taskId);
        dest.writeLong(this.roomId);
        dest.writeLong(this.equipmentId);
        dest.writeString(this.chooseInspectionName);
        dest.writeLong(this.dataItemId);
        dest.writeLong(this.currentUserId);
    }

    public String getLocalPhoto() {
        return this.localPhoto;
    }

    public void setLocalPhoto(String localPhoto) {
        this.localPhoto = localPhoto;
    }

    protected EquipmentDataDb(Parcel in) {
        this._id = (Long) in.readValue(Long.class.getClassLoader());
        this.type = in.readInt();
        this.value = in.readString();
        this.taskId = in.readLong();
        this.roomId = in.readLong();
        this.equipmentId = in.readLong();
        this.chooseInspectionName = in.readString();
        this.dataItemId = in.readLong();
        this.currentUserId = in.readLong();
    }

    public static final Creator<EquipmentDataDb> CREATOR = new Creator<EquipmentDataDb>() {
        @Override
        public EquipmentDataDb createFromParcel(Parcel source) {
            return new EquipmentDataDb(source);
        }

        @Override
        public EquipmentDataDb[] newArray(int size) {
            return new EquipmentDataDb[size];
        }
    };
}
