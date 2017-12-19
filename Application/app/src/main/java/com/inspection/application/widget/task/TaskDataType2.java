package com.inspection.application.widget.task;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inspection.application.R;
import com.inspection.application.common.ConstantInt;
import com.inspection.application.mode.bean.equipment.db.EquipmentDb;
import com.inspection.application.mode.bean.task.DataItemBean;
import com.inspection.application.mode.bean.task.DataItemValueListBean;
import com.inspection.application.mode.db.DbManager;

import java.text.MessageFormat;

/**
 * 选择类型
 * Created by pingan on 2017/12/17.
 */

public class TaskDataType2 extends LinearLayout {

    private Context context;
    private DataItemValueListBean dataItemValueListBean;
    private EquipmentDb equipmentDb;
    private EditText editText;
    private IEquipmentStateChange iEquipmentStateChange;

    public TaskDataType2(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        inflate(context, R.layout.layout_task_type_2, this);
    }

    public void setTaskData(DataItemValueListBean dataItemValueListBean, EquipmentDb equipmentDb, boolean canEdit, IEquipmentStateChange listener) {
        this.equipmentDb = equipmentDb;
        this.iEquipmentStateChange = listener;
        this.dataItemValueListBean = dataItemValueListBean;
        this.editText = findViewById(R.id.et_value);
        if (!TextUtils.isEmpty(dataItemValueListBean.getDataItem().getEquipmentDataDb().getValue())) {
            dataItemValueListBean.getDataItem().setValue(dataItemValueListBean.getDataItem().getEquipmentDataDb().getValue());
        }
        ((TextView) (findViewById(R.id.tv_value_title_2))).setText(dataItemValueListBean.getDataItem().getInspectionName());
        if (canEdit) {
            findViewById(R.id.iv_value).setVisibility(GONE);
            if (!TextUtils.isEmpty(dataItemValueListBean.getDataItem().getValue())) {
                editText.setText(dataItemValueListBean.getDataItem().getValue());
            } else {
                editText.setHint("请输入" + dataItemValueListBean.getDataItem().getInspectionName());
            }
            editText.addTextChangedListener(new MyTextWatcher(editText, dataItemValueListBean.getDataItem().getInspectionType()));
        } else {
            editText.setVisibility(GONE);
            findViewById(R.id.iv_value).setVisibility(VISIBLE);
            ((TextView) (findViewById(R.id.iv_value))).setText(dataItemValueListBean.getDataItem().getValue());
        }
        if (dataItemValueListBean.getDataItem().getInspectionType() == ConstantInt.DATA_VALUE_TYPE_2) {
            String lowerStr = "";
            if (!TextUtils.isEmpty(dataItemValueListBean.getDataItem().getQuantityLowlimit())) {
                lowerStr = dataItemValueListBean.getDataItem().getQuantityLowlimit();
            }
            String upperStr = "";
            if (!TextUtils.isEmpty(dataItemValueListBean.getDataItem().getQuantityUplimit())) {
                upperStr = dataItemValueListBean.getDataItem().getQuantityUplimit();
            }
            if (!TextUtils.isEmpty(dataItemValueListBean.getLastValue())) {
                ((TextView) findViewById(R.id.tv_last_value)).setText(MessageFormat.format("上次记录:{0}", dataItemValueListBean.getLastValue()));
            } else {
                ((TextView) findViewById(R.id.tv_last_value)).setText("上次记录:无");
            }
            if (!TextUtils.isEmpty(lowerStr) || !TextUtils.isEmpty(upperStr)) {
                findViewById(R.id.tv_norma_value).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.tv_norma_value)).setText(String.format("正常范围 [%s~%s ]", lowerStr, upperStr));
            } else {
                findViewById(R.id.tv_norma_value).setVisibility(View.GONE);
            }
        }
        if (isNormal(dataItemValueListBean.getDataItem())) {
            editText.setTextColor(context.getResources().getColor(R.color.color4C8FE2));
        } else {
            editText.setTextColor(context.getResources().getColor(R.color.colorF05340));
        }
    }


    private class MyTextWatcher implements TextWatcher {


        MyTextWatcher(EditText editText, int type) {
            if (type == ConstantInt.DATA_VALUE_TYPE_2) {
                editText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
            } else {
                editText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0) {
                return;
            }
            if (dataItemValueListBean.getDataItem().getInspectionType() == ConstantInt.DATA_VALUE_TYPE_4) {
                return;
            }
            String older = s.subSequence(0, start).toString();
            String enter = s.subSequence(start, s.length()).toString();
            boolean isWatcher = enter.matches("[0-9.-]+");
            boolean hasPoint = older.contains(".") && enter.contains(".");
            boolean startPoint = s.toString().startsWith(".");
            boolean hasMinus;
            hasMinus = older.contains("-") && enter.contains("-")
                    || older.length() > 0 && enter.contains("-");
            if (startPoint) {
                editText.setText("");
                if (editText.getText().length() == 0) return;
                return;
            }
            if (!isWatcher || hasPoint || hasMinus) {
                editText.setText(older);
                if (editText.getText().length() == 0) {
                    return;
                }
                editText.setSelection(older.length());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (dataItemValueListBean != null) {
                if (TextUtils.isEmpty(dataItemValueListBean
                        .getDataItem().getValue()) || !dataItemValueListBean
                        .getDataItem().getValue().equals(editable.toString())) {
                    dataItemValueListBean.getDataItem().setValue(editable.toString());
                    dataItemValueListBean.getDataItem().getEquipmentDataDb().setValue(editable.toString());
                    DbManager.getDbManager().getDaoSession().getEquipmentDataDbDao().insertOrReplace(dataItemValueListBean
                            .getDataItem().getEquipmentDataDb());
                    if (equipmentDb.getUploadState()) {
                        equipmentDb.setUploadState(false);
                        DbManager.getDbManager().getDaoSession().getEquipmentDbDao().insertOrReplaceInTx(equipmentDb);
                        if (iEquipmentStateChange != null) {
                            iEquipmentStateChange.equipmentState();
                        }
                    }
                    if (isNormal(dataItemValueListBean.getDataItem())) {
                        editText.setTextColor(context.getResources().getColor(R.color.color4C8FE2));
                    } else {
                        editText.setTextColor(context.getResources().getColor(R.color.colorF05340));
                    }
                }
            }
        }
    }

    private boolean isNormal(DataItemBean dataItemBean) {
        if (dataItemBean.getInspectionType() == ConstantInt.DATA_VALUE_TYPE_2) {
            try {
                String lower = dataItemBean.getQuantityLowlimit();
                String upper = dataItemBean.getQuantityUplimit();
                if (TextUtils.isEmpty(dataItemBean.getValue())) {
                    return true;
                }
                if (TextUtils.isEmpty(lower) && TextUtils.isEmpty(upper)) {
                    return true;
                } else if (TextUtils.isEmpty(lower) && !TextUtils.isEmpty(upper)) {
                    float upperValue = Float.valueOf(upper);
                    float value = Float.valueOf(dataItemBean.getValue());
                    return TextUtils.isEmpty(dataItemBean.getValue()) || value <= upperValue;
                } else if (!TextUtils.isEmpty(lower) && TextUtils.isEmpty(upper)) {
                    float lowerValue = Float.valueOf(lower);
                    float value = Float.valueOf(dataItemBean.getValue());
                    return TextUtils.isEmpty(dataItemBean.getValue()) || value >= lowerValue;
                } else {
                    float lowerValue = Float.valueOf(lower);
                    float upperValue = Float.valueOf(upper);
                    float value = Float.valueOf(dataItemBean.getValue());
                    return TextUtils.isEmpty(dataItemBean.getValue()) || (lowerValue <= value) && (value <= upperValue);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return true;
            }
        }
        return true;
    }
}
