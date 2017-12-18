package com.inspection.application.mode.bean.equipment;

/**
 * Created by Yangzb on 2017/7/19 16:49
 * E-mail：yangzongbin@si-top.com
 */
public class CheckData {

    /**
     * inspectionId : 10
     * quantityUplimit : 24.5
     * inspectionType : 2
     * quantityUnit : ℃
     * inspectionName : 温度
     * value : 100
     * quantityLowlimit : 11.7
     */

    private int inspectionId;
    private double quantityUplimit;
    private int inspectionType;
    private String quantityUnit;
    private String inspectionName;
    private String value;
    private double quantityLowlimit;
    private String[] photos;

    public String[] getPhotos() {
        return photos;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }

    public int getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(int inspectionId) {
        this.inspectionId = inspectionId;
    }

    public double getQuantityUplimit() {
        return quantityUplimit;
    }

    public void setQuantityUplimit(double quantityUplimit) {
        this.quantityUplimit = quantityUplimit;
    }

    public int getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(int inspectionType) {
        this.inspectionType = inspectionType;
    }

    public String getQuantityUnit() {
        return quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public String getInspectionName() {
        return inspectionName;
    }

    public void setInspectionName(String inspectionName) {
        this.inspectionName = inspectionName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getQuantityLowlimit() {
        return quantityLowlimit;
    }

    public void setQuantityLowlimit(double quantityLowlimit) {
        this.quantityLowlimit = quantityLowlimit;
    }
}
