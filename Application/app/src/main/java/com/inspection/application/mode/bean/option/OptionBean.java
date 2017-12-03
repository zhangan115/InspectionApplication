package com.inspection.application.mode.bean.option;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 字典类
 */
public class OptionBean implements Parcelable {

    private long optionId;
    private String optionName;
    private List<ItemListBean> itemList;

    protected OptionBean(Parcel in) {
        optionId = in.readLong();
        optionName = in.readString();
        itemList = in.createTypedArrayList(ItemListBean.CREATOR);
    }

    public static final Creator<OptionBean> CREATOR = new Creator<OptionBean>() {
        @Override
        public OptionBean createFromParcel(Parcel in) {
            return new OptionBean(in);
        }

        @Override
        public OptionBean[] newArray(int size) {
            return new OptionBean[size];
        }
    };

    public long getOptionId() {
        return optionId;
    }

    public void setOptionId(long optionId) {
        this.optionId = optionId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public List<ItemListBean> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemListBean> itemList) {
        this.itemList = itemList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(optionId);
        parcel.writeString(optionName);
        parcel.writeTypedList(itemList);
    }

    public static class ItemListBean implements Parcelable {

        private long itemId;
        private int itemApply;
        private String itemCode;
        private String itemName;

        protected ItemListBean(Parcel in) {
            itemId = in.readLong();
            itemApply = in.readInt();
            itemCode = in.readString();
            itemName = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(itemId);
            dest.writeInt(itemApply);
            dest.writeString(itemCode);
            dest.writeString(itemName);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ItemListBean> CREATOR = new Creator<ItemListBean>() {
            @Override
            public ItemListBean createFromParcel(Parcel in) {
                return new ItemListBean(in);
            }

            @Override
            public ItemListBean[] newArray(int size) {
                return new ItemListBean[size];
            }
        };

        public long getItemId() {
            return itemId;
        }

        public void setItemId(long itemId) {
            this.itemId = itemId;
        }

        public int getItemApply() {
            return itemApply;
        }

        public void setItemApply(int itemApply) {
            this.itemApply = itemApply;
        }

        public String getItemCode() {
            return itemCode;
        }

        public void setItemCode(String itemCode) {
            this.itemCode = itemCode;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }
    }
}
