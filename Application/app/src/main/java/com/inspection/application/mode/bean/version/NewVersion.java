package com.inspection.application.mode.bean.version;

/**
 * 新版本
 * Created by zhangan on 2017-05-02.
 */

public class NewVersion {

    private int flag;
    private int id;
    private int intervalTime;
    private String note;
    private boolean openGetData;
    private String time;
    private String url;
    private int version;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isOpenGetData() {
        return openGetData;
    }

    public void setOpenGetData(boolean openGetData) {
        this.openGetData = openGetData;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
