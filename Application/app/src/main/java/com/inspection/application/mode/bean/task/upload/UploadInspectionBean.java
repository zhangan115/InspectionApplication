package com.inspection.application.mode.bean.task.upload;

/**
 * Created by zhangan on 2017-07-10.
 */

public class UploadInspectionBean {

    private UploadTaskInfo taskInfo;

    public UploadInspectionBean() {
    }

    public UploadInspectionBean(UploadTaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    public UploadTaskInfo getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(UploadTaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }
}
