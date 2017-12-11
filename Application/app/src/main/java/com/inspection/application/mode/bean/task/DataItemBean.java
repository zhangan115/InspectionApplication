package com.inspection.application.mode.bean.task;

import android.os.Parcel;
import android.os.Parcelable;


import com.inspection.application.mode.bean.equipment.db.EquipmentDataDb;

import java.util.List;

/**
 * Created by zhangan on 2017-07-10.
 */

public class DataItemBean implements Parcelable {

    private long createTime;
    private int deleteState;
    private long deleteTime;
    private long inspectionId;
    private String inspectionName;
    private int inspectionType;
    private List<InspectionItemOption> inspectionItemOptionList;
    private String quantityLowlimit;
    private String quantityUplimit;
    private String quantityUnit;
    private String value;
    private String localFile;
    //本地添加的字段，进行逻辑处理
    private String chooseInspectionName;//本地添加属性：选择结果名称
    private EquipmentDataDb equipmentDataDb;//本地添加属性：保存的设备数据

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

    public long getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(long deleteTime) {
        this.deleteTime = deleteTime;
    }

    public long getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(long inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getInspectionName() {
        return inspectionName;
    }

    public void setInspectionName(String inspectionName) {
        this.inspectionName = inspectionName;
    }

    public int getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(int inspectionType) {
        this.inspectionType = inspectionType;
    }

    public List<InspectionItemOption> getInspectionItemOptionList() {
        return inspectionItemOptionList;
    }

    public void setInspectionItemOptionList(List<InspectionItemOption> inspectionItemOptionList) {
        this.inspectionItemOptionList = inspectionItemOptionList;
    }

    public String getQuantityLowlimit() {
        return quantityLowlimit;
    }

    public void setQuantityLowlimit(String quantityLowlimit) {
        this.quantityLowlimit = quantityLowlimit;
    }

    public String getQuantityUplimit() {
        return quantityUplimit;
    }

    public void setQuantityUplimit(String quantityUplimit) {
        this.quantityUplimit = quantityUplimit;
    }

    public String getQuantityUnit() {
        return quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLocalFile() {
        return localFile;
    }

    public void setLocalFile(String localFile) {
        this.localFile = localFile;
    }

    public String getChooseInspectionName() {
        return chooseInspectionName;
    }

    public void setChooseInspectionName(String chooseInspectionName) {
        this.chooseInspectionName = chooseInspectionName;
    }

    public void setEquipmentDataDb(EquipmentDataDb equipmentDataDb) {
        this.equipmentDataDb = equipmentDataDb;
    }

    protected DataItemBean(Parcel in) {
        createTime = in.readLong();
        deleteState = in.readInt();
        deleteTime = in.readLong();
        inspectionId = in.readLong();
        inspectionName = in.readString();
        inspectionType = in.readInt();
        inspectionItemOptionList = in.createTypedArrayList(InspectionItemOption.CREATOR);
        quantityLowlimit = in.readString();
        quantityUplimit = in.readString();
        quantityUnit = in.readString();
        value = in.readString();
        localFile = in.readString();
        chooseInspectionName = in.readString();
        equipmentDataDb = in.readParcelable(EquipmentDataDb.class.getClassLoader());
    }

    public static final Creator<DataItemBean> CREATOR = new Creator<DataItemBean>() {
        @Override
        public DataItemBean createFromParcel(Parcel in) {
            return new DataItemBean(in);
        }

        @Override
        public DataItemBean[] newArray(int size) {
            return new DataItemBean[size];
        }
    };

    public EquipmentDataDb getEquipmentDataDb() {
        return equipmentDataDb;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(createTime);
        parcel.writeInt(deleteState);
        parcel.writeLong(deleteTime);
        parcel.writeLong(inspectionId);
        parcel.writeString(inspectionName);
        parcel.writeInt(inspectionType);
        parcel.writeTypedList(inspectionItemOptionList);
        parcel.writeString(quantityLowlimit);
        parcel.writeString(quantityUplimit);
        parcel.writeString(quantityUnit);
        parcel.writeString(value);
        parcel.writeString(localFile);
        parcel.writeString(chooseInspectionName);
        parcel.writeParcelable(equipmentDataDb, i);
    }
}
