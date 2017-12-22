package com.inspection.application.mode.source.fault;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.mode.bean.fault.DefaultFlowBean;
import com.inspection.application.mode.bean.fault.FaultDetail;
import com.inspection.application.mode.bean.fault.FaultList;
import com.inspection.application.mode.bean.image.Image;
import com.inspection.application.mode.callback.IListCallBack;
import com.inspection.application.mode.callback.IObjectCallBack;

import org.json.JSONObject;

import java.util.List;

import rx.Subscription;

/**
 * fault data source
 * Created by pingan on 2017/12/5.
 */

public interface FaultDataSource {

    interface LoadImageCallBack {

        void onSuccess(@NonNull List<Image> imageList);
    }


    @NonNull
    Subscription loadImageList(@Nullable String inspectionTag, @NonNull LoadImageCallBack callBack);

    interface UploadImageCallBack {
        /**
         * 上传成功
         */
        void onSuccess();

        /**
         * 上传失败
         *
         * @param image 照片
         */
        void onFail(Image image);
    }

    /**
     * 上传一张照片
     *
     * @param image    照片
     * @param callBack 回调
     */
    @NonNull
    Subscription uploadPhoto(Image image, @NonNull UploadImageCallBack callBack);

    /**
     * 上传异常数据
     *
     * @param jsonObject json数据
     * @param callBack   回调
     */
    @NonNull
    Subscription uploadFaultData(@NonNull JSONObject jsonObject, @NonNull IObjectCallBack<String> callBack);

    /**
     * 删除一张照片
     *
     * @param image 照片
     */
    void deleteImage(Image image);

    /**
     * 获取人员列表
     *
     * @param callBack 回调
     * @return 订阅
     */
    @NonNull
    Subscription getFlowUserList(@NonNull IListCallBack<DefaultFlowBean> callBack);

    /**
     * 获取故障列表
     *
     * @param info     参数
     * @param callBack 回调
     * @return 订阅
     */
    @NonNull
    Subscription getFaultList(@NonNull String info, @NonNull IListCallBack<FaultList> callBack);

    /**
     * 获取故障详情
     *
     * @param id       参数
     * @param callBack 回调
     * @return 订阅
     */
    @NonNull
    Subscription getFaultDetail(long id, @NonNull IObjectCallBack<FaultDetail> callBack);

    @NonNull
    Subscription closeFault(long faultId, String content, IObjectCallBack<String> callBack);

    @NonNull
    Subscription pointFault(String info, IObjectCallBack<String> callBack);

    @NonNull
    Subscription sureFault(long faultId, String flowRemark, IObjectCallBack<String> callBack);

    void deleteImages(List<Image> images);
}
