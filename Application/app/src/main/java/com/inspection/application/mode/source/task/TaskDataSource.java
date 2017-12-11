package com.inspection.application.mode.source.task;

import android.support.annotation.NonNull;

import com.inspection.application.mode.bean.secure.SecureBean;
import com.inspection.application.mode.bean.task.InspectionBean;
import com.inspection.application.mode.bean.task.InspectionDetailBean;
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
     * @param taskId          任务id
     * @param callBack 回调
     * @return 订阅
     */
    @NonNull
    Subscription getTaskInfo(long taskId, IObjectCallBack<InspectionDetailBean> callBack);

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
}
