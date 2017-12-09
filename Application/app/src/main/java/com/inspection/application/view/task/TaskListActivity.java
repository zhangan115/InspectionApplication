package com.inspection.application.view.task;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inspection.application.R;
import com.inspection.application.mode.Injection;
import com.inspection.application.view.BaseActivity;
import com.library.utils.CalendarUtil;
import com.library.widget.ExpendRecycleView;
import com.library.widget.RecycleRefreshLoadLayout;
import com.orhanobut.logger.Logger;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_task_lsit, R.string.main_task);
        new TaskPresenter(Injection.getIntent().provideTaskRepository(), this);
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
        getDate(mCurrentDay.get(Calendar.YEAR), mCurrentDay.get(Calendar.MONTH) + 1, mCurrentDay.get(Calendar.DAY_OF_MONTH));
        dateList = CalendarUtil.getDaysOfWeek(mCurrentDay.getTime());
        setDayToView();
    }

    private void setDayToView(boolean isCalendar) {
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
        getDate(mCurrentDay.get(Calendar.YEAR), mCurrentDay.get(Calendar.MONTH) + 1, mCurrentDay.get(Calendar.DAY_OF_MONTH));
        if (!isCalendar && !TextUtils.isEmpty(mDate)) {
            Logger.d(mDate);
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

    private void setDayToView() {
        setDayToView(false);
    }

    @Override
    public void showTaskList() {
        mNoDataLayout.setVisibility(View.GONE);
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
        mNoDataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(TaskContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onRefresh() {
        mPresenter.getTaskList("");
    }

    private void getDate(int year, int monthOfYear, int dayOfMonth) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(year)).append("-");
        mYearTv.setText(String.valueOf(year) + "年");
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
        mDate = sb.toString();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        mCurrentDay.set(year, monthOfYear, dayOfMonth);
        dateList = CalendarUtil.getDaysOfWeek(mCurrentDay.getTime());
        setDayToView(true);
        monthOfYear = monthOfYear + 1;
        getDate(year, monthOfYear, dayOfMonth);
    }
}
