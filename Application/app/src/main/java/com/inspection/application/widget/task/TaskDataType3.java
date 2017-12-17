package com.inspection.application.widget.task;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.inspection.application.R;
import com.inspection.application.mode.bean.equipment.db.EquipmentDataDb;
import com.inspection.application.mode.bean.task.DataItemValueListBean;
import com.inspection.application.view.photo.ViewPagePhotoActivity;
import com.library.utils.GlideUtils;

/**
 * 选择类型
 * Created by pingan on 2017/12/17.
 */

public class TaskDataType3 extends LinearLayout {

    private Context context;
    private OnTakePhotoListener onTakePhotoListener;
    private DataItemValueListBean dataItemValueListBean;
    private EquipmentDataDb equipmentDataDb;

    public TaskDataType3(Context context) {
        super(context);
        init(context);
    }


    private void init(Context context) {
        this.context = context;
        inflate(context, R.layout.layout_task_type_3, this);
    }

    public void setTaskData(final DataItemValueListBean dataItemValueListBean, final boolean canEdit, OnTakePhotoListener listener) {
        this.onTakePhotoListener = listener;
        this.dataItemValueListBean = dataItemValueListBean;
        this.equipmentDataDb = dataItemValueListBean.getDataItem().getEquipmentDataDb();
        if (!TextUtils.isEmpty(equipmentDataDb.getValue())) {
            dataItemValueListBean.getDataItem().setValue(equipmentDataDb.getValue());
        }
        if (!TextUtils.isEmpty(equipmentDataDb.getLocalPhoto())) {
            dataItemValueListBean.getDataItem().setLocalFile(equipmentDataDb.getLocalPhoto());
        }
        ((TextView) (findViewById(R.id.tv_title))).setText(dataItemValueListBean.getDataItem().getInspectionName());
        if (TextUtils.isEmpty(dataItemValueListBean.getDataItem().getValue())) {
            ((ImageView) (findViewById(R.id.iv_take_photo))).setImageDrawable(context.getResources().getDrawable(R.drawable.icon_add));
        } else {
            String imageUrl;
            if (dataItemValueListBean.getDataItem().getEquipmentDataDb() != null && !TextUtils.isEmpty(dataItemValueListBean.getDataItem().getEquipmentDataDb().getLocalPhoto())) {
                imageUrl = dataItemValueListBean.getDataItem().getEquipmentDataDb().getLocalPhoto();
            } else {
                imageUrl = dataItemValueListBean.getDataItem().getValue();
            }
            GlideUtils.ShowImage(context, imageUrl, (ImageView) (findViewById(R.id.iv_take_photo)), R.drawable.picture_default);
        }
        findViewById(R.id.iv_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(dataItemValueListBean.getDataItem().getValue())) {
                    ViewPagePhotoActivity.startActivity(context, new String[]{dataItemValueListBean.getDataItem().getValue()}, 0);
                    return;
                }
                if (!canEdit) {
                    return;
                }
                if (onTakePhotoListener != null) {
                    onTakePhotoListener.onTakePhoto(dataItemValueListBean);
                }
            }
        });
        findViewById(R.id.iv_take_photo).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (TextUtils.isEmpty(dataItemValueListBean.getDataItem().getValue())) {
                    return false;
                }
                new MaterialDialog.Builder(context)
                        .items(R.array.choose_condition_2)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                if (!canEdit) {
                                    return;
                                }
                                if (TextUtils.isEmpty(dataItemValueListBean.getDataItem().getValue())) {
                                    return;
                                }
                                switch (position) {
                                    case 0://删除照片
                                        onTakePhotoListener.onDeletePhoto(dataItemValueListBean);
                                        break;
                                    default://重新拍照
                                        onTakePhotoListener.onAgainPhoto(dataItemValueListBean);
                                        break;
                                }
                            }
                        })
                        .show();
                return true;
            }
        });
    }

    public void notifyUi() {
        if (dataItemValueListBean == null) {
            return;
        }
        String imageUrl;
        if (dataItemValueListBean.getDataItem().getEquipmentDataDb() != null && !TextUtils.isEmpty(dataItemValueListBean.getDataItem().getEquipmentDataDb().getLocalPhoto())) {
            imageUrl = dataItemValueListBean.getDataItem().getEquipmentDataDb().getLocalPhoto();
        } else {
            imageUrl = dataItemValueListBean.getDataItem().getValue();
        }
        if (TextUtils.isEmpty(imageUrl)) {
            ((ImageView) (findViewById(R.id.iv_take_photo))).setImageDrawable(context.getResources().getDrawable(R.drawable.icon_add));
        } else {
            GlideUtils.ShowImage(context, imageUrl, (ImageView) (findViewById(R.id.iv_take_photo)), R.drawable.picture_default);
        }
    }

    public interface OnTakePhotoListener {

        void onTakePhoto(DataItemValueListBean dataItemValueListBean);

        void onDeletePhoto(DataItemValueListBean dataItemValueListBean);

        void onAgainPhoto(DataItemValueListBean dataItemValueListBean);
    }

}
