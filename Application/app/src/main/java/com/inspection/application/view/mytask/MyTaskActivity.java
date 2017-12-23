package com.inspection.application.view.mytask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.common.ConstantInt;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.task.InspectionBean;
import com.inspection.application.view.BaseActivity;
import com.inspection.application.view.task.data.TaskDataActivity;
import com.library.adapter.RVAdapter;
import com.library.utils.DataUtil;
import com.library.widget.ExpendRecycleView;
import com.library.widget.RecycleRefreshLoadLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的任务
 * Created by pingan on 2017/12/15.
 */

public class MyTaskActivity extends BaseActivity implements MyTaskContract.View, RecycleRefreshLoadLayout.OnLoadListener, SwipeRefreshLayout.OnRefreshListener {

    private RecycleRefreshLoadLayout mRecycleRefreshLoadLayout;
    private ExpendRecycleView mRecyclerView;
    private MyTaskContract.Presenter mPresenter;
    private RelativeLayout noDataLayout;

    private boolean isRefresh;
    private List<InspectionBean> mList;
    private String[] strList = new String[]{"日检", "周检", "月检", "特检"};
    private int[] icons = new int[]{R.drawable.bg_inspection_day
            , R.drawable.bg_inspection_week
            , R.drawable.bg_inspection_month
            , R.drawable.bg_inspection_spect};

    private int[] colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_defect_record, R.string.main_my_task);
        new MyTaskPresenter(Injection.getIntent().provideTaskRepository(App.getInstance().getModule()), this);
        mRecyclerView = findViewById(R.id.recycleViewId);
        mRecycleRefreshLoadLayout = findViewById(R.id.refresh_layout);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mRecycleRefreshLoadLayout.setColorSchemeColors(findColorById(R.color.colorPrimary));
        noDataLayout = findViewById(R.id.layout_no_data);
        mRecycleRefreshLoadLayout.setOnRefreshListener(this);
        mRecycleRefreshLoadLayout.setOnLoadListener(this);
        colors = new int[]{findColorById(R.color.color_finish)
                , findColorById(R.color.color_get)
                , findColorById(R.color.color_un_start)
                , findColorById(R.color.color_working)};
        initAdapter();
        mPresenter.getTaskList();
    }

    private void initAdapter() {
        mList = new ArrayList<>();
        RVAdapter<InspectionBean> adapter = new RVAdapter<InspectionBean>(mRecyclerView, mList, R.layout.item_day_inspection) {
            @Override
            public void showData(ViewHolder vHolder, InspectionBean data, int position) {
                //巡检类型
                TextView tv_inspection_type = (TextView) vHolder.getView(R.id.tv_inspection_type);
                LinearLayout ll_inspection_type = (LinearLayout) vHolder.getView(R.id.ll_inspection_type);
                if (data.getIsManualCreated() == 0) {
                    if (data.getPlanPeriodType() == 0) {
                        ll_inspection_type.setVisibility(View.GONE);
                    } else {
                        ll_inspection_type.setVisibility(View.VISIBLE);

                        tv_inspection_type.setText(strList[data.getPlanPeriodType() - 1]);
                        tv_inspection_type.setTextColor(colors[data.getPlanPeriodType() - 1]);
                        tv_inspection_type.setBackground(findDrawById(icons[data.getPlanPeriodType() - 1]));
                    }
                } else {
                    ll_inspection_type.setVisibility(View.VISIBLE);
                    tv_inspection_type.setBackground(findDrawById(icons[3]));
                    tv_inspection_type.setText(strList[3]);
                    tv_inspection_type.setTextColor(colors[3]);
                }
                //巡检状态
                TextView tv_inspection_state = (TextView) vHolder.getView(R.id.tv_inspection_state);
                ImageView iv_inspection_type = (ImageView) vHolder.getView(R.id.iv_inspection_type);
                switch (data.getTaskState()) {
                    case ConstantInt.TASK_STATE_1:
                        tv_inspection_state.setText("待领取");
                        tv_inspection_state.setTextColor(findColorById(R.color.color_get));
                        iv_inspection_type.setImageDrawable(findDrawById(R.drawable.bg_inspection_get));
                        break;
                    case ConstantInt.TASK_STATE_2:
                        tv_inspection_state.setText("待开始");
                        tv_inspection_state.setTextColor(findColorById(R.color.color_un_start));
                        iv_inspection_type.setImageDrawable(findDrawById(R.drawable.bg_inspection_un_start));
                        break;
                    case ConstantInt.TASK_STATE_3:
                        tv_inspection_state.setTextColor(findColorById(R.color.color_working));
                        tv_inspection_state.setText("进行中");
                        iv_inspection_type.setImageDrawable(findDrawById(R.drawable.bg_inspection_wroking));
                        break;
                    default:
                        tv_inspection_state.setTextColor(findColorById(R.color.color_finish));
                        tv_inspection_state.setText("已完成");
                        iv_inspection_type.setImageDrawable(findDrawById(R.drawable.bg_inspection_finish));
                        break;
                }
                //任务名称
                TextView tv_inspection_name = (TextView) vHolder.getView(R.id.tv_inspection_name);
                TextView tv_start_time = (TextView) vHolder.getView(R.id.tv_start_time);
                tv_inspection_name.setText(data.getTaskName());
                if (data.getPlanStartTime() != 0) {
                    tv_start_time.setVisibility(View.VISIBLE);
                    tv_start_time.setText(String.format("开始时间:  %s", DataUtil.timeFormat(data.getPlanStartTime(), null)));
                } else {
                    tv_start_time.setVisibility(View.GONE);
                }
                TextView tv_equipment_count = (TextView) vHolder.getView(R.id.tv_equipment_count);
                tv_equipment_count.setText(String.format("%s/%s", String.valueOf(data.getUploadCount()), String.valueOf(data.getCount())));
                //点检部门
                TextView tv_inspection_dept = (TextView) vHolder.getView(R.id.tv_inspection_dept);
                if (data.getExecutorDeptList() != null && data.getExecutorDeptList().size() > 0) {
                    tv_inspection_dept.setVisibility(View.VISIBLE);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < data.getExecutorDeptList().size(); i++) {
                        sb.append(data.getExecutorDeptList().get(i).getDeptBean().getDeptName());
                        if (i != data.getExecutorDeptList().size() - 1) {
                            sb.append("、");
                        }
                    }
                    tv_inspection_dept.setText(sb.toString());
                } else {
                    tv_inspection_dept.setVisibility(View.GONE);
                }
            }
        };
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MyTaskActivity.this, TaskDataActivity.class);
                intent.putExtra(ConstantStr.KEY_BUNDLE_STR, mList.get(position).getTaskName());
                intent.putExtra(ConstantStr.KEY_BUNDLE_LONG, mList.get(position).getTaskId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void showTaskList(List<InspectionBean> data) {
        mList.clear();
        mList.addAll(data);
        mRecyclerView.getAdapter().notifyDataSetChanged();
        noDataLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        isRefresh = false;
        mRecycleRefreshLoadLayout.setRefreshing(false);
    }

    @Override
    public void noData() {
        mList.clear();
        mRecyclerView.getAdapter().notifyDataSetChanged();
        noDataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void noMoreData() {
        mRecycleRefreshLoadLayout.setNoMoreData(true);
    }


    @Override
    public void hideLoadingMore() {
        mRecycleRefreshLoadLayout.loadFinish();
    }

    @Override
    public void showTaskListMore(List<InspectionBean> data) {
        mList.addAll(data);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void setPresenter(MyTaskContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onLoadMore() {
        if (isRefresh) {
            return;
        }
        mPresenter.getTaskListMore(mList.get(mList.size() - 1).getTaskId());
    }

    @Override
    public void onRefresh() {
        mList.clear();
        mRecyclerView.getAdapter().notifyDataSetChanged();
        isRefresh = true;
        mPresenter.getTaskList();
    }
}
