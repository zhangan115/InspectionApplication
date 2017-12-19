package com.inspection.application.view.fault;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.common.ConstantInt;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.equipment.EquipmentBean;
import com.inspection.application.mode.bean.fault.DefaultFlowBean;
import com.inspection.application.mode.bean.image.Image;
import com.inspection.application.mode.bean.option.OptionBean;
import com.inspection.application.utils.PhotoUtils;
import com.inspection.application.view.BaseActivity;
import com.inspection.application.view.equipment.EquipListActivity;
import com.inspection.application.widget.TakePhotoView;
import com.library.utils.ActivityUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 缺陷上报
 * Created by pingan on 2017/12/5.
 */

public class FaultActivity extends BaseActivity implements FaultContract.View {

    private FaultContract.Presenter mPresenter;
    @Nullable
    private Long mTaskId, mRoomId, mEquipmentId;
    //view
    private TakePhotoView mTakePhotoView;
    private TextView deviceNameTv, faultGradeTv, appointTv;
    private EditText describeFaultEt;
    //data
    private Long taskId;
    private JSONObject uploadJson;//上传数据
    private File photoFile;//拍照
    private Image mImage;//当前拍照对象
    private List<Image> images;//照片集合
    private EquipmentBean mEquipmentBean;//选择的设备
    private String INSPECTION_TAG;
    private int mFaultGrade = -1;
    private Long defaultFlowId;
    private List<OptionBean.ItemListBean> typeList;
    private List<DefaultFlowBean> mDefaultFlowBeen;

    private static final int ACTION_START_CAMERA = 200;
    private static final int CHOOSE_EQUIPMENT = 110;
    private static final int CHOOSE_USER_LIST = 111;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_fault, R.string.main_fault_submit);
        new FaultPresenter(Injection.getIntent().provideFaultRepository(App.getInstance().getModule()), this);
        initView();
        getDataFromIntent();
        initData();
        mPresenter.getCacheFromDb(INSPECTION_TAG);
    }

    private void initView() {
        findViewById(R.id.tv_fault_submit).setOnClickListener(this);
        findViewById(R.id.id_fault_ll_grade).setOnClickListener(this);
        describeFaultEt = findViewById(R.id.et_fault_describe);
        deviceNameTv = findViewById(R.id.id_fault_device_name);
        faultGradeTv = findViewById(R.id.tv_fault_grade);
        appointTv = findViewById(R.id.tv_appoint);
        mTakePhotoView = findViewById(R.id.take_photo_view);
        mTakePhotoView.setTakePhotoListener(new TakePhotoView.TakePhotoListener() {

            @Override
            public void onTakePhoto() {
                mImage = null;
                photoFile = new File(App.getInstance().imageCacheFile(), System.currentTimeMillis() + ".jpg");
                ActivityUtils.startCameraToPhoto(FaultActivity.this, photoFile, ACTION_START_CAMERA);
            }

            @Override
            public void onDelete(int position, Image image) {
                mImage = null;
                images.remove(position);
                mPresenter.deleteImage(image);
                mTakePhotoView.setImages(images);
            }

            @Override
            public void onTakePhoto(int position, Image image) {
                mImage = image;
                photoFile = new File(App.getInstance().imageCacheFile(), System.currentTimeMillis() + ".jpg");
                ActivityUtils.startCameraToPhoto(FaultActivity.this, photoFile, ACTION_START_CAMERA);
            }
        });
    }

    private void initData() {
        images = new ArrayList<>();
        List<OptionBean> list = App.getInstance().getOptionInfo();
        if (list != null && list.size() > 0) {
            typeList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getOptionId() == 2) {
                    typeList.addAll(list.get(i).getItemList());
                }
            }
        }
        if (App.getInstance().getCurrentUser().getCustomer().getIsOpen() == 1) {
            mPresenter.getUserFlowList();
        }
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
        mEquipmentBean = intent.getParcelableExtra(ConstantStr.KEY_BUNDLE_OBJECT);
        if (mEquipmentBean == null && mTaskId == null) {
            findViewById(R.id.id_fault_ll_device).setOnClickListener(this);
        } else {
            INSPECTION_TAG = mTaskId + "_" + mRoomId + "_" + mEquipmentId;
            setEquipmentName();
        }
    }

    private void setEquipmentName() {
        if (TextUtils.isEmpty(mEquipmentBean.getEquipmentSn())) {
            deviceNameTv.setText(mEquipmentBean.getEquipmentName());
        } else {
            String str = mEquipmentBean.getEquipmentName() + "(" + mEquipmentBean.getEquipmentSn() + ")";
            deviceNameTv.setText(str);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_fault_submit:
                for (int i = 0; i < mTakePhotoView.getImages().size(); i++) {
                    if (!mTakePhotoView.getImages().get(i).getIsUpload()) {
                        App.getInstance().showToast("正在上传照片,请稍等...");
                        return;
                    }
                }
                uploadJson = new JSONObject();
                String imageUrls = null;
                try {
                    if (images != null && images.size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < images.size(); i++) {
                            if (!TextUtils.isEmpty(images.get(i).getImageUrl())) {
                                if (i != images.size() - 1) {
                                    sb.append(images.get(i).getImageUrl()).append(",");
                                } else {
                                    sb.append(images.get(i).getImageUrl());
                                }
                            }
                        }
                        imageUrls = sb.toString();
                    }
                    if (TextUtils.isEmpty(imageUrls)) {
                        App.getInstance().showToast("请拍照");
                        return;
                    }
                    uploadJson.put("picsUrl", imageUrls);
                    if (mEquipmentBean == null) {
                        App.getInstance().showToast("请选择设备");
                        return;
                    }
                    uploadJson.put("equipmentId", String.valueOf(mEquipmentBean.getEquipmentId()));
                    if (mFaultGrade == -1) {
                        App.getInstance().showToast("请选择故障类型");
                        return;
                    }
                    uploadJson.put("faultType", String.valueOf(mFaultGrade));
                    if (TextUtils.isEmpty(describeFaultEt.getText().toString())) {
                        App.getInstance().showToast("请输入故障描述");
                        return;
                    }
                    uploadJson.put("faultDescript", describeFaultEt.getText().toString());
                    if (defaultFlowId == null) {
                        App.getInstance().showToast("请选择指派人");
                        return;
                    }
                    uploadJson.put("defaultFlowId", String.valueOf(defaultFlowId));
                    mPresenter.uploadFaultData(uploadJson);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.id_fault_ll_device:
                Intent deviceInt = new Intent(this, EquipListActivity.class);
                deviceInt.putExtra(ConstantStr.KEY_BUNDLE_BOOLEAN, true);
                startActivityForResult(deviceInt, CHOOSE_EQUIPMENT);
                break;
            case R.id.id_fault_ll_grade:
                final List<String> mTypeItem = new ArrayList<>();
                for (int i = 0; i < typeList.size(); i++) {
                    mTypeItem.add(typeList.get(i).getItemName());
                }
                new MaterialDialog.Builder(FaultActivity.this)
                        .items(mTypeItem)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                faultGradeTv.setText(typeList.get(position).getItemName());
                                mFaultGrade = Integer.valueOf(typeList.get(position).getItemCode());
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CHOOSE_EQUIPMENT) {
                mEquipmentBean = data.getParcelableExtra(ConstantStr.KEY_BUNDLE_OBJECT);
                setEquipmentName();
            } else if (requestCode == ACTION_START_CAMERA) {
                if (mImage != null) {
                    mImage.setIsUpload(false);
                    mImage.setImageUrl(null);
                } else {
                    mImage = new Image();
                    mImage.setWorkType(ConstantInt.FAULT);
                    if (!TextUtils.isEmpty(INSPECTION_TAG)) {
                        mImage.setItemId(INSPECTION_TAG);
                    }
                    images.add(mImage);
                }
                mImage.setPhotoLocal(photoFile.getAbsolutePath());
                mTakePhotoView.setImages(images);
                PhotoUtils.cropPhoto(this, mImage, new PhotoUtils.CropPhotoListener() {

                    @Override
                    public void onFail(Image image) {
                        if (images.contains(image)) {
                            images.remove(image);
                        }
                        mTakePhotoView.setImages(images);
                    }

                    @Override
                    public void onSuccess(Image image) {
                        mPresenter.uploadPhoto(image);
                    }
                });
            }
        }
    }

    @Override
    public void showUploadProgress() {
        showProgressDialog("上传中...");
    }

    @Override
    public void hideUploadProgress() {
        hideProgressDialog();
    }

    @Override
    public void showImageList(@NonNull List<Image> images) {
        this.images = images;
        mTakePhotoView.setImages(images);
    }

    @Override
    public void uploadImageSuccess() {
        mTakePhotoView.setImages(images);
    }

    @Override
    public void uploadImageFail(Image image) {
        App.getInstance().showToast("照片上传失败");
        if (images.contains(image)) {
            mPresenter.deleteImage(image);
            images.remove(image);
            mTakePhotoView.setImages(images);
        }
    }

    @Override
    public void uploadFaultDataSuccess() {
        App.getInstance().showToast("上传缺陷成功");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void uploadFaultDataFail() {
        App.getInstance().showToast("上传缺陷失败");
    }

    @Override
    public void showDefaultFlowData(@NonNull List<DefaultFlowBean> beans) {
        mDefaultFlowBeen = beans;
        appointTv.setText("故障审批人已由管理员预设");
        defaultFlowId = beans.get(0).getDefaultFlowId();
    }

    @Override
    public void noDefaultFlowData() {
        appointTv.setText("未设置故障审批人，请去后台设置");
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
