package com.inspection.application.mode.bean.task;

import android.os.Parcel;
import android.os.Parcelable;

import com.inspection.application.mode.bean.equipment.EquipmentBean;

import java.util.List;

/**
 * Created by zhangan on 2017-07-10.
 */

public class TaskEquipmentBean implements Parcelable {

    private int taskEquipmentId;
    private int taskEquipmentState;
    private EquipmentBean equipment;
    private List<DataListBean> dataList;
    private boolean isAlarm;//本地添加属性:判断当前设备是否异常上报过
    private boolean isTakePhoto;//本地添加属性:是否进行拍照
    private boolean isUpload;//本地添加属性:是否上传
    private boolean isChoose;

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public boolean isTakePhoto() {
        return isTakePhoto;
    }

    public boolean isUpload() {
        return isUpload;
    }

    public void setUpload(boolean upload) {
        isUpload = upload;
    }

    public void setTakePhoto(boolean takePhoto) {
        isTakePhoto = takePhoto;
    }

    public EquipmentBean getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentBean equipment) {
        this.equipment = equipment;
    }

    public int getTaskEquipmentId() {
        return taskEquipmentId;
    }

    public void setTaskEquipmentId(int taskEquipmentId) {
        this.taskEquipmentId = taskEquipmentId;
    }

    public int getTaskEquipmentState() {
        return taskEquipmentState;
    }

    public void setTaskEquipmentState(int taskEquipmentState) {
        this.taskEquipmentState = taskEquipmentState;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public TaskEquipmentBean() {
    }

    public boolean isAlarm() {
        return isAlarm;
    }

    public void setAlarm(boolean alarm) {
        isAlarm = alarm;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.taskEquipmentId);
        dest.writeInt(this.taskEquipmentState);
        dest.writeParcelable(this.equipment, flags);
        dest.writeTypedList(this.dataList);
        dest.writeByte(this.isAlarm ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isTakePhoto ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isUpload ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isChoose ? (byte) 1 : (byte) 0);
    }

    protected TaskEquipmentBean(Parcel in) {
        this.taskEquipmentId = in.readInt();
        this.taskEquipmentState = in.readInt();
        this.equipment = in.readParcelable(EquipmentBean.class.getClassLoader());
        this.dataList = in.createTypedArrayList(DataListBean.CREATOR);
        this.isAlarm = in.readByte() != 0;
        this.isTakePhoto = in.readByte() != 0;
        this.isUpload = in.readByte() != 0;
        this.isChoose = in.readByte() != 0;
    }

    public static final Creator<TaskEquipmentBean> CREATOR = new Creator<TaskEquipmentBean>() {
        @Override
        public TaskEquipmentBean createFromParcel(Parcel source) {
            return new TaskEquipmentBean(source);
        }

        @Override
        public TaskEquipmentBean[] newArray(int size) {
            return new TaskEquipmentBean[size];
        }
    };
}
