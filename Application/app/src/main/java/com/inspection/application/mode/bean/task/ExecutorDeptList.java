package com.inspection.application.mode.bean.task;

import com.inspection.application.mode.bean.fault.DeptBean;

/**
 * Created by pingan on 2017/12/19.
 */

public class ExecutorDeptList {

    private long taskDeptId;
    private DeptBean dept;

    public long getTaskDeptId() {
        return taskDeptId;
    }

    public void setTaskDeptId(long taskDeptId) {
        this.taskDeptId = taskDeptId;
    }

    public DeptBean getDeptBean() {
        return dept;
    }

    public void setDeptBean(DeptBean deptBean) {
        this.dept = deptBean;
    }
}
