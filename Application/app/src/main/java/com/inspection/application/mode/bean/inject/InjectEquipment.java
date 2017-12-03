package com.inspection.application.mode.bean.inject;

import android.support.annotation.NonNull;

/**
 * 注油管理
 * Created by zhangan on 2017/8/29.
 */

public class InjectEquipment implements Comparable<InjectEquipment> {

    private long equipmentId;
    private long createTime;
    private int cycle;
    private int deleteState;
    private String equipmentName;
    private String equipmentNumber;
    private String equipmentSn;
    private InjectionOilBean injectionOil;
    private boolean isInject;
    private long time;

    public String getEquipmentSn() {
        return equipmentSn;
    }

    public void setEquipmentSn(String equipmentSn) {
        this.equipmentSn = equipmentSn;
    }

    public boolean isInject() {
        return isInject;
    }

    public void setInject(boolean inject) {
        isInject = inject;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
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

    public InjectionOilBean getInjectionOil() {
        return injectionOil;
    }

    public void setInjectionOil(InjectionOilBean injectionOil) {
        this.injectionOil = injectionOil;
    }


    @Override
    public int compareTo(@NonNull InjectEquipment o) {
        long i = o.getTime() - this.time;
        if (i == 0) {
            return 0;
        } else if (i > 0) {
            return 1;
        } else {
            return -1;
        }
    }

    public static class InjectionOilBean {

        private long createTime;
        private long id;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }
}
