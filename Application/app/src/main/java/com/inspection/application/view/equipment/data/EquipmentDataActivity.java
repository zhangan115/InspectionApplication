package com.inspection.application.view.equipment.data;

import android.os.Bundle;

import com.inspection.application.R;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.Injection;
import com.inspection.application.view.BaseActivity;
import com.library.utils.ActivityUtilsV4;


/**
 * Created by zhangan on 2017/10/13.
 */

public class EquipmentDataActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_container_toolbar, "巡检数据");
        long equipmentId = getIntent().getLongExtra(ConstantStr.KEY_BUNDLE_LONG, -1);
        if (equipmentId == -1) {
            finish();
            return;
        }
        EquipmentDataFragment fragment = (EquipmentDataFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (fragment == null) {
            fragment = EquipmentDataFragment.newInstance(equipmentId);
        }
        new EquipmentDataPresenter(Injection.getIntent().provideEquipmentRepository(), fragment);
        ActivityUtilsV4.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frame_container);
    }
}
