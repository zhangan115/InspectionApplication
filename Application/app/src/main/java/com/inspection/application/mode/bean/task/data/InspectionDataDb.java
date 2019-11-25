package com.inspection.application.mode.bean.task.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "inspection_data")
public class InspectionDataDb {
    @Id(autoincrement = true)
    private Long _id;
    private long taskId; //任务id
    private String inspectionData;

    @Generated(hash = 1363971104)
    public InspectionDataDb(Long _id, long taskId, String inspectionData) {
        this._id = _id;
        this.taskId = taskId;
        this.inspectionData = inspectionData;
    }
    @Generated(hash = 87365648)
    public InspectionDataDb() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public long getTaskId() {
        return this.taskId;
    }
    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }
    public String getInspectionData() {
        return this.inspectionData;
    }
    public void setInspectionData(String inspectionData) {
        this.inspectionData = inspectionData;
    }
}
