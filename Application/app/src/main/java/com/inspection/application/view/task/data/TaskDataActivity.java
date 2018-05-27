package com.inspection.application.view.task.data;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.common.ConstantInt;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.task.data.CheckBean;
import com.inspection.application.mode.bean.task.data.InspectionDataBean;
import com.inspection.application.view.BaseActivity;
import com.library.utils.DataUtil;
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
            ((TextView) headerView.findViewById(R.id.tv_time_plan)).setText(String.format("%s-%s"
                    , DataUtil.timeFormat(mCheckBean.getPlanStartTime(), "yyyy.MM.dd HH:mm")
                    , DataUtil.timeFormat(mCheckBean.getPlanEndTime(), "yyyy.MM.dd HH:mm")));
            if (mInspectionDataBean.getTaskState() == ConstantInt.TASK_STATE_4) {
                ((TextView) headerView.findViewById(R.id.tv_time_work)).setText(String.format("%s-%s"
                        , DataUtil.timeFormat(mCheckBean.getStartTime(), "yyyy.MM.dd HH:mm")
                        , DataUtil.timeFormat(mCheckBean.getEndTime(), "yyyy.MM.dd HH:mm")));
                headerView.findViewById(R.id.ll_time_work).setVisibility(View.VISIBLE);
                headerView.findViewById(R.id.ll_count).setVisibility(View.VISIBLE);
                ((TextView) headerView.findViewById(R.id.tv_1)).setText(mCheckBean.getActualCount() + "台");
                ((TextView) headerView.findViewById(R.id.tv_2)).setText(mCheckBean.getMissEquipmentCount() + "台");
                ((TextView) headerView.findViewById(R.id.tv_3)).setText(mCheckBean.getMissDateCount() + "个");
                ((TextView) headerView.findViewById(R.id.tv_4)).setText(mCheckBean.getAlarmCount() + "个");
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mInspectionDataBean.getRoomList().size(); i++) {
                sb.append(mInspectionDataBean.getRoomList().get(i).getRoom().getRoomName());
                if (i != mInspectionDataBean.getRoomList().size() - 1) {
                    sb.append(",");
                }
            }
            StringBuilder sbUser = new StringBuilder();
            if (mCheckBean.getUsers() != null) {
                for (int i = 0; i < mCheckBean.getUsers().size(); i++) {
                    sbUser.append(mCheckBean.getUsers().get(i).getRealName());
                    if (i != mCheckBean.getUsers().size() - 1) {
                        sbUser.append(",");
                    }
                }
            }
            if (!TextUtils.isEmpty(sbUser)) {
                ((TextView) headerView.findViewById(R.id.tv_user)).setText(sbUser.toString());
            } else {
                headerView.findViewById(R.id.ll_user).setVisibility(View.GONE);
            }
            ((TextView) headerView.findViewById(R.id.tv_room_list)).setText(sb.toString());
            ((TextView) headerView.findViewById(R.id.tv_equipment_count)).setText(String.format("%d台", mCheckBean.getCount()));

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
