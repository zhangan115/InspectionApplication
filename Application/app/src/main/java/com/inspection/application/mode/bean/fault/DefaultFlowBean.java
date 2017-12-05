package com.inspection.application.mode.bean.fault;


import com.inspection.application.mode.bean.user.CustomerBean;
import com.inspection.application.mode.bean.user.User;

import java.util.List;

/**
 * 预设流程
 * Created by zhangan on 2017/8/30.
 */

public class DefaultFlowBean {

    private long createTime;
    private long customerId;
    private long defaultFlowId;
    private String defaultFlowName;
    private int defaultFlowNo;
    private int deleteState;
    private DeptBean dept;
    private long deptId;
    private List<User> usersN;
    private CustomerBean customer;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getDefaultFlowId() {
        return defaultFlowId;
    }

    public void setDefaultFlowId(long defaultFlowId) {
        this.defaultFlowId = defaultFlowId;
    }

    public String getDefaultFlowName() {
        return defaultFlowName;
    }

    public void setDefaultFlowName(String defaultFlowName) {
        this.defaultFlowName = defaultFlowName;
    }

    public int getDefaultFlowNo() {
        return defaultFlowNo;
    }

    public void setDefaultFlowNo(int defaultFlowNo) {
        this.defaultFlowNo = defaultFlowNo;
    }

    public int getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(int deleteState) {
        this.deleteState = deleteState;
    }

    public DeptBean getDept() {
        return dept;
    }

    public void setDept(DeptBean dept) {
        this.dept = dept;
    }

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    public List<User> getUsersN() {
        return usersN;
    }

    public void setUsersN(List<User> usersN) {
        this.usersN = usersN;
    }

}
