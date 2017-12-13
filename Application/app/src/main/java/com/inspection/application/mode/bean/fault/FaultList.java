package com.inspection.application.mode.bean.fault;


import com.inspection.application.mode.bean.equipment.EquipmentBean;
import com.inspection.application.mode.bean.user.User;

import java.util.List;

/**
 * 缺陷描述
 */
public class FaultList {

    private long closeTime;
    private long createTime;
    private EquipmentBean equipment;
    private String faultDescript;
    private long faultId;
    private int faultState;
    private int faultType;
    private User user;
    private List<FaultPicsBean> faultPics;

    public long getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(long closeTime) {
        this.closeTime = closeTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public EquipmentBean getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentBean equipment) {
        this.equipment = equipment;
    }

    public String getFaultDescript() {
        return faultDescript;
    }

    public void setFaultDescript(String faultDescript) {
        this.faultDescript = faultDescript;
    }

    public long getFaultId() {
        return faultId;
    }

    public void setFaultId(long faultId) {
        this.faultId = faultId;
    }

    public int getFaultState() {
        return faultState;
    }

    public void setFaultState(int faultState) {
        this.faultState = faultState;
    }

    public int getFaultType() {
        return faultType;
    }

    public void setFaultType(int faultType) {
        this.faultType = faultType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<FaultPicsBean> getFaultPics() {
        return faultPics;
    }

    public void setFaultPics(List<FaultPicsBean> faultPics) {
        this.faultPics = faultPics;
    }

    public static class FaultPicsBean {

        private long id;
        private String picUrl;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }
    }
}
