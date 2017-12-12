package com.inspection.application.mode.source.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.inspection.application.app.App;
import com.inspection.application.common.ConstantInt;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.api.Api;
import com.inspection.application.mode.api.ApiCallBackList;
import com.inspection.application.mode.api.ApiCallBackObject;
import com.inspection.application.mode.api.FaultApi;
import com.inspection.application.mode.api.TaskApi;
import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.equipment.db.RoomDb;
import com.inspection.application.mode.bean.equipment.db.RoomDbDao;
import com.inspection.application.mode.bean.secure.SecureBean;
import com.inspection.application.mode.bean.task.InspectionBean;
import com.inspection.application.mode.bean.task.InspectionDetailBean;
import com.inspection.application.mode.callback.IListCallBack;
import com.inspection.application.mode.callback.IObjectCallBack;
import com.inspection.application.mode.db.DbManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * task repository
 * Created by pingan on 2017/12/9.
 */
@Singleton
public class TaskRepository implements TaskDataSource {

    private SharedPreferences dataSp;

    @Inject
    TaskRepository(Context context) {
        dataSp = context.getSharedPreferences(ConstantStr.SECURE_INFO, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public Subscription getSecureInfo(long securityId, @NonNull final IObjectCallBack<SecureBean> callBack) {
        Observable<Bean<SecureBean>> observable = Api.createRetrofit().create(TaskApi.class)
                .getSecureInfo(securityId);
        return new ApiCallBackObject<SecureBean>(observable) {
            @Override
            public void onData(@NonNull SecureBean data) {
                callBack.onData(data);
            }

            @Override
            public void onSuccess() {
                callBack.onSuccess();
            }

            @Override
            public void onFail(@NonNull String message) {
                callBack.onError(message);
            }

            @Override
            public void onFinish() {
                callBack.onFinish();
            }

            @Override
            public void noData() {
                callBack.noData();
            }
        }.execute().subscribe();
    }

    @NonNull
    @Override
    public Subscription getTaskList(String date, @NonNull final IListCallBack<InspectionBean> callBack) {
        Observable<Bean<List<InspectionBean>>> observable = Api.createRetrofit().create(TaskApi.class)
                .getInspectionList(ConstantInt.INSPECTION_TYPE, date, ConstantInt.MAX_PAGE_SIZE);
        return new ApiCallBackList<InspectionBean>(observable) {
            @Override
            public void onSuccess() {
                callBack.onSuccess();
            }

            @Override
            public void onData(List<InspectionBean> data) {
                callBack.onData(data);
            }

            @Override
            public void onFail(@NonNull String message) {
                callBack.onError(message);
            }

            @Override
            public void onFinish() {
                callBack.onFinish();
            }

            @Override
            public void noData() {
                callBack.noData();
            }
        }.execute().subscribe();
    }

    @NonNull
    @Override
    public Subscription getTask(final int position, final long taskId, @NonNull final GetTaskCallBack callBack) {
        return new ApiCallBackObject<String>(Api.createRetrofit()
                .create(TaskApi.class).operationTask(taskId, ConstantInt.OPERATION_STATE_1)) {
            @Override
            public void onData(@NonNull String data) {

            }

            @Override
            public void onSuccess() {
                callBack.onSuccess(position);
            }

            @Override
            public void onFail(@NonNull String message) {
                callBack.onFail(message);
            }

            @Override
            public void onFinish() {
                callBack.onFinish();
            }

            @Override
            public void noData() {

            }
        }.execute().subscribe();
    }

    @NonNull
    @Override
    public Subscription getTaskInfo(long taskId, final IObjectCallBack<InspectionDetailBean> callBack) {

        return new ApiCallBackObject<InspectionDetailBean>(Api.createRetrofit().create(TaskApi.class)
                .getInspectionDetailList(taskId)) {
            @Override
            public void onData(@NonNull InspectionDetailBean data) {

            }

            @Override
            public void onSuccess() {
                callBack.onSuccess();
            }

            @Override
            public void onFail(@NonNull String message) {
                callBack.onError(message);
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void noData() {
                callBack.noData();
            }
        }.execute().observeOn(Schedulers.io()).flatMap(new Func1<InspectionDetailBean, Observable<InspectionDetailBean>>() {
            @Override
            public Observable<InspectionDetailBean> call(InspectionDetailBean inspectionDetailBean) {
                if (inspectionDetailBean != null) {
                    long taskId = inspectionDetailBean.getTaskId();
                    List<RoomDb> saveRoomDbList = new ArrayList<>();
                    for (int i = 0; i < inspectionDetailBean.getRoomList().size(); i++) {
                        long roomId = inspectionDetailBean.getRoomList().get(i).getRoom().getRoomId();
                        RoomDb roomDb = DbManager.getDbManager().getDaoSession().getRoomDbDao().queryBuilder()
                                .where(RoomDbDao.Properties.TaskId.eq(taskId), RoomDbDao.Properties.RoomId.eq(roomId),
                                        RoomDbDao.Properties.UserId.eq(App.getInstance().getCurrentUser().getUserId())).unique();
                        if (roomDb == null) {
                            roomDb = new RoomDb();
                            roomDb.setRoomId(roomId);
                            roomDb.setTaskId(taskId);
                            roomDb.setLastSaveTime(System.currentTimeMillis());
                            roomDb.setRoomName(inspectionDetailBean.getRoomList().get(i).getRoom().getRoomName());
                            roomDb.setState(inspectionDetailBean.getRoomList().get(i).getTaskRoomState());
                            saveRoomDbList.add(roomDb);
                        }
                        inspectionDetailBean.getRoomList().get(i).setRoomDb(roomDb);
                    }
                    DbManager.getDbManager().getDaoSession().getRoomDbDao().insertOrReplaceInTx(saveRoomDbList);
                    return Observable.just(inspectionDetailBean);
                }
                return Observable.just(null);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<InspectionDetailBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                callBack.onFinish();
                callBack.onError(e.getMessage());
                callBack.noData();
            }

            @Override
            public void onNext(InspectionDetailBean inspectionDetailBean) {
                callBack.onFinish();
                callBack.onData(inspectionDetailBean);
            }
        });
    }

    @Override
    public void checkSecure(int position, long taskId, @NonNull CheckSecureInfoCallBack callBack) {
        if (!dataSp.getBoolean(String.valueOf(taskId), false)) {
            callBack.onShowSecure(position);
        } else {
            callBack.onShowTask(position);
        }
    }
}
