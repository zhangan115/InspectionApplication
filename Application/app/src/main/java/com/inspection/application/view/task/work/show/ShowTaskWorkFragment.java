package com.inspection.application.view.task.work.show;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.common.ConstantInt;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.bean.task.DataItemValueListBean;
import com.inspection.application.mode.bean.task.TaskEquipmentBean;
import com.inspection.application.utils.PhotoUtils;
import com.inspection.application.view.MvpFragment;
import com.inspection.application.view.task.work.IEquipmentChangeListener;
import com.inspection.application.view.task.work.IViewCreateListener;
import com.inspection.application.widget.task.IEquipmentStateChange;
import com.inspection.application.widget.task.TaskDataType1;
import com.inspection.application.widget.task.TaskDataType2;
import com.inspection.application.widget.task.TaskDataType3;
import com.library.utils.ActivityUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备数据展示
 * Created by pingan on 2017/12/17.
 */

public class ShowTaskWorkFragment extends MvpFragment<ShowTaskWorkContact.Presenter> implements IEquipmentChangeListener, ShowTaskWorkContact.View, TaskDataType3.OnTakePhotoListener, IEquipmentStateChange {

    private LinearLayout ll_equipment_data;
    private LinearLayout loadingLL;

    private static final int ACTION_START_CAMERA = 100;
    private static final int ACTION_START_ALARM = 101;
    private TaskEquipmentBean mTaskEquipmentBean;
//        private IViewCreateListener createListener;
    private boolean canEdit;
    private long taskId, roomId;
    private List<TaskDataType3> photoTypeList;
    private File photoFile;

    private DataItemValueListBean dataItemValueListBean;

    public static ShowTaskWorkFragment newInstance(long taskId, long roomId) {
        Bundle args = new Bundle();
        args.putLong(ConstantStr.KEY_BUNDLE_LONG, taskId);
        args.putLong(ConstantStr.KEY_BUNDLE_LONG_1, roomId);
        ShowTaskWorkFragment fragment = new ShowTaskWorkFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoTypeList = new ArrayList<>();
        if (getArguments() != null) {
            taskId = getArguments().getLong(ConstantStr.KEY_BUNDLE_LONG);
            roomId = getArguments().getLong(ConstantStr.KEY_BUNDLE_LONG_1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_work, container, false);
        ll_equipment_data = rootView.findViewById(R.id.ll_equipment_data);
        loadingLL = rootView.findViewById(R.id.ll_loading);
        return rootView;
    }

    public void setCreateListener(IViewCreateListener createListener) {
//        this.createListener = createListener;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            Log.d("za", "view_create");
            getActivity().sendBroadcast(new Intent("view_create"));
        }
    }

    @Override
    public void equipmentChange(TaskEquipmentBean taskEquipmentBean, boolean canEdit) {
        mTaskEquipmentBean = taskEquipmentBean;
        this.canEdit = canEdit;
        mPresenter.unSubscribe();
        mPresenter.loadDtaFromDb(taskId, roomId, taskEquipmentBean);
    }

    private void notifyEquipmentChange() {
        if (getView() == null) {
            return;
        }
        ll_equipment_data.removeAllViews();
        for (int i = 0; i < mTaskEquipmentBean.getDataList().get(0).getDataItemValueList().size(); i++) {
            DataItemValueListBean dataItemValueListBean = mTaskEquipmentBean.getDataList().get(0).getDataItemValueList().get(i);
            if (dataItemValueListBean != null) {
                int dataType = dataItemValueListBean.getDataItem().getInspectionType();
                if (dataType == ConstantInt.DATA_VALUE_TYPE_1) {
                    TaskDataType1 taskDataType1 = new TaskDataType1(getActivity());
                    taskDataType1.setTaskData(dataItemValueListBean, mTaskEquipmentBean.getEquipment().getEquipmentDb(), canEdit, this);
                    ll_equipment_data.addView(taskDataType1);
                } else if (dataType == ConstantInt.DATA_VALUE_TYPE_2) {
                    TaskDataType2 taskDataType2 = new TaskDataType2(getActivity());
                    taskDataType2.setTaskData(dataItemValueListBean, mTaskEquipmentBean.getEquipment().getEquipmentDb(), canEdit, this);
                    ll_equipment_data.addView(taskDataType2);
                } else if (dataType == ConstantInt.DATA_VALUE_TYPE_3) {
                    TaskDataType3 taskDataType3 = new TaskDataType3(getActivity());
                    taskDataType3.setTaskData(dataItemValueListBean, canEdit, this);
                    ll_equipment_data.addView(taskDataType3);
                    photoTypeList.add(taskDataType3);
                } else if (dataType == ConstantInt.DATA_VALUE_TYPE_4) {
                    TaskDataType2 taskDataType4 = new TaskDataType2(getActivity());
                    taskDataType4.setTaskData(dataItemValueListBean, mTaskEquipmentBean.getEquipment().getEquipmentDb(), canEdit, this);
                    ll_equipment_data.addView(taskDataType4);
                }
            }
        }
        equipmentState();
    }

    @Override
    public void showLoading() {
        loadingLL.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingLL.setVisibility(View.GONE);
    }

    @Override
    public void showData() {
        notifyEquipmentChange();
    }

    @Override
    public void uploadPhotoSuccess() {
        for (int i = 0; i < photoTypeList.size(); i++) {
            photoTypeList.get(i).notifyUi();
        }
        equipmentState();
    }

    @Override
    public void uploadPhotoFail() {
        equipmentState();
    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void deletePhoto() {
        for (int i = 0; i < photoTypeList.size(); i++) {
            photoTypeList.get(i).notifyUi();
        }
    }

    @Override
    public void setPresenter(ShowTaskWorkContact.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onTakePhoto(DataItemValueListBean dataItemValueListBean) {
        this.dataItemValueListBean = dataItemValueListBean;
        photoFile = new File(App.getInstance().imageCacheFile(), System.currentTimeMillis() + ".jpg");
        ActivityUtils.startCameraToPhotoV4(ShowTaskWorkFragment.this, photoFile, ACTION_START_CAMERA);
    }

    @Override
    public void onDeletePhoto(DataItemValueListBean dataItemValueListBean) {
        this.dataItemValueListBean = null;
        mPresenter.deletePhoto(dataItemValueListBean);
    }

    @Override
    public void onAgainPhoto(DataItemValueListBean dataItemValueListBean) {
        onDeletePhoto(dataItemValueListBean);
        onTakePhoto(dataItemValueListBean);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == ACTION_START_CAMERA && resultCode == Activity.RESULT_OK) {
            PhotoUtils.cropPhoto(getActivity(), photoFile, new PhotoUtils.PhotoListener() {
                @Override
                public void onSuccess(File file) {
                    if (dataItemValueListBean != null) {
                        dataItemValueListBean.getDataItem().setLocalFile(file.getAbsolutePath());
                        mPresenter.uploadPhoto(dataItemValueListBean);
                    }
                }
            });
        } else if (requestCode == ACTION_START_ALARM && resultCode == Activity.RESULT_OK) {

        }
    }

    @Override
    public void equipmentState() {
        if (getActivity() != null) {
            Log.d("za", "equipment_state");
            getActivity().sendBroadcast(new Intent("equipment_state"));
        }
    }
}
