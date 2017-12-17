package com.inspection.application.view.task.data;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.task.data.CheckBean;
import com.inspection.application.mode.bean.task.data.InspectionDataBean;
import com.inspection.application.view.BaseActivity;
import com.library.widget.PinnedHeaderExpandableListView;

/**
 * 任务数据
 * Created by pingan on 2017/12/15.
 */

public class TaskDataActivity extends BaseActivity implements TaskDataContract.View {

    private PinnedHeaderExpandableListView mListView;
    private RelativeLayout noDataLayout;
    private TaskDataContract.Presenter mPresenter;

    private CheckBean mCheckBean;
    private InspectionDataBean mInspectionDataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR);
        long taskId = getIntent().getLongExtra(ConstantStr.KEY_BUNDLE_LONG, -1);
        if (taskId == -1 || TextUtils.isEmpty(title)) {
            finish();
            return;
        }
        setLayoutAndToolbar(R.layout.activity_task_detail_data, title);
        new TaskDataPresenter(Injection.getIntent().provideTaskRepository(App.getInstance().getModule()), this);
        mListView = findViewById(R.id.expandableListView);
        noDataLayout = findViewById(R.id.layout_no_data);

        mPresenter.getCheckData(taskId);
        mPresenter.getTaskData(taskId);

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void noData() {
        noDataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCheckData(CheckBean checkBean) {
        mCheckBean = checkBean;
        setData();
    }

    @Override
    public void showTaskData(InspectionDataBean inspectionDataBean) {
        mInspectionDataBean = inspectionDataBean;
        setData();
    }

    private void setData() {
        if (mCheckBean != null && mInspectionDataBean != null) {
            noDataLayout.setVisibility(View.GONE);
            DataAdapter dataAdapter = new DataAdapter(this, mInspectionDataBean.getRoomList());
            mListView.setAdapter(dataAdapter);
            View headerView = LayoutInflater.from(this).inflate(R.layout.header_task_data, null);
            mListView.addHeaderView(headerView);
        }
    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void setPresenter(TaskDataContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
