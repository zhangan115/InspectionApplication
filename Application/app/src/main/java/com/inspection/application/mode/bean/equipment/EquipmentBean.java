package com.inspection.application.mode.bean.equipment;

import android.os.Parcel;
import android.os.Parcelable;

import com.inspection.application.mode.bean.equipment.db.EquipmentDb;


/**
 * 设备信息
 * Created by zhangan on 2017-07-10.
 */

public class EquipmentBean implements Parcelable {

    private long createTime;
    private int deleteState;
    private long equipmentId;
    private String equipmentName;
    private String equipmentNumber;
    private String equipmentRemark;
    private long manufactureTime;
    private String manufacturer;//生产厂
    private String supplier;//供应商
    private String nameplatePicUrl;
    private String equipmentSn;
    private int voltageLevel;
    private int equipmentState;
    private int workingState;
    private long startTime;
    private long installTime;
    private String itemNumber;
    private String equipmentFsn;//设备出厂编号
    private EquipmentType equipmentType;
    private String equipmentAlias;
    private RoomListBean room;//所属配电
    private EquipmentDb equipmentDb;//本地添加属性:设备录入的数据

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

    public long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public String getEquipmentRemark() {
        return equipmentRemark;
    }

    public void setEquipmentRemark(String equipmentRemark) {
        this.equipmentRemark = equipmentRemark;
    }

    public long getManufactureTime() {
        return manufactureTime;
    }

    public void setManufactureTime(long manufactureTime) {
        this.manufactureTime = manufactureTime;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getNameplatePicUrl() {
        return nameplatePicUrl;
    }

    public void setNameplatePicUrl(String nameplatePicUrl) {
        this.nameplatePicUrl = nameplatePicUrl;
    }

    public String getEquipmentSn() {
        return equipmentSn;
    }

    public void setEquipmentSn(String equipmentSn) {
        this.equipmentSn = equipmentSn;
    }

    public int getVoltageLevel() {
        return voltageLevel;
    }

    public void setVoltageLevel(int voltageLevel) {
        this.voltageLevel = voltageLevel;
    }

    public int getEquipmentState() {
        return equipmentState;
    }

    public void setEquipmentState(int equipmentState) {
        this.equipmentState = equipmentState;
    }

    public int getWorkingState() {
        return workingState;
    }

    public void setWorkingState(int workingState) {
        this.workingState = workingState;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getInstallTime() {
        return installTime;
    }

    public void setInstallTime(long installTime) {
        this.installTime = installTime;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getEquipmentFsn() {
        return equipmentFsn;
    }

    public void setEquipmentFsn(String equipmentFsn) {
        this.equipmentFsn = equipmentFsn;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getEquipmentAlias() {
        return equipmentAlias;
    }

    public void setEquipmentAlias(String equipmentAlias) {
        this.equipmentAlias = equipmentAlias;
    }

    public RoomListBean getRoom() {
        return room;
    }

    public void setRoom(RoomListBean room) {
        this.room = room;
    }

    public EquipmentDb getEquipmentDb() {
        return equipmentDb;
    }

    public void setEquipmentDb(EquipmentDb equipmentDb) {
        this.equipmentDb = equipmentDb;
    }

    protected EquipmentBean(Parcel in) {
        createTime = in.readLong();
        deleteState = in.readInt();
        equipmentId = in.readLong();
        equipmentName = in.readString();
        equipmentNumber = in.readString();
        equipmentRemark = in.readString();
        manufactureTime = in.readLong();
        manufacturer = in.readString();
        supplier = in.readString();
        nameplatePicUrl = in.readString();
        equipmentSn = in.readString();
        voltageLevel = in.readInt();
        equipmentState = in.readInt();
        workingState = in.readInt();
        startTime = in.readLong();
        installTime = in.readLong();
        itemNumber = in.readString();
        equipmentFsn = in.readString();
        equipmentType = in.readParcelable(EquipmentType.class.getClassLoader());
        equipmentAlias = in.readString();
        room = in.readParcelable(RoomListBean.class.getClassLoader());
        equipmentDb = in.readParcelable(EquipmentDb.class.getClassLoader());
    }

    public static final Creator<EquipmentBean> CREATOR = new Creator<EquipmentBean>() {
        @Override
        public EquipmentBean createFromParcel(Parcel in) {
            return new EquipmentBean(in);
        }

        @Override
        public EquipmentBean[] newArray(int size) {
            return new EquipmentBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(createTime);
        parcel.writeInt(deleteState);
        parcel.writeLong(equipmentId);
        parcel.writeString(equipmentName);
        parcel.writeString(equipmentNumber);
        parcel.writeString(equipmentRemark);
        parcel.writeLong(manufactureTime);
        parcel.writeString(manufacturer);
        parcel.writeString(supplier);
        parcel.writeString(nameplatePicUrl);
        parcel.writeString(equipmentSn);
        parcel.writeInt(voltageLevel);
        parcel.writeInt(equipmentState);
        parcel.writeInt(workingState);
        parcel.writeLong(startTime);
        parcel.writeLong(installTime);
        parcel.writeString(itemNumber);
        parcel.writeString(equipmentFsn);
        parcel.writeParcelable(equipmentType, i);
        parcel.writeString(equipmentAlias);
        parcel.writeParcelable(room, i);
        parcel.writeParcelable(equipmentDb, i);
    }
}
