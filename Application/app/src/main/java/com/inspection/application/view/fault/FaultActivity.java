package com.inspection.application.view.fault;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inspection.application.R;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.equipment.EquipmentBean;
import com.inspection.application.mode.bean.fault.DefaultFlowBean;
import com.inspection.application.mode.bean.image.Image;
import com.inspection.application.mode.bean.user.User;
import com.inspection.application.view.BaseActivity;

import java.util.List;

/**
 * 缺陷上报
 * Created by pingan on 2017/12/5.
 */

public class FaultActivity extends BaseActivity implements FaultContract.View {

    private FaultContract.Presenter mPresenter;
    @Nullable
    private Long mTaskId, mRoomId, mEquipmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_container_toolbar, R.string.main_fault_submit);
        if (savedInstanceState != null) {

        }

        getDataFromIntent();
        mPresenter.getCacheFromDb(mTaskId, mRoomId, mEquipmentId);
    }

    /**
     * 获取从intent的值
     */
    private void getDataFromIntent() {
        Intent intent = getIntent();
        mTaskId = intent.getLongExtra(ConstantStr.KEY_BUNDLE_LONG, -1);
        if (mTaskId == -1) {
            mTaskId = null;
        }
        mRoomId = intent.getLongExtra(ConstantStr.KEY_BUNDLE_LONG_1, -1);
        if (mRoomId == -1) {
            mRoomId = null;
        }
        mEquipmentId = intent.getLongExtra(ConstantStr.KEY_BUNDLE_LONG_2, -1);
        if (mEquipmentId == -1) {
            mEquipmentId = null;
        }
    }

    @Override
    public void showUploadProgress() {

    }

    @Override
    public void hideUploadProgress() {

    }

    @Override
    public void showImageList(@NonNull List<Image> images) {

    }

    @Override
    public void showDataFromCache(@Nullable EquipmentBean equipmentBean, int faultType, @Nullable String faultDescribe, @Nullable List<User> userList) {

    }

    @Override
    public void uploadImageSuccess() {

    }

    @Override
    public void uploadImageFail(Image image) {

    }

    @Override
    public void uploadFaultDataSuccess() {

    }

    @Override
    public void uploadFaultDataFail() {

    }

    @Override
    public void showDefaultFlowData(@Nullable List<DefaultFlowBean> beans) {

    }

    @Override
    public void noDefaultFlowData() {

    }

    @Override
    public void showMessage(@Nullable String message) {

    }

    @Override
    public void setPresenter(FaultContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onCancel() {
        super.onCancel();
        mPresenter.unSubscribe();
    }
}
