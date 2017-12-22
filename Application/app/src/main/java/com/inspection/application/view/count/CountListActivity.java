package com.inspection.application.view.count;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.inspection.application.R;
import com.inspection.application.view.BaseActivity;
import com.inspection.application.view.count.fault.count.FaultNumberCountActivity;
import com.inspection.application.view.count.fault.level.FaultLevelCountActivity;
import com.inspection.application.view.count.missing.MissingCountActivity;
import com.inspection.application.view.count.staff.StaffCountActivity;
import com.inspection.application.view.count.work.WorkCountActivity;

/**
 * 统计展示界面
 * Created by pingan on 2017/12/21.
 */

public class CountListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_count_list, getString(R.string.count_analysis));
        findViewById(R.id.tv_1).setOnClickListener(this);
        findViewById(R.id.tv_2).setOnClickListener(this);
        findViewById(R.id.tv_3).setOnClickListener(this);
        findViewById(R.id.tv_4).setOnClickListener(this);
        findViewById(R.id.tv_5).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_1:
                startActivity(new Intent(this, WorkCountActivity.class));
                break;
            case R.id.tv_2:
                startActivity(new Intent(this, MissingCountActivity.class));
                break;
            case R.id.tv_3:
                startActivity(new Intent(this, StaffCountActivity.class));
                break;
            case R.id.tv_4:
                startActivity(new Intent(this, FaultLevelCountActivity.class));
                break;
            case R.id.tv_5:
                startActivity(new Intent(this, FaultNumberCountActivity.class));
                break;
        }
    }
}
