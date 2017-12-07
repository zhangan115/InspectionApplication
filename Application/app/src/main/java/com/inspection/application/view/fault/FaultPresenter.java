package com.inspection.application.view.fault;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.mode.bean.image.Image;
import com.inspection.application.mode.callback.IObjectCallBack;
import com.inspection.application.mode.source.fault.FaultDataSource;

import org.json.JSONObject;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

/**
 * 异常 p
 * Created by pingan on 2017/12/5.
 */

class FaultPresenter implements FaultContract.Presenter {

    private final FaultDataSource mFaultDataSource;
    private final FaultContract.View mView;
    private CompositeSubscription subscription;

    FaultPresenter(FaultDataSource mFaultDataSource, FaultContract.View mView) {
        this.mFaultDataSource = mFaultDataSource;
        this.mView = mView;
        mView.setPresenter(this);
        subscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        subscription.clear();
    }

    @Override
    public void getCacheFromDb(@Nullable String inspectionTag) {
        subscription.add(mFaultDataSource.loadImageList(inspectionTag, new FaultDataSource.LoadImageCallBack() {
            @Override
            public void onSuccess(@NonNull List<Image> imageList) {
                mView.showImageList(imageList);
            }
        }));
    }

    @Override
    public void uploadPhoto(@NonNull Image image) {
        subscription.add(mFaultDataSource.uploadPhoto(image, new FaultDataSource.UploadImageCallBack() {
            @Override
            public void onSuccess() {
                mView.uploadImageSuccess();
            }

            @Override
            public void onFail(Image image) {
                mView.uploadImageFail(image);
            }
        }));
    }

    @Override
    public void uploadFaultData(@NonNull JSONObject jsonObject) {
        mView.showUploadProgress();
        subscription.add(mFaultDataSource.uploadFaultData(jsonObject, new IObjectCallBack<String>() {

            @Override
            public void onSuccess() {
                mView.uploadFaultDataSuccess();
            }

            @Override
            public void onData(@NonNull String s) {

            }

            @Override
            public void onError(@Nullable String message) {
                mView.uploadFaultDataFail();
            }

            @Override
            public void noData() {

            }

            @Override
            public void onFinish() {
                mView.hideUploadProgress();
            }
        }));
    }

    @Override
    public void getUserFlowList() {

    }

    @Override
    public void deleteImage(Image image) {

    }
}
