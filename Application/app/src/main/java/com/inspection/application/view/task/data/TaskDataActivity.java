package com.inspection.application.view.task.data;

import android.os.Bundle;

import com.inspection.application.R;
import com.inspection.application.view.BaseActivity;

/**
 * 巡检数据展示界面
 * Created by pingan on 2017/12/11.
 */

public class TaskDataActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_task_data, "任务详情");
    }
}
