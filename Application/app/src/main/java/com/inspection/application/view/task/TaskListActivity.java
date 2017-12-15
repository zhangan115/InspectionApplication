package com.inspection.application.view.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
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
import com.inspection.application.view.secure.SecureActivity;
import com.inspection.application.view.task.info.TaskInfoActivity;
import com.inspection.application.view.taskdata.TaskDataActivity;
import com.library.adapter.RVAdapter;
import com.library.utils.CalendarUtil;
import com.library.utils.DataUtil;
import com.library.widget.ExpendRecycleView;
import com.library.widget.RecycleRefreshLoadLayout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 点检任务
 * Created by pingan on 2017/12/9.
 */

public class TaskListActivity extends BaseActivity implements TaskContract.View, SwipeRefreshLayout.OnRefreshListener, DatePickerDialog.OnDateSetListener {

    private RecycleRefreshLoadLayout mRefreshLoadLayout;
    private ExpendRecycleView mExpendRecycleView;
    private TaskContract.Presenter mPresenter;
    private RelativeLayout mNoDataLayout;

    private String mDate;
    private TextView[] dayTvs = new TextView[7];
    private LinearLayout[] dayOfWeekLayout = new LinearLayout[7];
    private List<Date> dateList = new ArrayList<>();
    private Calendar mCurrentDay;
    private TextView mYearTv, mMonthTv, mDayTv;

    private List<InspectionBean> mList;
    private String[] strList = new String[]{"日检", "周检", "月检", "特检"};
    private int[] icons = new int[]{R.drawable.bg_inspection_day
            , R.drawable.bg_inspection_week
            , R.drawable.bg_inspection_month
            , R.drawable.bg_inspection_special};
    private int[] colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_task_lsit, R.string.main_task);
        new TaskPresenter(Injection.getIntent().provideTaskRepository(App.getInstance().getModule()), this);
        mExpendRecycleView = findViewById(R.id.recycleViewId);
        mRefreshLoadLayout = findViewById(R.id.refresh_layout);
        mExpendRecycleView.setLayoutManager(new GridLayoutManager(this, 1));
        mRefreshLoadLayout.setColorSchemeColors(findColorById(R.color.colorPrimary));
        mNoDataLayout = findViewById(R.id.layout_no_data);
        mRefreshLoadLayout.setOnRefreshListener(this);

        findViewById(R.id.ll_choose_month_day).setOnClickListener(this);
        mYearTv = findViewById(R.id.tv_year);
        mMonthTv = findViewById(R.id.tv_month);
        mDayTv = findViewById(R.id.tv_day);
        colors = new int[]{findColorById(R.color.color_day)
                , findColorById(R.color.color_day)
                , findColorById(R.color.color_month)
                , findColorById(R.color.color_special)};
        dayTvs[0] = findViewById(R.id.tv_1);
        dayTvs[1] = findViewById(R.id.tv_2);
        dayTvs[2] = findViewById(R.id.tv_3);
        dayTvs[3] = findViewById(R.id.tv_4);
        dayTvs[4] = findViewById(R.id.tv_5);
        dayTvs[5] = findViewById(R.id.tv_6);
        dayTvs[6] = findViewById(R.id.tv_7);
        dayOfWeekLayout[0] = findViewById(R.id.ll_monday);
        dayOfWeekLayout[1] = findViewById(R.id.ll_tuesday);
        dayOfWeekLayout[2] = findViewById(R.id.ll_wednesday);
        dayOfWeekLayout[3] = findViewById(R.id.ll_thursday);
        dayOfWeekLayout[4] = findViewById(R.id.ll_friday);
        dayOfWeekLayout[5] = findViewById(R.id.ll_saturday);
        dayOfWeekLayout[6] = findViewById(R.id.ll_sunday);
        for (LinearLayout layout : dayOfWeekLayout) {
            layout.setOnClickListener(this);
        }
        mCurrentDay = Calendar.getInstance(Locale.CHINA);
        dateList = CalendarUtil.getDaysOfWeek(mCurrentDay.getTime());
        setDayToView();
        initAdapter();
    }

    private void initAdapter() {
        mList = new ArrayList<>();
        RVAdapter<InspectionBean> adapter = new RVAdapter<InspectionBean>(mExpendRecycleView, mList, R.layout.item_day_inspection) {
            @Override
            public void showData(ViewHolder vHolder, InspectionBean data, int position) {
                //巡检类型
                TextView tv_inspection_type = (TextView) vHolder.getView(R.id.tv_inspection_type);
                ImageView iv_inspection_type = (ImageView) vHolder.getView(R.id.iv_inspection_type);
                LinearLayout ll_inspection_type = (LinearLayout) vHolder.getView(R.id.ll_inspection_type);
                if (data.getIsManualCreated() == 0) {
                    if (data.getPlanPeriodType() == 0) {
                        ll_inspection_type.setVisibility(View.GONE);
                    } else {
                        ll_inspection_type.setVisibility(View.VISIBLE);
                        iv_inspection_type.setImageDrawable(findDrawById(icons[data.getPlanPeriodType() - 1]));
                        tv_inspection_type.setText(strList[data.getPlanPeriodType() - 1]);
                        tv_inspection_type.setTextColor(colors[data.getPlanPeriodType() - 1]);
                    }
                } else {
                    ll_inspection_type.setVisibility(View.VISIBLE);
                    iv_inspection_type.setImageDrawable(findDrawById(icons[3]));
                    tv_inspection_type.setText(strList[3]);
                    tv_inspection_type.setTextColor(colors[3]);
                }
                //巡检状态
                TextView tv_inspection_state = (TextView) vHolder.getView(R.id.tv_inspection_state);
                switch (data.getTaskState()) {
                    case ConstantInt.TASK_STATE_1:
                        tv_inspection_state.setText("领取");
                        tv_inspection_state.setBackground(findDrawById(R.drawable.inspection_state_get));
                        break;
                    case ConstantInt.TASK_STATE_2:
                        tv_inspection_state.setText("开始");
                        tv_inspection_state.setBackground(findDrawById(R.drawable.inspection_state_start));
                        break;
                    case ConstantInt.TASK_STATE_3:
                        tv_inspection_state.setText("进行中");
                        tv_inspection_state.setBackground(findDrawById(R.drawable.inspection_state_working));
                        break;
                    case ConstantInt.TASK_STATE_4:
                        tv_inspection_state.setText("已完成");
                        tv_inspection_state.setBackground(findDrawById(R.drawable.inspection_state_finish));
                        break;
                }
                tv_inspection_state.setTag(R.id.tag_object, data);
                tv_inspection_state.setTag(R.id.tag_position, position);
                tv_inspection_state.setOnClickListener(clickListener);
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
            }
        };
        mExpendRecycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(TaskListActivity.this, TaskDataActivity.class);
                intent.putExtra(ConstantStr.KEY_BUNDLE_STR, mList.get(position).getTaskName());
                intent.putExtra(ConstantStr.KEY_BUNDLE_LONG, mList.get(position).getTaskId());
                startActivity(intent);
            }
        });
    }

    /**
     * 点击事件
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag(R.id.tag_position);
            InspectionBean data = (InspectionBean) view.getTag(R.id.tag_object);
            if (data != null) {
                switch (data.getTaskState()) {
                    case ConstantInt.TASK_STATE_1:
                        mPresenter.getTask(position, data.getTaskId());
                        break;
                    case ConstantInt.TASK_STATE_2:
                    case ConstantInt.TASK_STATE_3:
                    case ConstantInt.TASK_STATE_4:
                        if (data.getSecurityPackage() == null) {
                            showTask(position);
                        } else {
                            mPresenter.checkSecure(position, data.getTaskId());
                        }
                        break;
                }
            }
        }
    };

    private void setDayToView() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        for (int i = 0; i < dayTvs.length; i++) {
            calendar.setTime(dateList.get(i));
            if (calendar.get(Calendar.DAY_OF_WEEK) == mCurrentDay.get(Calendar.DAY_OF_WEEK)) {
                dayTvs[i].setBackground(findDrawById(R.drawable.bg_choose_date));
                dayTvs[i].setTextColor(findColorById(R.color.colorWhite));
            } else {
                dayTvs[i].setBackgroundColor(findColorById(R.color.colorWhite));
                if (i == 0 || i == dayTvs.length - 1) {
                    dayTvs[i].setTextColor(findColorById(R.color.text_light_gray));
                } else {
                    dayTvs[i].setTextColor(findColorById(R.color.text_gray));
                }
            }
            dayTvs[i].setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        }
        if (!getDate(mCurrentDay.get(Calendar.YEAR), mCurrentDay.get(Calendar.MONTH) + 1, mCurrentDay.get(Calendar.DAY_OF_MONTH))) {
            mPresenter.getTaskList(mDate);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_choose_month_day:
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        TaskListActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setVersion(DatePickerDialog.Version.VERSION_1);
                dpd.show(getFragmentManager(), "choose_date");
                break;
            case R.id.ll_monday:
                mCurrentDay.setTime(dateList.get(0));
                setDayToView();
                break;
            case R.id.ll_tuesday:
                mCurrentDay.setTime(dateList.get(1));
                setDayToView();
                break;
            case R.id.ll_wednesday:
                mCurrentDay.setTime(dateList.get(2));
                setDayToView();
                break;
            case R.id.ll_thursday:
                mCurrentDay.setTime(dateList.get(3));
                setDayToView();
                break;
            case R.id.ll_friday:
                mCurrentDay.setTime(dateList.get(4));
                setDayToView();
                break;
            case R.id.ll_saturday:
                mCurrentDay.setTime(dateList.get(5));
                setDayToView();
                break;
            case R.id.ll_sunday:
                mCurrentDay.setTime(dateList.get(6));
                setDayToView();
                break;
        }
    }

    @Override
    public void showTaskList(List<InspectionBean> been) {
        mNoDataLayout.setVisibility(View.GONE);
        mList.clear();
        mList.addAll(been);
        mExpendRecycleView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        mRefreshLoadLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mRefreshLoadLayout.setRefreshing(false);
    }

    @Override
    public void noData() {
        mList.clear();
        mExpendRecycleView.getAdapter().notifyDataSetChanged();
        mNoDataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void getTaskSuccess(int position) {
        mList.get(position).setTaskState(ConstantInt.TASK_STATE_2);
        mExpendRecycleView.getAdapter().notifyItemChanged(position);
    }

    @Override
    public void showTask(int position) {
        Intent intent = new Intent(this, TaskInfoActivity.class);
        intent.putExtra(ConstantStr.KEY_BUNDLE_LONG, mList.get(position).getTaskId());
        startActivity(intent);
    }

    @Override
    public void showSecure(int position) {
        Intent intent = new Intent(this, SecureActivity.class);
        intent.putExtra(ConstantStr.KEY_BUNDLE_LONG, mList.get(position).getSecurityPackage().getSecurityId());
        intent.putExtra(ConstantStr.KEY_BUNDLE_LONG_1, mList.get(position).getTaskId());
        startActivity(intent);
    }

    @Override
    public void setPresenter(TaskContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onRefresh() {
        mList.clear();
        mExpendRecycleView.getAdapter().notifyDataSetChanged();
        mPresenter.getTaskList(mDate);
    }

    private boolean getDate(int year, int monthOfYear, int dayOfMonth) {
        String currentDate = mDate;
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(year)).append("-");
        mYearTv.setText(String.format("%s年", String.valueOf(year)));
        StringBuilder time = new StringBuilder();
        if (monthOfYear < 10) {
            sb.append("0").append(String.valueOf(monthOfYear)).append("-");
            time.append("0").append(String.valueOf(monthOfYear)).append("/");
        } else {
            sb.append(String.valueOf(monthOfYear)).append("-");
            time.append(String.valueOf(monthOfYear)).append("/");
        }
        if (dayOfMonth < 10) {
            sb.append("0").append(String.valueOf(dayOfMonth));
            time.append("0").append(String.valueOf(dayOfMonth));
        } else {
            sb.append(MessageFormat.format("{0}", String.valueOf(dayOfMonth)));
            time.append(String.valueOf(dayOfMonth));
        }
        mMonthTv.setText(time.toString());
        mDayTv.setText(CalendarUtil.getWeek(mCurrentDay));
        if (!TextUtils.isEmpty(currentDate) && currentDate.equals(sb.toString())) {
            return true;
        } else {
            mDate = sb.toString();
            return false;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        mCurrentDay.set(year, monthOfYear, dayOfMonth);
        dateList = CalendarUtil.getDaysOfWeek(mCurrentDay.getTime());
        setDayToView();
    }
}
