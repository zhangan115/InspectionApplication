package com.inspection.application.mode.bean.count;

/**
 * Created by Yangzb on 2017/7/18 11:57
 * E-mail：yangzongbin@si-top.com
 */
public class FaultLevel {

    /**
     * month : 2017-06
     * aFaultCount : 0
     * cFaultCount : 0
     * allFaultCount : 3
     * bFaultCount : 3
     */

    private String month;
    private int aFaultCount;
    private int cFaultCount;
    private int allFaultCount;
    private int bFaultCount;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getAFaultCount() {
        return aFaultCount;
    }

    public void setAFaultCount(int aFaultCount) {
        this.aFaultCount = aFaultCount;
    }

    public int getCFaultCount() {
        return cFaultCount;
    }

    public void setCFaultCount(int cFaultCount) {
        this.cFaultCount = cFaultCount;
    }

    public int getAllFaultCount() {
        return allFaultCount;
    }

    public void setAllFaultCount(int allFaultCount) {
        this.allFaultCount = allFaultCount;
    }

    public int getBFaultCount() {
        return bFaultCount;
    }

    public void setBFaultCount(int bFaultCount) {
        this.bFaultCount = bFaultCount;
    }
}
