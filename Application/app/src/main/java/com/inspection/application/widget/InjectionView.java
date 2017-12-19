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
import com.inspection.application.mode.bean.inject.OilList;
import com.library.utils.DataUtil;

import java.util.ArrayList;
import java.util.List;


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
    private List<OilList> oilLists;
    private LinearLayout ll_items;
    private Long OriId;

    public InjectionView(Context context, InjectEquipment injectEquipment, int position, List<OilList> newLis) {
        super(context);
        this.position = position;
        this.injectEquipment = injectEquipment;
        this.oilLists = newLis;
        init(context);
        initData();
    }

    private void initData() {
        StringScrollPicker scrollPicker = findViewById(R.id.str_scroll);
        List<CharSequence> texts = new ArrayList<>();
        for (int i = 0; i < oilLists.size(); i++) {
            texts.add(oilLists.get(i).getOilName());
        }
        scrollPicker.setData(texts);
        scrollPicker.setSelectedPosition(0);
        OriId = oilLists.get(0).getId();
        scrollPicker.setOnSelectedListener(new ScrollPickerView.OnSelectedListener() {
            @Override
            public void onSelected(ScrollPickerView scrollPickerView, int position) {
                OriId = oilLists.get(position).getId();
            }
        });
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
        TextView text_id = findViewById(R.id.id_greasing_dialog_id);
        TextView text_lastTime = findViewById(R.id.id_greasing_dialog_last_time);
        TextView text_nextTime = findViewById(R.id.id_greasing_dialog_next_time);
        ll_items = findViewById(R.id.ll_items);
        if (TextUtils.isEmpty(injectEquipment.getEquipmentSn())) {
            text_id.setText(injectEquipment.getEquipmentName());
        } else {
            text_id.setText(String.format("%s(%s)", injectEquipment.getEquipmentName(), injectEquipment.getEquipmentSn()));
        }
        if (injectEquipment.getInjectionOil() != null && injectEquipment.getInjectionOil().getCreateTime() != 0) {
            text_lastTime.setText(DataUtil.timeFormat(injectEquipment.getInjectionOil().getCreateTime(), "yyyy.MM.dd"));
            text_nextTime.setText(DataUtil.timeFormat(injectEquipment.getInjectionOil().getCreateTime()
                    + injectEquipment.getCycle() * 24L * 60L * 60L * 1000L, "yyyy.MM.dd"));
        }
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
                injectEquipment(position, cycle, OriId);
                cancel();
                break;
        }
    }

    public abstract void injectEquipment(int position, Integer cycle, Long oriId);

    public abstract void cancel();
}
