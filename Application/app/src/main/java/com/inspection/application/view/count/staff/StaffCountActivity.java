package com.inspection.application.view.count.staff;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.count.DeptType;
import com.inspection.application.mode.bean.count.MonthCount;
import com.inspection.application.view.BaseActivity;
import com.inspection.application.widget.StaffDialog;
import com.library.adapter.RVAdapter;
import com.library.utils.DataUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * 月度人员到岗统计
 * Created by pingan on 2017/12/22.
 */

public class StaffCountActivity extends BaseActivity implements StaffContract.View {

    private RecyclerView recyclerView;
    private RelativeLayout noDataLayout;
    private String deptType = "1";
    private List<MonthCount> monthCounts;
    private TextView mChooseDept;
    private List<String> listStr;
    private List<DeptType> mDeptTypes;
    private long mDeptId;
    private StaffContract.Presenter mPresenter;
    private Calendar mCurrentCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_staff_count, "月度人员到岗统计");
        new StaffPresenter(Injection.getIntent().provideCountRepository(), this);
        mCurrentCalendar = Calendar.getInstance(Locale.CHINA);
        mChooseDept = findViewById(R.id.choose_dept);
        recyclerView = findViewById(R.id.recycleViewId);
        noDataLayout = findViewById(R.id.layout_no_data);
        mChooseDept.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        monthCounts = new ArrayList<>();
        RVAdapter<MonthCount> adapter = new RVAdapter<MonthCount>(recyclerView, monthCounts, R.layout.item_staff_work) {
            @Override
            public void showData(ViewHolder vHolder, MonthCount data, int position) {
                TextView name = (TextView) vHolder.getView(R.id.task_name);
                name.setText(data.getTaskName());
            }
        };
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String name = monthCounts.get(position).getTaskName();
                String planTime = DataUtil.timeFormat(monthCounts.get(position).getPlanStartTime(), "yyyy-MM-dd HH:mm")
                        + "至" + DataUtil.timeFormat(monthCounts.get(position).getPlanEndTime(), "yyyy-MM-dd HH:mm");
                String rooms = "";
                StringBuilder stringBuilder = new StringBuilder();
                if (monthCounts.get(position).getStartTime() != 0) {
                    stringBuilder.append(DataUtil.timeFormat(monthCounts.get(position).getStartTime(), "yyyy-MM-dd HH:mm")).append("至");
                }
                if (monthCounts.get(position).getEndTime() != 0) {
                    stringBuilder.append(DataUtil.timeFormat(monthCounts.get(position).getEndTime(), "yyyy-MM-dd HH:mm"));
                }
                if (monthCounts.get(position).getRooms() != null && monthCounts.get(position).getRooms().size() > 0) {
                    StringBuilder roomsBuilder = new StringBuilder();
                    for (int j = 0; j < monthCounts.get(position).getRooms().size(); j++) {
                        roomsBuilder.append(",").append(monthCounts.get(position).getRooms().get(j));
                    }
                    rooms = roomsBuilder.toString();
                    if (!TextUtils.isEmpty(rooms)) {
                        rooms = rooms.substring(1, rooms.length());
                    }
                }
                new StaffDialog(StaffCountActivity.this, name, "计划时间：" + planTime
                        , "巡检时间：" + stringBuilder.toString(), "巡检区域：" + rooms, "1").show();
            }
        });
        recyclerView.setAdapter(adapter);
        listStr = new ArrayList<>();
        mPresenter.getDeptId(deptType);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.choose_dept:
                new MaterialDialog.Builder(this)
                        .items(listStr)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                mDeptId = mDeptTypes.get(position).getDeptId();
                                mChooseDept.setText(mDeptTypes.get(position).getDeptName());
                                mPresenter.getChartData(mDeptId, getDate(mCurrentCalendar));
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void showDeptList(List<DeptType> deptTypes) {
        mDeptTypes = deptTypes;
        listStr = new ArrayList<>();
        for (int i = 0; i < deptTypes.size(); i++) {
            listStr.add(deptTypes.get(i).getDeptName());
        }
        if (monthCounts.size() != 0) {
            monthCounts.clear();
            recyclerView.getAdapter().notifyDataSetChanged();
        }
        if (deptTypes.size() > 0) {
            mDeptId = deptTypes.get(0).getDeptId();
            mChooseDept.setText(deptTypes.get(0).getDeptName());
            mPresenter.getChartData(mDeptId, getDate(mCurrentCalendar));
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showChartData(List<MonthCount> countBeans) {
        noDataLayout.setVisibility(View.GONE);
        monthCounts.clear();
        monthCounts.addAll(countBeans);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void noData() {
        monthCounts.clear();
        recyclerView.getAdapter().notifyDataSetChanged();
        noDataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(StaffContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private String getDate(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH) + 1;
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(year)).append("-");
        if (monthOfYear < 10) {
            sb.append("0").append(String.valueOf(monthOfYear));
        } else {
            sb.append(String.valueOf(monthOfYear));
        }
        return sb.toString();
    }
}
