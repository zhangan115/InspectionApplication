package com.inspection.application.mode.source.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.app.App;
import com.inspection.application.common.ConstantInt;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.api.Api;
import com.inspection.application.mode.api.ApiCallBackList;
import com.inspection.application.mode.api.ApiCallBackObject;
import com.inspection.application.mode.api.TaskApi;
import com.inspection.application.mode.api.UploadApi;
import com.inspection.application.mode.bean.Bean;
import com.inspection.application.mode.bean.equipment.db.EquipmentDataDb;
import com.inspection.application.mode.bean.equipment.db.EquipmentDataDbDao;
import com.inspection.application.mode.bean.equipment.db.EquipmentDb;
import com.inspection.application.mode.bean.equipment.db.EquipmentDbDao;
import com.inspection.application.mode.bean.equipment.db.RoomDb;
import com.inspection.application.mode.bean.equipment.db.RoomDbDao;
import com.inspection.application.mode.bean.secure.SecureBean;
import com.inspection.application.mode.bean.task.DataItemValueListBean;
import com.inspection.application.mode.bean.task.InspectionBean;
import com.inspection.application.mode.bean.task.InspectionDetailBean;
import com.inspection.application.mode.bean.task.RoomListBean;
import com.inspection.application.mode.bean.task.TaskEquipmentBean;
import com.inspection.application.mode.bean.task.data.CheckBean;
import com.inspection.application.mode.bean.task.data.InspectionDataBean;
import com.inspection.application.mode.callback.IListCallBack;
import com.inspection.application.mode.callback.IObjectCallBack;
import com.inspection.application.mode.db.DbManager;
import com.inspection.application.mode.source.FilePartManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
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
    public Subscription getMyTaskList(@Nullable Long lastId, @NonNull final IListCallBack<InspectionBean> callBack) {
        Observable<Bean<List<InspectionBean>>> observable = Api.createRetrofit().create(TaskApi.class)
                .getInspectionList(ConstantInt.INSPECTION_TYPE, ConstantInt.PAGE_SIZE, lastId);
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

    @NonNull
    @Override
    public Subscription loadTaskEquipData(final long taskId, final long roomId, final TaskEquipmentBean taskEquipmentBean, final ILoadEquipmentDataCallBack callBack) {
        return Observable.just(taskEquipmentBean).flatMap(new Func1<TaskEquipmentBean, Observable<TaskEquipmentBean>>() {
            @Override
            public Observable<TaskEquipmentBean> call(TaskEquipmentBean taskEquipmentBean) {
                if (taskEquipmentBean.getEquipment().getEquipmentDb() == null) {
                    try {
                        EquipmentDb equipmentDb = DbManager.getDbManager().getDaoSession().getEquipmentDbDao().queryBuilder()
                                .where(EquipmentDbDao.Properties.UserId.eq(App.getInstance().getCurrentUser().getUserId())
                                        , EquipmentDbDao.Properties.EquipmentId.eq(taskEquipmentBean.getEquipment().getEquipmentId())
                                        , EquipmentDbDao.Properties.TaskId.eq(taskId)
                                        , EquipmentDbDao.Properties.RoomId.eq(roomId))
                                .unique();
                        if (equipmentDb == null) {
                            equipmentDb = new EquipmentDb();
                            equipmentDb.setEquipmentId(taskEquipmentBean.getEquipment().getEquipmentId());
                            equipmentDb.setRoomId(roomId);
                            equipmentDb.setTaskId(taskId);
                            equipmentDb.setEquipmentName(taskEquipmentBean.getEquipment().getEquipmentName());
                            DbManager.getDbManager().getDaoSession().getEquipmentDbDao().insertOrReplaceInTx(equipmentDb);
                        }
                        taskEquipmentBean.getEquipment().setEquipmentDb(equipmentDb);
                    } catch (Exception e) {
                        return Observable.just(null);
                    }
                }
                List<EquipmentDataDb> equipmentDataDbToSave = new ArrayList<>();
                for (int i = 0; i < taskEquipmentBean.getDataList().get(0).getDataItemValueList().size(); i++) {
                    if (taskEquipmentBean.getDataList().get(0).getDataItemValueList().get(i).getDataItem().getEquipmentDataDb() == null) {
                        try {
                            EquipmentDataDb equipmentData = DbManager.getDbManager().getDaoSession().getEquipmentDataDbDao()
                                    .queryBuilder().where(EquipmentDataDbDao.Properties.CurrentUserId.eq(App.getInstance().getCurrentUser().getUserId())
                                            , EquipmentDataDbDao.Properties.EquipmentId.eq(taskEquipmentBean.getEquipment().getEquipmentId())
                                            , EquipmentDataDbDao.Properties.RoomId.eq(roomId)
                                            , EquipmentDataDbDao.Properties.TaskId.eq(taskId)
                                            , EquipmentDataDbDao.Properties.DataItemId.eq(taskEquipmentBean.getDataList().get(0).getDataItemValueList().get(i).getDataItemValueId())
                                            , EquipmentDataDbDao.Properties.Type.eq(taskEquipmentBean.getDataList().get(0).getDataItemValueList().get(i).getDataItem().getInspectionType())).unique();
                            if (equipmentData == null) {
                                equipmentData = new EquipmentDataDb();
                                equipmentData.setRoomId(roomId);
                                equipmentData.setTaskId(taskId);
                                equipmentData.setEquipmentId(taskEquipmentBean.getEquipment().getEquipmentId());
                                equipmentData.setDataItemId(taskEquipmentBean.getDataList().get(0).getDataItemValueList().get(i).getDataItemValueId());
                                equipmentData.setType(taskEquipmentBean.getDataList().get(0).getDataItemValueList().get(i).getDataItem().getInspectionType());
                                equipmentDataDbToSave.add(equipmentData);
                            }
                            taskEquipmentBean.getDataList().get(0).getDataItemValueList().get(i).getDataItem().setEquipmentDataDb(equipmentData);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return Observable.just(null);
                        }
                    }
                }
                if (equipmentDataDbToSave.size() > 0) {
                    DbManager.getDbManager().getDaoSession().getEquipmentDataDbDao().insertOrReplaceInTx(equipmentDataDbToSave);
                }
                return Observable.just(taskEquipmentBean);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<TaskEquipmentBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                callBack.onFail();
            }

            @Override
            public void onNext(TaskEquipmentBean taskEquipmentBean) {
                if (taskEquipmentBean == null) {
                    callBack.onFail();
                } else {
                    callBack.success();
                }
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

    @NonNull
    @Override
    public Subscription getCheckData(long taskId, @NonNull final IObjectCallBack<CheckBean> callBack) {
        return new ApiCallBackObject<CheckBean>(Api.createRetrofit().create(TaskApi.class).getCheckInfo(taskId)) {
            @Override
            public void onData(@NonNull CheckBean data) {
                callBack.onData(data);
            }

            @Override
            public void onSuccess() {

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
    public Subscription getTaskData(long taskId, @NonNull final IObjectCallBack<InspectionDataBean> callBack) {
        return new ApiCallBackObject<InspectionDataBean>(Api.createRetrofit().create(TaskApi.class).getInspectionData(taskId)) {
            @Override
            public void onData(@NonNull InspectionDataBean data) {
                callBack.onData(data);
            }

            @Override
            public void onSuccess() {

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
    public Subscription startTask(final RoomListBean roomListBean, long taskId, final IStartTaskCallBack callBack) {
        return new ApiCallBackObject<String>(Api.createRetrofit().create(TaskApi.class).uploadStartOrEnd(taskId, roomListBean.getTaskRoomId(), 1)) {

            @Override
            public void onData(@NonNull String data) {

            }

            @Override
            public void onSuccess() {
                roomListBean.setTaskRoomState(ConstantInt.ROOM_STATE_2);
                callBack.onSuccess(roomListBean);
            }

            @Override
            public void onFail(@NonNull String message) {
                callBack.onFail(message);
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void noData() {

            }
        }.execute().subscribe();
    }

    @NonNull
    @Override
    public Subscription finishTask(final RoomListBean roomListBean, long taskId, final IStartTaskCallBack callBack) {
        return new ApiCallBackObject<String>(Api.createRetrofit().create(TaskApi.class).uploadStartOrEnd(taskId, roomListBean.getTaskRoomId(), 2)) {

            @Override
            public void onData(@NonNull String data) {

            }

            @Override
            public void onSuccess() {
                roomListBean.setTaskRoomState(ConstantInt.ROOM_STATE_3);
                callBack.onSuccess(roomListBean);
            }

            @Override
            public void onFail(@NonNull String message) {
                callBack.onFail(message);
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void noData() {

            }
        }.execute().subscribe();
    }

    @Override
    public void checkTaskFinish(RoomListBean roomListBean, long taskId, ICheckTaskCallBack callBack) {

    }

    @NonNull
    @Override
    public Subscription uploadTaskPhoto(final DataItemValueListBean dataItemValueListBean, final IUploadPhotoCallBack callBack) {
        Observable<Bean<List<String>>> observable = Api.createRetrofit().create(UploadApi.class)
                .postFile(FilePartManager.getPostFileParts("task", "image", new File(dataItemValueListBean.getDataItem().getLocalFile())));
        return new ApiCallBackList<String>(observable) {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onData(List<String> strings) {

            }

            @Override
            public void onFail(@NonNull String message) {
                callBack.onFail();
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void noData() {

            }
        }.execute().observeOn(Schedulers.io()).flatMap(new Func1<List<String>, Observable<DataItemValueListBean>>() {
            @Override
            public Observable<DataItemValueListBean> call(List<String> strings) {
                boolean isSuccess;
                if (strings != null && strings.size() > 0) {
                    isSuccess = true;
                    dataItemValueListBean.getDataItem().setLocalFile(strings.get(0));
                    dataItemValueListBean.getDataItem().setValue(strings.get(0));
                    dataItemValueListBean.getDataItem().getEquipmentDataDb().setValue(strings.get(0));
                } else {
                    isSuccess = false;
                    dataItemValueListBean.getDataItem().setLocalFile("");
                    dataItemValueListBean.getDataItem().setValue("");
                    dataItemValueListBean.getDataItem().getEquipmentDataDb().setLocalPhoto("");
                    dataItemValueListBean.getDataItem().getEquipmentDataDb().setValue("");
                }
                DbManager.getDbManager().getDaoSession().getEquipmentDataDbDao().insertOrReplace(dataItemValueListBean.getDataItem().getEquipmentDataDb());
                if (isSuccess) {
                    return Observable.just(dataItemValueListBean);
                } else {
                    return Observable.just(null);
                }

            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<DataItemValueListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                callBack.onFail();
            }

            @Override
            public void onNext(DataItemValueListBean dataItemValueListBean) {
                if (dataItemValueListBean != null) {
                    callBack.onSuccess();
                } else {
                    callBack.onFail();
                }
            }
        });
    }

    @Override
    public void deletePhoto(DataItemValueListBean dataItemValueListBean) {
        dataItemValueListBean.getDataItem().setLocalFile("");
        dataItemValueListBean.getDataItem().setValue("");
        if (dataItemValueListBean.getDataItem().getEquipmentDataDb() != null) {
            dataItemValueListBean.getDataItem().getEquipmentDataDb().setLocalPhoto("");
            dataItemValueListBean.getDataItem().getEquipmentDataDb().setValue("");
            DbManager.getDbManager().getDaoSession().getEquipmentDataDbDao().insertOrReplaceInTx(dataItemValueListBean.getDataItem().getEquipmentDataDb());
        }
    }
}
