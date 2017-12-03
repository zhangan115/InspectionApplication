package com.inspection.application.mode.bean.equipment;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 设备类型
 * Created by zhangan on 2017/10/11.
 */

public class EquipmentType implements Parcelable {

    private long createTime;
    private int deleteState;
    private long equipmentTypeId;
    private String equipmentTypeName;
    private String equipmentTypeRemark;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(int deleteState) {
        this.deleteState = deleteState;
    }

    public long getEquipmentTypeId() {
        return equipmentTypeId;
    }

    public void setEquipmentTypeId(long equipmentTypeId) {
        this.equipmentTypeId = equipmentTypeId;
    }

    public String getEquipmentTypeName() {
        return equipmentTypeName;
    }

    public void setEquipmentTypeName(String equipmentTypeName) {
        this.equipmentTypeName = equipmentTypeName;
    }

    public String getEquipmentTypeRemark() {
        return equipmentTypeRemark;
    }

    public void setEquipmentTypeRemark(String equipmentTypeRemark) {
        this.equipmentTypeRemark = equipmentTypeRemark;
    }

    public EquipmentType() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.createTime);
        dest.writeInt(this.deleteState);
        dest.writeLong(this.equipmentTypeId);
        dest.writeString(this.equipmentTypeName);
        dest.writeString(this.equipmentTypeRemark);
    }

    protected EquipmentType(Parcel in) {
        this.createTime = in.readLong();
        this.deleteState = in.readInt();
        this.equipmentTypeId = in.readLong();
        this.equipmentTypeName = in.readString();
        this.equipmentTypeRemark = in.readString();
    }

    public static final Creator<EquipmentType> CREATOR = new Creator<EquipmentType>() {
        @Override
        public EquipmentType createFromParcel(Parcel source) {
            return new EquipmentType(source);
        }

        @Override
        public EquipmentType[] newArray(int size) {
            return new EquipmentType[size];
        }
    };
}
