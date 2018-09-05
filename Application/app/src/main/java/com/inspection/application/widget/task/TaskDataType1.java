package com.inspection.application.widget.task;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.mode.bean.equipment.db.EquipmentDataDb;
import com.inspection.application.mode.bean.equipment.db.EquipmentDb;
import com.inspection.application.mode.bean.task.DataItemBean;
import com.inspection.application.mode.bean.task.DataItemValueListBean;
import com.inspection.application.mode.db.DbManager;
import com.inspection.application.view.task.work.IEquipmentChangeListener;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 选择类型
 * Created by pingan on 2017/12/17.
 */

public class TaskDataType1 extends LinearLayout implements View.OnClickListener {

    private Context context;
    private boolean canEdit;
    private DataItemBean dataItemBean;
    private EquipmentDb equipmentDb;
    private EquipmentDataDb equipmentDataDb;
    private IEquipmentStateChange iEquipmentStateChange;


    public TaskDataType1(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        inflate(context, R.layout.layout_task_type_1, this);
    }

    public void setTaskData(DataItemValueListBean dataItemValueListBean, EquipmentDb equipmentDb, boolean caEdit, IEquipmentStateChange listener) {
        this.iEquipmentStateChange = listener;
        this.canEdit = caEdit;
        this.equipmentDb = equipmentDb;
        ((TextView) findViewById(R.id.tv_value_title)).setText(dataItemValueListBean.getDataItem().getInspectionName());
        dataItemBean = dataItemValueListBean.getDataItem();
        equipmentDataDb = dataItemBean.getEquipmentDataDb();
        dataItemBean.setValue(equipmentDataDb.getValue());
        findViewById(R.id.ll_check_result).setOnClickListener(this);
        dataItemBean.setChooseInspectionName(equipmentDataDb.getChooseInspectionName());
        if (!TextUtils.isEmpty(dataItemBean.getChooseInspectionName())) {
            ((TextView) findViewById(R.id.tv_check_result)).setText(dataItemBean.getEquipmentDataDb().getChooseInspectionName());
            ((TextView) findViewById(R.id.tv_check_result)).setTextColor(context.getResources().getColor(R.color.color4C8FE2));
            ((TextView) findViewById(R.id.tv_content_type_1)).setTextColor(context.getResources().getColor(R.color.color4C8FE2));
        } else {
            ((TextView) findViewById(R.id.tv_check_result)).setText("");
            ((TextView) findViewById(R.id.tv_check_result)).setTextColor(context.getResources().getColor(R.color.colorAAB5C9));
            ((TextView) findViewById(R.id.tv_content_type_1)).setTextColor(context.getResources().getColor(R.color.colorAAB5C9));
        }
        if (!TextUtils.isEmpty(dataItemValueListBean.getLastValue())) {
            ((TextView) findViewById(R.id.tv_last_time)).setText(MessageFormat.format("上次记录:{0}", dataItemValueListBean.getLastValue()));
        } else {
            ((TextView) findViewById(R.id.tv_last_time)).setText("上次记录:无");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_check_result) {
            if (!canEdit) {
                return;
            }
            if (dataItemBean == null) {
                return;
            }
            if (!(context instanceof Activity)) {
                return;
            }
            if (dataItemBean.getInspectionItemOptionList() == null || dataItemBean.getInspectionItemOptionList().size() == 0) {
                App.getInstance().showToast("请在后台配置选项");
                return;
            }
            List<String> items = new ArrayList<>();
            for (int i = 0; i < dataItemBean.getInspectionItemOptionList().size(); i++) {
                items.add(dataItemBean.getInspectionItemOptionList().get(i).getOptionName());
            }
            new MaterialDialog.Builder(context)
                    .items(items)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                            if (TextUtils.isEmpty(dataItemBean.getValue()) || !dataItemBean.getValue().equals(String.valueOf(dataItemBean.getInspectionItemOptionList().get(position).getId()))) {
                                ((TextView) findViewById(R.id.tv_check_result)).setText(text);
                                dataItemBean.setValue(String.valueOf(dataItemBean.getInspectionItemOptionList().get(position).getId()));
                                dataItemBean.setChooseInspectionName(dataItemBean.getInspectionItemOptionList().get(position).getOptionName());
                                EquipmentDataDb dataDb = dataItemBean.getEquipmentDataDb();
                                dataDb.setChooseInspectionName(text.toString());
                                dataDb.setValue(String.valueOf(dataItemBean.getInspectionItemOptionList().get(position).getId()));
                                if (equipmentDb.getUploadState()) {
                                    equipmentDb.setUploadState(false);
                                    DbManager.getDbManager().getDaoSession().getEquipmentDbDao()
                                            .insertOrReplace(equipmentDb);
                                    if (iEquipmentStateChange != null) {
                                        iEquipmentStateChange.equipmentState();
                                    }
                                }
                                DbManager.getDbManager().getDaoSession()
                                        .getEquipmentDataDbDao().insertOrReplaceInTx(dataDb);
                                refreshUi();
                            }
                        }
                    })
                    .show();
        }
    }

    private void refreshUi() {
        if (!TextUtils.isEmpty(dataItemBean.getChooseInspectionName())) {
            ((TextView) findViewById(R.id.tv_check_result)).setText(dataItemBean.getEquipmentDataDb().getChooseInspectionName());
            if (isNormal()) {
                ((TextView) findViewById(R.id.tv_check_result)).setTextColor(context.getResources().getColor(R.color.color4C8FE2));
                ((TextView) findViewById(R.id.tv_content_type_1)).setTextColor(context.getResources().getColor(R.color.color4C8FE2));
            } else {
                ((TextView) findViewById(R.id.tv_check_result)).setTextColor(context.getResources().getColor(R.color.colorF05340));
                ((TextView) findViewById(R.id.tv_content_type_1)).setTextColor(context.getResources().getColor(R.color.colorF05340));
            }
        } else {
            ((TextView) findViewById(R.id.tv_check_result)).setText("");
            ((TextView) findViewById(R.id.tv_check_result)).setTextColor(context.getResources().getColor(R.color.colorAAB5C9));
            ((TextView) findViewById(R.id.tv_content_type_1)).setTextColor(context.getResources().getColor(R.color.colorAAB5C9));
        }
    }

    private boolean isNormal() {
        if (dataItemBean.getInspectionItemOptionList() != null) {
            for (int i = 0; i < dataItemBean.getInspectionItemOptionList().size(); i++) {
                if (Long.valueOf(dataItemBean.getValue()) == dataItemBean.getInspectionItemOptionList().get(i).getId()) {
                    return dataItemBean.getInspectionItemOptionList().get(i).getIsAlarm() == 0;
                }
            }
        }
        return true;
    }
}
