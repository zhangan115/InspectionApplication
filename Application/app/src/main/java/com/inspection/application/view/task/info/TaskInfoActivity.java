package com.inspection.application.view.task.info;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.task.InspectionDetailBean;
import com.inspection.application.view.BaseActivity;
import com.orhanobut.logger.Logger;

/**
 * 巡检任务信息
 * Created by pingan on 2017/12/11.
 */

public class TaskInfoActivity extends BaseActivity implements TaskInfoContract.View {

    private long taskId;
    private TaskInfoContract.Presenter mPresenter;

    private RelativeLayout noDataLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_task_info, "任务列表");
        taskId = getIntent().getLongExtra(ConstantStr.KEY_BUNDLE_LONG, -1);
        if (taskId == -1) {
            finish();
            return;
        }
        new TaskInfoPresenter(Injection.getIntent().provideTaskRepository(App.getInstance().getModule()), this);
        noDataLayout = findViewById(R.id.layout_no_data);
        mPresenter.getInspectionDetailList(taskId);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void noData() {
        noDataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showData(InspectionDetailBean inspectionBeen) {
        Logger.d("inspectionBean" + inspectionBeen);
    }

    @Override
    public void setPresenter(TaskInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
