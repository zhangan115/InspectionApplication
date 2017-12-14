package com.inspection.application.widget;

import android.content.Context;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inspection.application.R;
import com.inspection.application.mode.bean.inject.InjectEquipment;
import com.library.utils.DataUtil;


/**
 * 注油管理dialog
 * Created by Administrator on 2017/6/14.
 */

public abstract class InjectionView extends LinearLayout implements View.OnClickListener {

    private int position;
    private EditText editText;
    private Integer cycle;
    private InjectEquipment injectEquipment;
    private boolean canEdit = true;

    public InjectionView(Context context, InjectEquipment injectEquipment, int position) {
        super(context);
        this.position = position;
        this.injectEquipment = injectEquipment;
        init(context);
    }


    public InjectionView(Context context, InjectEquipment injectEquipment, int position, boolean canEdit) {
        super(context);
        this.position = position;
        this.injectEquipment = injectEquipment;
        this.canEdit = canEdit;
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.layout_inject, this);
        editText = findViewById(R.id.edit_cycle);
        TextView img_inject = findViewById(R.id.tv_inject);
        TextView img_cancel = findViewById(R.id.tv_cancel);
        TextView text_id = findViewById(R.id.id_greasing_dialog_id);
        TextView text_lastTime = findViewById(R.id.id_greasing_dialog_last_time);
        TextView text_nextTime = findViewById(R.id.id_greasing_dialog_next_time);
        if (TextUtils.isEmpty(injectEquipment.getEquipmentSn())) {
            text_id.setText(injectEquipment.getEquipmentName());
        } else {
            text_id.setText(String.format("%s\n(%s)", injectEquipment.getEquipmentName(), injectEquipment.getEquipmentSn()));
        }
        if (injectEquipment.getInjectionOil() != null && injectEquipment.getInjectionOil().getCreateTime() != 0) {
            text_lastTime.setText(DataUtil.timeFormat(injectEquipment.getInjectionOil().getCreateTime(), "yyyy.MM.dd"));
            text_nextTime.setText(DataUtil.timeFormat(injectEquipment.getInjectionOil().getCreateTime()
                    + injectEquipment.getCycle() * 24L * 60L * 60L * 1000L, "yyyy.MM.dd"));
        }
        img_cancel.setOnClickListener(this);
        img_inject.setOnClickListener(this);
        editText.setText(String.valueOf(injectEquipment.getCycle()));
        editText.setSelection(String.valueOf(injectEquipment.getCycle()).length());
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_inject:
                try {
                    cycle = Integer.valueOf(editText.getText().toString());
                } catch (NumberFormatException exception) {
                    exception.printStackTrace();
                }
                injectEquipment(position, cycle);
                cancel();
                break;
            case R.id.tv_cancel:
                cancel();
                break;
        }
    }

    public abstract void injectEquipment(int position, Integer cycle);

    public abstract void cancel();
}
