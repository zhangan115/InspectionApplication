package com.inspection.application.view.equipment.archives;

import android.os.Bundle;

import com.inspection.application.R;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.equipment.EquipmentBean;
import com.inspection.application.view.BaseActivity;
import com.library.utils.ActivityUtilsV4;

/**
 * 设备档案
 * Created by zhangan on 2017/10/12.
 */

public class EquipmentArchivesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EquipmentBean bean = getIntent().getParcelableExtra(ConstantStr.KEY_BUNDLE_OBJECT);
        if (bean == null) {
            finish();
            return;
        }
        setLayoutAndToolbar(R.layout.activity_container_toolbar, bean.getEquipmentName());
        EquipmentArchivesFragment fragment = (EquipmentArchivesFragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (fragment == null) {
            fragment = EquipmentArchivesFragment.newInstance(bean);
            ActivityUtilsV4.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frame_container);
        }
        new EquipmentArchivesPresenter(Injection.getIntent().provideEquipmentRepository(), fragment);
    }
}
