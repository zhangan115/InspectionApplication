package com.inspection.application.mode.source.task;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.mode.api.ApiCallBackObject;
import com.inspection.application.mode.bean.equipment.EquipmentBean;
import com.inspection.application.mode.bean.secure.SecureBean;
import com.inspection.application.mode.bean.task.AlarmList;
import com.inspection.application.mode.bean.task.DataItemValueListBean;
import com.inspection.application.mode.bean.task.InspectionBean;
import com.inspection.application.mode.bean.task.InspectionDetailBean;
import com.inspection.application.mode.bean.task.RoomListBean;
import com.inspection.application.mode.bean.task.TaskEquipmentBean;
import com.inspection.application.mode.bean.task.data.CheckBean;
import com.inspection.application.mode.bean.task.data.InspectionDataBean;
import com.inspection.application.mode.bean.task.upload.UploadTaskInfo;
import com.inspection.application.mode.callback.IListCallBack;
import com.inspection.application.mode.callback.IObjectCallBack;

import rx.Subscription;

/**
 * 任务类
 * Created by pingan on 2017/12/9.
 */

public interface TaskDataSource {

    /**
     * 获取安全包
     *
     * @param securityId 安全包id
     * @param callBack   回调
     * @return 订阅
     */
    @NonNull
    Subscription getSecureInfo(long securityId, @NonNull final IObjectCallBack<SecureBean> callBack);

    /**
     * 获取任务
     *
     * @param date     日期
     * @param callBack 回调
     * @return 订阅
     */
    @NonNull
    Subscription getTaskList(String date, @NonNull final IListCallBack<InspectionBean> callBack);

    /**
     * 获取任务
     *
     * @param lastId   lastId
     * @param callBack 回调
     * @return 订阅
     */
    @NonNull
    Subscription getMyTaskList(@Nullable Long lastId, @NonNull final IListCallBack<InspectionBean> callBack);

    /**
     * 领取任务
     *
     * @param position 位置
     * @param taskId   任务id
     * @param callBack 回调
     * @return 订阅
     */
    @NonNull
    Subscription getTask(int position, long taskId, @NonNull final GetTaskCallBack callBack);

    /**
     * 获取任务详情
     *
     * @param taskId   任务id
     * @param callBack 回调
     * @return 订阅
     */
    @NonNull
    Subscription getTaskInfo(long taskId, IObjectCallBack<InspectionDetailBean> callBack);

    interface ILoadEquipmentDataCallBack {
        void success();

        void onFail();
    }

    /**
     * 从数据库获取设备数据
     *
     * @param taskEquipmentBean 设备信息
     * @param callBack          回调
     * @return 订阅
     */
    @NonNull
    Subscription loadTaskEquipData(final long taskId, final long roomId, TaskEquipmentBean taskEquipmentBean, ILoadEquipmentDataCallBack callBack);

    void loadTaskEquipData(final long taskId, RoomListBean roomListBean, ILoadEquipmentDataCallBack callBack);

    interface GetTaskCallBack {

        void onSuccess(int position);

        void onFail(String message);

        void onFinish();
    }

    interface CheckSecureInfoCallBack {

        void onShowTask(int position);

        void onShowSecure(int position);
    }

    /**
     * 查看安全包信息
     * <p>
     *
     * @param position 位置
     * @param taskId   taskId
     * @param callBack 回调
     */
    void checkSecure(int position, long taskId, @NonNull CheckSecureInfoCallBack callBack);

    /**
     * 获取任务的头部数据
     *
     * @param taskId   taskId
     * @param callBack 回调
     * @return 订阅
     */
    @NonNull
    Subscription getCheckData(long taskId, @NonNull IObjectCallBack<CheckBean> callBack);

    /**
     * 获取任务数据
     *
     * @param taskId   taskId
     * @param callBack 回调
     * @return 订阅
     */
    @NonNull
    Subscription getTaskData(long taskId, @NonNull IObjectCallBack<InspectionDataBean> callBack);

    /**
     * 开始一个任务
     *
     * @param roomListBean 数据
     * @param taskId       taskId
     * @return 订阅
     */
    @NonNull
    Subscription startTask(RoomListBean roomListBean, long taskId, IStartTaskCallBack callBack);

    interface IStartTaskCallBack {

        void onSuccess(RoomListBean roomListBean);

        void onFail(String message);
    }


    void checkTaskFinish(RoomListBean roomListBean, long taskId, ICheckTaskCallBack callBack);

    interface ICheckTaskCallBack {

        void onFinish(RoomListBean roomListBean);

        void onNotFinish(RoomListBean roomListBean);
    }

    interface IUploadPhotoCallBack {

        void onSuccess();

        void onFail();
    }

    /**
     * 上传照片
     *
     * @param dataItemValueListBean 数据
     * @param callBack              回调
     * @return 订阅
     */
    @NonNull
    Subscription uploadTaskPhoto(DataItemValueListBean dataItemValueListBean, IUploadPhotoCallBack callBack);

    /**
     * 删除照片数据
     *
     * @param dataItemValueListBean 数据
     */
    void deletePhoto(DataItemValueListBean dataItemValueListBean);

    interface IUploadTaskDataCallBack {

        void onSuccess();

        void onFail();

    }

    /**
     * 上传任务数据
     *
     * @param roomListBean 完成
     * @param callBack     回调
     * @param position     设备位置
     * @return 完成
     */
    @NonNull
    Subscription uploadTaskData(@NonNull UploadTaskInfo uploadTaskInfo, RoomListBean roomListBean, int position, @NonNull IUploadTaskDataCallBack callBack);

    interface IFinishTaskDataCallBack {

        void onSuccess();

        void onFail(String message);

    }

    /**
     * 完成任务
     *
     * @param roomListBean 数据
     * @param callBack     回调
     * @return 订阅
     */
    @NonNull
    Subscription finishTaskData(@NonNull UploadTaskInfo uploadTaskInfo, RoomListBean roomListBean, @NonNull IFinishTaskDataCallBack callBack);

    @NonNull
    Subscription getAlarmList(String info, @NonNull IListCallBack<AlarmList> callBack);

    @NonNull
    Subscription finishAllTask(long taskId, String userIds, IObjectCallBack<String> callBack);

    @Nullable
    EquipmentBean getEquipFromCache();

    /**
     * 保存设备到缓存中
     *
     * @param equipment 设备
     */
    void saveEquipToCache(EquipmentBean equipment);

    /**
     * 保存配电室信息
     *
     * @param roomListBean 配电室
     */
    void saveRoomData(RoomListBean roomListBean, long taskId);

    /**
     * 从缓存获取配电室信息
     *
     * @return 配电室
     */
    @Nullable
    RoomListBean getRoomDataFromCache();
}
