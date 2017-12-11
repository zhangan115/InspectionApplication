package com.inspection.application.mode.bean.task;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by zhangan on 2017-07-10.
 */

public class DataListBean implements Parcelable {
    private int dataId;
    private List<DataItemValueListBean> dataItemValueList;

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public List<DataItemValueListBean> getDataItemValueList() {
        return dataItemValueList;
    }

    public void setDataItemValueList(List<DataItemValueListBean> dataItemValueList) {
        this.dataItemValueList = dataItemValueList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.dataId);
        dest.writeTypedList(this.dataItemValueList);
    }

    public DataListBean() {
    }

    protected DataListBean(Parcel in) {
        this.dataId = in.readInt();
        this.dataItemValueList = in.createTypedArrayList(DataItemValueListBean.CREATOR);
    }

    public static final Creator<DataListBean> CREATOR = new Creator<DataListBean>() {
        @Override
        public DataListBean createFromParcel(Parcel source) {
            return new DataListBean(source);
        }

        @Override
        public DataListBean[] newArray(int size) {
            return new DataListBean[size];
        }
    };
}
