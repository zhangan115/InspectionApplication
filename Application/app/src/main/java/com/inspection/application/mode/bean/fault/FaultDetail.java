package com.inspection.application.mode.bean.fault;


import com.inspection.application.mode.bean.equipment.EquipmentBean;
import com.inspection.application.mode.bean.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangan on 2017-07-18.
 */

public class FaultDetail {

    private long closeTime;
    private long createTime;
    private CustomerBean customer;
    private EquipmentBean equipment;
    private String faultDescript;
    private long faultId;
    private int faultState;
    private int faultType;
    private RepairBean repair;
    private UserBeanX user;
    private List<FaultFlowsBean> faultFlows;
    private List<FaultPicsBean> faultPics;
    private Long defaultFlowId;
    private DefaultFlowBean defaultFlow;
    private int currentFlowIndex;
    private ArrayList<User> usersN;

    public Long getDefaultFlowId() {
        return defaultFlowId;
    }

    public int getCurrentFlowIndex() {
        return currentFlowIndex;
    }

    public void setCurrentFlowIndex(int currentFlowIndex) {
        this.currentFlowIndex = currentFlowIndex;
    }

    public void setDefaultFlowId(Long defaultFlowId) {
        this.defaultFlowId = defaultFlowId;
    }

    public DefaultFlowBean getDefaultFlow() {
        return defaultFlow;
    }

    public void setDefaultFlow(DefaultFlowBean defaultFlow) {
        this.defaultFlow = defaultFlow;
    }

    public ArrayList<User> getUsersN() {
        return usersN;
    }

    public void setUsersN(ArrayList<User> usersN) {
        this.usersN = usersN;
    }

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

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
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

    public RepairBean getRepair() {
        return repair;
    }

    public void setRepair(RepairBean repair) {
        this.repair = repair;
    }

    public UserBeanX getUser() {
        return user;
    }

    public void setUser(UserBeanX user) {
        this.user = user;
    }

    public List<FaultFlowsBean> getFaultFlows() {
        return faultFlows;
    }

    public void setFaultFlows(List<FaultFlowsBean> faultFlows) {
        this.faultFlows = faultFlows;
    }

    public List<FaultPicsBean> getFaultPics() {
        return faultPics;
    }

    public void setFaultPics(List<FaultPicsBean> faultPics) {
        this.faultPics = faultPics;
    }

    public static class CustomerBean {

        private String contractTime;
        private long createTime;
        private String customerAddress;
        private int customerId;
        private String customerImage;
        private String customerLinkman;
        private String customerName;
        private String customerNumber;
        private String customerPhone;
        private String customerRemark;
        private int deleteState;
        private String inTime;

        public String getContractTime() {
            return contractTime;
        }

        public void setContractTime(String contractTime) {
            this.contractTime = contractTime;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getCustomerAddress() {
            return customerAddress;
        }

        public void setCustomerAddress(String customerAddress) {
            this.customerAddress = customerAddress;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public String getCustomerImage() {
            return customerImage;
        }

        public void setCustomerImage(String customerImage) {
            this.customerImage = customerImage;
        }

        public String getCustomerLinkman() {
            return customerLinkman;
        }

        public void setCustomerLinkman(String customerLinkman) {
            this.customerLinkman = customerLinkman;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerNumber() {
            return customerNumber;
        }

        public void setCustomerNumber(String customerNumber) {
            this.customerNumber = customerNumber;
        }

        public String getCustomerPhone() {
            return customerPhone;
        }

        public void setCustomerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
        }

        public String getCustomerRemark() {
            return customerRemark;
        }

        public void setCustomerRemark(String customerRemark) {
            this.customerRemark = customerRemark;
        }

        public int getDeleteState() {
            return deleteState;
        }

        public void setDeleteState(int deleteState) {
            this.deleteState = deleteState;
        }

        public String getInTime() {
            return inTime;
        }

        public void setInTime(String inTime) {
            this.inTime = inTime;
        }
    }


    public static class RepairBean {

        private long commitTime;
        private long createTime;
        private long endTime;
        private long repairId;
        private String repairIntro;
        private String repairName;
        private String repairRemark;
        private int repairResult;
        private int repairState;
        private long startTime;
        private UserBean user;
        private int soundTimescale;
        private UserExecuteBean userExecute;
        private String voiceUrl;
        private List<RepairPicsBean> repairPics;

        public int getSoundTimescale() {
            return soundTimescale;
        }

        public List<RepairPicsBean> getRepairPics() {
            return repairPics;
        }

        public void setRepairPics(List<RepairPicsBean> repairPics) {
            this.repairPics = repairPics;
        }

        public void setSoundTimescale(int soundTimescale) {
            this.soundTimescale = soundTimescale;
        }

        public long getCommitTime() {
            return commitTime;
        }

        public void setCommitTime(long commitTime) {
            this.commitTime = commitTime;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public long getRepairId() {
            return repairId;
        }

        public void setRepairId(long repairId) {
            this.repairId = repairId;
        }

        public String getRepairIntro() {
            return repairIntro;
        }

        public void setRepairIntro(String repairIntro) {
            this.repairIntro = repairIntro;
        }

        public String getRepairName() {
            return repairName;
        }

        public void setRepairName(String repairName) {
            this.repairName = repairName;
        }

        public String getRepairRemark() {
            return repairRemark;
        }

        public void setRepairRemark(String repairRemark) {
            this.repairRemark = repairRemark;
        }

        public int getRepairResult() {
            return repairResult;
        }

        public void setRepairResult(int repairResult) {
            this.repairResult = repairResult;
        }

        public int getRepairState() {
            return repairState;
        }

        public void setRepairState(int repairState) {
            this.repairState = repairState;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public UserExecuteBean getUserExecute() {
            return userExecute;
        }

        public void setUserExecute(UserExecuteBean userExecute) {
            this.userExecute = userExecute;
        }

        public String getVoiceUrl() {
            return voiceUrl;
        }

        public void setVoiceUrl(String voiceUrl) {
            this.voiceUrl = voiceUrl;
        }

        public static class UserBean {

            private long createTime;
            private int deleteState;
            private long joinTime;
            private String realName;
            private long userId;
            private String userName;
            private String userPhone;
            private int userType;

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getDeleteState() {
                return deleteState;
            }

            public void setDeleteState(int deleteState) {
                this.deleteState = deleteState;
            }

            public long getJoinTime() {
                return joinTime;
            }

            public void setJoinTime(long joinTime) {
                this.joinTime = joinTime;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserPhone() {
                return userPhone;
            }

            public void setUserPhone(String userPhone) {
                this.userPhone = userPhone;
            }

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }
        }


        public static class RepairPicsBean {

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

        public static class UserExecuteBean {

            private long createTime;
            private int deleteState;
            private long joinTime;
            private String realName;
            private int userId;
            private String userName;
            private String userPhone;
            private int userType;

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getDeleteState() {
                return deleteState;
            }

            public void setDeleteState(int deleteState) {
                this.deleteState = deleteState;
            }

            public long getJoinTime() {
                return joinTime;
            }

            public void setJoinTime(long joinTime) {
                this.joinTime = joinTime;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserPhone() {
                return userPhone;
            }

            public void setUserPhone(String userPhone) {
                this.userPhone = userPhone;
            }

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }
        }
    }

    public static class UserBeanX {

        private long createTime;
        private int deleteState;
        private long joinTime;
        private String realName;
        private long userId;
        private String userName;
        private String userPhone;
        private String userTelephone;
        private int userType;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getDeleteState() {
            return deleteState;
        }

        public void setDeleteState(int deleteState) {
            this.deleteState = deleteState;
        }

        public long getJoinTime() {
            return joinTime;
        }

        public void setJoinTime(long joinTime) {
            this.joinTime = joinTime;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getUserTelephone() {
            return userTelephone;
        }

        public void setUserTelephone(String userTelephone) {
            this.userTelephone = userTelephone;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }
    }

    public static class FaultFlowsBean {

        private long id;
        private long flowTime;
        private int executeState;//0 待执行 1 已执行
        private int faultState;//1 发现 2指派 3关闭 4 检修
        private String flowRemark;
        private String usersNext;
        private UserBeanXX user;
        private CustomerBeanX customer;
        private UserExecuteBeanX userExecute;
        private List<UsersNBean> usersN;

        public CustomerBeanX getCustomer() {
            return customer;
        }

        public void setCustomer(CustomerBeanX customer) {
            this.customer = customer;
        }

        public int getExecuteState() {
            return executeState;
        }

        public void setExecuteState(int executeState) {
            this.executeState = executeState;
        }

        public int getFaultState() {
            return faultState;
        }

        public void setFaultState(int faultState) {
            this.faultState = faultState;
        }

        public String getFlowRemark() {
            return flowRemark;
        }

        public void setFlowRemark(String flowRemark) {
            this.flowRemark = flowRemark;
        }

        public long getFlowTime() {
            return flowTime;
        }

        public void setFlowTime(long flowTime) {
            this.flowTime = flowTime;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public UserBeanXX getUser() {
            return user;
        }

        public void setUser(UserBeanXX user) {
            this.user = user;
        }

        public UserExecuteBeanX getUserExecute() {
            return userExecute;
        }

        public void setUserExecute(UserExecuteBeanX userExecute) {
            this.userExecute = userExecute;
        }

        public String getUsersNext() {
            return usersNext;
        }

        public void setUsersNext(String usersNext) {
            this.usersNext = usersNext;
        }

        public List<UsersNBean> getUsersN() {
            return usersN;
        }

        public void setUsersN(List<UsersNBean> usersN) {
            this.usersN = usersN;
        }

        public static class CustomerBeanX {
            /**
             * contractTime : 2017/06/01 10:13:10
             * createTime : 1496653812000
             * customerAddress : 汇鑫IBC
             * customerId : 1
             * customerImage : --
             * customerLinkman : abc
             * customerName : 西拓电气股份有限公司
             * customerNumber : 111111
             * customerPhone : 123456789
             * customerRemark : 西拓电气
             * deleteState : 0
             * inTime : 2017/06/05 17:10:13
             */

            private String contractTime;
            private long createTime;
            private String customerAddress;
            private int customerId;
            private String customerImage;
            private String customerLinkman;
            private String customerName;
            private String customerNumber;
            private String customerPhone;
            private String customerRemark;
            private int deleteState;
            private String inTime;

            public String getContractTime() {
                return contractTime;
            }

            public void setContractTime(String contractTime) {
                this.contractTime = contractTime;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getCustomerAddress() {
                return customerAddress;
            }

            public void setCustomerAddress(String customerAddress) {
                this.customerAddress = customerAddress;
            }

            public int getCustomerId() {
                return customerId;
            }

            public void setCustomerId(int customerId) {
                this.customerId = customerId;
            }

            public String getCustomerImage() {
                return customerImage;
            }

            public void setCustomerImage(String customerImage) {
                this.customerImage = customerImage;
            }

            public String getCustomerLinkman() {
                return customerLinkman;
            }

            public void setCustomerLinkman(String customerLinkman) {
                this.customerLinkman = customerLinkman;
            }

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public String getCustomerNumber() {
                return customerNumber;
            }

            public void setCustomerNumber(String customerNumber) {
                this.customerNumber = customerNumber;
            }

            public String getCustomerPhone() {
                return customerPhone;
            }

            public void setCustomerPhone(String customerPhone) {
                this.customerPhone = customerPhone;
            }

            public String getCustomerRemark() {
                return customerRemark;
            }

            public void setCustomerRemark(String customerRemark) {
                this.customerRemark = customerRemark;
            }

            public int getDeleteState() {
                return deleteState;
            }

            public void setDeleteState(int deleteState) {
                this.deleteState = deleteState;
            }

            public String getInTime() {
                return inTime;
            }

            public void setInTime(String inTime) {
                this.inTime = inTime;
            }
        }

        public static class UserBeanXX {
            /**
             * createTime : 1499823981000
             * deleteState : 0
             * joinTime : 1499823981000
             * realName : 闫鑫
             * userId : 33
             * userName : yanxin
             * userPhone : 123456
             * userTelephone : 运行一班-班长
             * userType : 2
             */

            private long createTime;
            private int deleteState;
            private long joinTime;
            private String realName;
            private int userId;
            private String userName;
            private String userPhone;
            private String userTelephone;
            private int userType;

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getDeleteState() {
                return deleteState;
            }

            public void setDeleteState(int deleteState) {
                this.deleteState = deleteState;
            }

            public long getJoinTime() {
                return joinTime;
            }

            public void setJoinTime(long joinTime) {
                this.joinTime = joinTime;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserPhone() {
                return userPhone;
            }

            public void setUserPhone(String userPhone) {
                this.userPhone = userPhone;
            }

            public String getUserTelephone() {
                return userTelephone;
            }

            public void setUserTelephone(String userTelephone) {
                this.userTelephone = userTelephone;
            }

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }
        }

        public static class UserExecuteBeanX {
            /**
             * createTime : 1499825268000
             * deleteState : 0
             * joinTime : 1499825268000
             * realName : 梁宁
             * userId : 65
             * userName : liangning
             * userPhone : 123
             * userType : 2
             */

            private long createTime;
            private int deleteState;
            private long joinTime;
            private String realName;
            private int userId;
            private String userName;
            private String userPhone;
            private int userType;

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getDeleteState() {
                return deleteState;
            }

            public void setDeleteState(int deleteState) {
                this.deleteState = deleteState;
            }

            public long getJoinTime() {
                return joinTime;
            }

            public void setJoinTime(long joinTime) {
                this.joinTime = joinTime;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserPhone() {
                return userPhone;
            }

            public void setUserPhone(String userPhone) {
                this.userPhone = userPhone;
            }

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }
        }

        public static class UsersNBean {
            /**
             * createTime : 1499825268000
             * deleteState : 0
             * joinTime : 1499825268000
             * realName : 梁宁
             * userId : 65
             * userName : liangning
             * userPhone : 123
             * userType : 2
             */

            private long createTime;
            private int deleteState;
            private long joinTime;
            private String realName;
            private long userId;
            private String userName;
            private String userPhone;
            private int userType;

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getDeleteState() {
                return deleteState;
            }

            public void setDeleteState(int deleteState) {
                this.deleteState = deleteState;
            }

            public long getJoinTime() {
                return joinTime;
            }

            public void setJoinTime(long joinTime) {
                this.joinTime = joinTime;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserPhone() {
                return userPhone;
            }

            public void setUserPhone(String userPhone) {
                this.userPhone = userPhone;
            }

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }
        }
    }

    public static class FaultPicsBean {
        /**
         * id : 102
         * picUrl : http://192.168.8.250:8088/sitopeuv/file/1/fault/image/15003612563591500361133932718.jpg
         */

        private int id;
        private String picUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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
