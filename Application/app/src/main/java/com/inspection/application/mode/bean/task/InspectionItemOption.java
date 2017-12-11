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

    protected InspectionItemOption(Parcel in) {
        createTime = in.readLong();
        deleteState = in.readInt();
        deleteTime = in.readLong();
        id = in.readLong();
        optionName = in.readString();
    }

    public static final Creator<InspectionItemOption> CREATOR = new Creator<InspectionItemOption>() {
        @Override
        public InspectionItemOption createFromParcel(Parcel in) {
            return new InspectionItemOption(in);
        }

        @Override
        public InspectionItemOption[] newArray(int size) {
            return new InspectionItemOption[size];
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
        parcel.writeLong(deleteTime);
        parcel.writeLong(id);
        parcel.writeString(optionName);
    }
}
