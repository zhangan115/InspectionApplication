package com.inspection.application.mode.source.fault;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.inspection.application.app.App;
import com.inspection.application.common.ConstantInt;
import com.inspection.application.mode.api.Api;
import com.inspection.application.mode.api.ApiCallBackList;
import com.inspection.application.mode.api.ApiCallBackObject;
import com.inspection.application.mode.api.FaultApi;
import com.inspection.application.mode.api.UploadApi;
import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.fault.DefaultFlowBean;
import com.inspection.application.mode.bean.image.Image;
import com.inspection.application.mode.bean.image.ImageDao;
import com.inspection.application.mode.callback.IListCallBack;
import com.inspection.application.mode.callback.IObjectCallBack;
import com.inspection.application.mode.db.DbManager;
import com.inspection.application.mode.source.FilePartManager;
import com.inspection.application.utils.PhotoUtils;
import com.inspection.application.view.ApplicationModule;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * fault repository
 * Created by pingan on 2017/12/5.
 */
@Singleton
public class FaultRepository implements FaultDataSource {

    @Inject
    public FaultRepository(Context context) {

    }


    @NonNull
    @Override
    public Subscription loadImageList(@Nullable String inspectionTag, @NonNull final LoadImageCallBack callBack) {
        QueryBuilder<Image> queryBuilder;
        if (!TextUtils.isEmpty(inspectionTag)) {
            queryBuilder = DbManager.getDbManager().getDaoSession().getImageDao()
                    .queryBuilder()
                    .where(ImageDao.Properties.CurrentUserId.eq(App.getInstance().getCurrentUser().getUserId())
                            , ImageDao.Properties.ItemId.eq(inspectionTag)
                            , ImageDao.Properties.WorkType.eq(ConstantInt.FAULT));
        } else {
            queryBuilder = DbManager.getDbManager().getDaoSession().getImageDao()
                    .queryBuilder()
                    .where(ImageDao.Properties.CurrentUserId.eq(App.getInstance().getCurrentUser().getUserId())
                            , ImageDao.Properties.WorkType.eq(ConstantInt.FAULT));
        }
        return queryBuilder.rx().list()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Image>>() {
                    @Override
                    public void call(List<Image> images) {
                        if (images != null && images.size() > 0) {
                            callBack.onSuccess(images);
                        }
                    }
                });
    }

    @NonNull
    @Override
    public Subscription uploadPhoto(final Image image, @NonNull final UploadImageCallBack callBack) {
        Observable<Bean<List<String>>> observable = Api.createRetrofit().create(UploadApi.class)
                .postFile(FilePartManager.getPostFileParts("fault", "image", new File(image.getImageLocal())));
        return new ApiCallBackList<String>(observable) {
            @Override
            public void onData(List<String> data) {
                image.setImageUrl(data.get(0));
                image.setIsUpload(true);
                image.setSaveTime(System.currentTimeMillis());
                DbManager.getDbManager().getDaoSession().getImageDao().insertOrReplaceInTx(image);
                callBack.onSuccess();
            }
        }.execute(new IListCallBack<String>() {


            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(@NonNull List<String> list) {

            }

            @Override
            public void onError(@Nullable String message) {
                callBack.onFail(image);
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void noData() {

            }
        }).subscribe();
    }

    @NonNull
    @Override
    public Subscription uploadFaultData(@NonNull JSONObject jsonObject, @NonNull IObjectCallBack<String> callBack) {
        return new ApiCallBackObject<String>(Api.createRetrofit().create(FaultApi.class).uploadFaultData(jsonObject.toString())) {
            @Override
            public void onData(@NonNull String d) {

            }
        }.execute(callBack).subscribe();
    }

    @Override
    public void deleteImage(Image image) {
        DbManager.getDbManager().getDaoSession().getImageDao().delete(image);
    }

    @NonNull
    @Override
    public Subscription getFlowUserList(@NonNull final IListCallBack<DefaultFlowBean> callBack) {
        return new ApiCallBackList<DefaultFlowBean>(Api.createRetrofit().create(FaultApi.class).getDefaultFlow(1)) {
            @Override
            public void onData(List<DefaultFlowBean> data) {
                callBack.onData(data);
            }
        }.execute(callBack).subscribe();
    }

}
