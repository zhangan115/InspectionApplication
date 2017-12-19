package com.inspection.application.mode.bean.task.data;

import java.util.List;

/**
 * 巡检数据对象
 * Created by zhangan on 2017/9/27.
 */

public class InspectionDataBean {

    private int count;
    private long taskId;
    private int taskState;
    private String taskName;
    private String userIds;
    private List<RoomListBean> roomList;

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTaskState() {
        return taskState;
    }

    public void setTaskState(int taskState) {
        this.taskState = taskState;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public List<RoomListBean> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<RoomListBean> roomList) {
        this.roomList = roomList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static class RoomListBean {

        private RoomBean room;
        private long startTime;
        private long taskRoomId;
        private int taskRoomState;
        private List<TaskEquipmentBean> taskEquipment;

        public RoomBean getRoom() {
            return room;
        }

        public void setRoom(RoomBean room) {
            this.room = room;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getTaskRoomId() {
            return taskRoomId;
        }

        public void setTaskRoomId(long taskRoomId) {
            this.taskRoomId = taskRoomId;
        }

        public int getTaskRoomState() {
            return taskRoomState;
        }

        public void setTaskRoomState(int taskRoomState) {
            this.taskRoomState = taskRoomState;
        }

        public List<TaskEquipmentBean> getTaskEquipment() {
            return taskEquipment;
        }

        public void setTaskEquipment(List<TaskEquipmentBean> taskEquipment) {
            this.taskEquipment = taskEquipment;
        }

        public static class RoomBean {

            private long roomId;
            private String roomName;
            private String roomRemark;

            public long getRoomId() {
                return roomId;
            }

            public void setRoomId(long roomId) {
                this.roomId = roomId;
            }

            public String getRoomName() {
                return roomName;
            }

            public void setRoomName(String roomName) {
                this.roomName = roomName;
            }

            public String getRoomRemark() {
                return roomRemark;
            }

            public void setRoomRemark(String roomRemark) {
                this.roomRemark = roomRemark;
            }
        }

        public static class TaskEquipmentBean {

            private EquipmentBean equipment;
            private long taskEquipmentId;
            private int taskEquipmentState;
            private List<DataListBean> dataList;

            public EquipmentBean getEquipment() {
                return equipment;
            }

            public void setEquipment(EquipmentBean equipment) {
                this.equipment = equipment;
            }

            public long getTaskEquipmentId() {
                return taskEquipmentId;
            }

            public void setTaskEquipmentId(long taskEquipmentId) {
                this.taskEquipmentId = taskEquipmentId;
            }

            public int getTaskEquipmentState() {
                return taskEquipmentState;
            }

            public void setTaskEquipmentState(int taskEquipmentState) {
                this.taskEquipmentState = taskEquipmentState;
            }

            public List<DataListBean> getDataList() {
                return dataList;
            }

            public void setDataList(List<DataListBean> dataList) {
                this.dataList = dataList;
            }

            public static class EquipmentBean {

                private int equipmentId;
                private String equipmentName;
                private String equipmentNumber;
                private List<InPlacePic> inPlacePicList;

                public List<InPlacePic> getInPlacePicList() {
                    return inPlacePicList;
                }

                public void setInPlacePicList(List<InPlacePic> inPlacePicList) {
                    this.inPlacePicList = inPlacePicList;
                }

                public int getEquipmentId() {
                    return equipmentId;
                }

                public void setEquipmentId(int equipmentId) {
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
                public static class InPlacePic {


                    private long createTime;
                    private long inplaceId;
                    private String inplacePicUrl;

                    public long getCreateTime() {
                        return createTime;
                    }

                    public void setCreateTime(long createTime) {
                        this.createTime = createTime;
                    }

                    public long getInplaceId() {
                        return inplaceId;
                    }

                    public void setInplaceId(long inplaceId) {
                        this.inplaceId = inplaceId;
                    }

                    public String getInplacePicUrl() {
                        return inplacePicUrl;
                    }

                    public void setInplacePicUrl(String inplacePicUrl) {
                        this.inplacePicUrl = inplacePicUrl;
                    }
                }
            }

            public static class DataListBean {

                private long commitTime;
                private long dataId;
                private List<DataItemValueListBean> dataItemValueList;

                public long getCommitTime() {
                    return commitTime;
                }

                public void setCommitTime(long commitTime) {
                    this.commitTime = commitTime;
                }

                public long getDataId() {
                    return dataId;
                }

                public void setDataId(long dataId) {
                    this.dataId = dataId;
                }

                public List<DataItemValueListBean> getDataItemValueList() {
                    return dataItemValueList;
                }

                public void setDataItemValueList(List<DataItemValueListBean> dataItemValueList) {
                    this.dataItemValueList = dataItemValueList;
                }

                public static class DataItemValueListBean {

                    private long commitTime;
                    private DataItemBean dataItem;
                    private long dataItemValueId;
                    private String value;

                    public long getCommitTime() {
                        return commitTime;
                    }

                    public void setCommitTime(long commitTime) {
                        this.commitTime = commitTime;
                    }

                    public DataItemBean getDataItem() {
                        return dataItem;
                    }

                    public void setDataItem(DataItemBean dataItem) {
                        this.dataItem = dataItem;
                    }

                    public long getDataItemValueId() {
                        return dataItemValueId;
                    }

                    public void setDataItemValueId(long dataItemValueId) {
                        this.dataItemValueId = dataItemValueId;
                    }

                    public String getValue() {
                        return value;
                    }

                    public void setValue(String value) {
                        this.value = value;
                    }

                    public static class DataItemBean {

                        private int deleteState;
                        private long inspectionId;
                        private String inspectionName;
                        private int inspectionType;

                        public int getDeleteState() {
                            return deleteState;
                        }

                        public void setDeleteState(int deleteState) {
                            this.deleteState = deleteState;
                        }

                        public long getInspectionId() {
                            return inspectionId;
                        }

                        public void setInspectionId(long inspectionId) {
                            this.inspectionId = inspectionId;
                        }

                        public String getInspectionName() {
                            return inspectionName;
                        }

                        public void setInspectionName(String inspectionName) {
                            this.inspectionName = inspectionName;
                        }

                        public int getInspectionType() {
                            return inspectionType;
                        }

                        public void setInspectionType(int inspectionType) {
                            this.inspectionType = inspectionType;
                        }
                    }
                }
            }
        }
    }
}
