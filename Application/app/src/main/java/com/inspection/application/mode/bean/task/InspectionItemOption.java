package com.inspection.application.mode.bean.task;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangan on 2017-07-10.
 */

public class InspectionItemOption implements Parcelable {

    private long createTime;
    private int deleteState;
    private long deleteTime;
    private long id;
    private String optionName;
    private int isAlarm;

    public int getIsAlarm() {
        return isAlarm;
    }

    public void setIsAlarm(int isAlarm) {
        this.isAlarm = isAlarm;
    }

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.createTime);
        dest.writeInt(this.deleteState);
        dest.writeLong(this.deleteTime);
        dest.writeLong(this.id);
        dest.writeString(this.optionName);
        dest.writeInt(this.isAlarm);
    }

    public InspectionItemOption() {
    }

    protected InspectionItemOption(Parcel in) {
        this.createTime = in.readLong();
        this.deleteState = in.readInt();
        this.deleteTime = in.readLong();
        this.id = in.readLong();
        this.optionName = in.readString();
        this.isAlarm = in.readInt();
    }

    public static final Creator<InspectionItemOption> CREATOR = new Creator<InspectionItemOption>() {
        @Override
        public InspectionItemOption createFromParcel(Parcel source) {
            return new InspectionItemOption(source);
        }

        @Override
        public InspectionItemOption[] newArray(int size) {
            return new InspectionItemOption[size];
        }
    };
}
