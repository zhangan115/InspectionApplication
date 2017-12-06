package com.inspection.application.mode.source.fault;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.mode.bean.image.Image;
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

    void saveCache();

}
