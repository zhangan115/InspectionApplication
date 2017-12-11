package com.inspection.application.mode.bean.task;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangan on 2017-07-10.
 */

public class DataItemValueListBean implements Parcelable {

    private int dataItemValueId;
    private String lastValue;
    private DataItemBean dataItem;

    public DataItemBean getDataItem() {
        return dataItem;
    }

    public void setDataItem(DataItemBean dataItem) {
        this.dataItem = dataItem;
    }

    public int getDataItemValueId() {
        return dataItemValueId;
    }

    public void setDataItemValueId(int dataItemValueId) {
        this.dataItemValueId = dataItemValueId;
    }

    public String getLastValue() {
        return lastValue;
    }

    public void setLastValue(String lastValue) {
        this.lastValue = lastValue;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.dataItemValueId);
        dest.writeString(this.lastValue);
        dest.writeParcelable(this.dataItem, flags);
    }

    public DataItemValueListBean() {
    }

    protected DataItemValueListBean(Parcel in) {
        this.dataItemValueId = in.readInt();
        this.lastValue = in.readString();
        this.dataItem = in.readParcelable(DataItemBean.class.getClassLoader());
    }

    public static final Creator<DataItemValueListBean> CREATOR = new Creator<DataItemValueListBean>() {
        @Override
        public DataItemValueListBean createFromParcel(Parcel source) {
            return new DataItemValueListBean(source);
        }

        @Override
        public DataItemValueListBean[] newArray(int size) {
            return new DataItemValueListBean[size];
        }
    };
}
