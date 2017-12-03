package com.inspection.application.mode.bean.equipment;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 配电室列表
 */
public class EquipBean extends RoomListBean implements Parcelable {

    private List<EquipmentBean> equipments;

    public List<EquipmentBean> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<EquipmentBean> equipments) {
        this.equipments = equipments;
    }

    protected EquipBean(Parcel in) {
        super(in);
    }
}
