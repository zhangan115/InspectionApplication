package com.inspection.application.view.alarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.common.ConstantInt;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.task.AlarmList;
import com.inspection.application.view.BaseActivity;
import com.inspection.application.view.equipment.EquipListActivity;
import com.inspection.application.view.equipment.archives.EquipmentArchivesActivity;
import com.inspection.application.view.equipment.data.EquipmentDataActivity;
import com.inspection.application.view.task.data.TaskDataActivity;
import com.library.adapter.RVAdapter;
import com.library.utils.DataUtil;
import com.library.widget.ExpendRecycleView;
import com.library.widget.RecycleRefreshLoadLayout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * 报警信息
 * Created by pingan on 2017/12/18.
 */

public class AlarmActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, AlarmContract.View, SwipeRefreshLayout.OnRefreshListener, RecycleRefreshLoadLayout.OnLoadListener {

    private EditText editText;
    private TextView tv_time;
    private RelativeLayout noDataLayout;
    private Calendar mCurrentDay;
    private String mDate;
    private ExpendRecycleView mRecyclerView;
    private RecycleRefreshLoadLayout mRecycleRefreshLoadLayout;
    private AlarmContract.Presenter mPresenter;
    private boolean isRefresh;
    private String searchStr;
    private List<AlarmList> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_alarm, R.string.main_alarm_news);
        new AlarmPresenter(Injection.getIntent().provideTaskRepository(App.getInstance().getModule()), this);
        mList = new ArrayList<>();
        editText = findViewById(R.id.edit_equipment_name);
        tv_time = findViewById(R.id.tv_time);
        findViewById(R.id.ll_choose_time).setOnClickListener(this);
        findViewById(R.id.iv_search).setOnClickListener(this);
        mRecyclerView = findViewById(R.id.recycleViewId);
        noDataLayout = findViewById(R.id.layout_no_data);
        mRecycleRefreshLoadLayout = findViewById(R.id.refresh_layout);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mRecycleRefreshLoadLayout.setColorSchemeColors(findColorById(R.color.colorPrimary));
        mRecycleRefreshLoadLayout.setOnLoadListener(this);
        mRecycleRefreshLoadLayout.setOnRefreshListener(this);
        mCurrentDay = Calendar.getInstance(Locale.CHINA);
        RVAdapter<AlarmList> adapter = new RVAdapter<AlarmList>(mRecyclerView, mList, R.layout.item_alarm) {
            @Override
            public void showData(ViewHolder vHolder, AlarmList data, int position) {
                TextView tv_room_name = (TextView) vHolder.getView(R.id.tv_room_name);
                TextView tv_equip_name = (TextView) vHolder.getView(R.id.tv_equip_name);
                TextView tv_type = (TextView) vHolder.getView(R.id.tv_type);
                TextView tv_value = (TextView) vHolder.getView(R.id.tv_value);
                TextView tv_des = (TextView) vHolder.getView(R.id.tv_des);
                TextView tv_time = (TextView) vHolder.getView(R.id.tv_time);
                vHolder.getView(R.id.tv_task).setTag(R.id.tag_object, data);
                vHolder.getView(R.id.tv_equipment).setTag(R.id.tag_object, data);
                vHolder.getView(R.id.tv_task).setOnClickListener(taskListener);
                vHolder.getView(R.id.tv_equipment).setOnClickListener(equipmentListener);
                //time
                tv_time.setText(DataUtil.timeFormat(data.getCommitTime(), "yyyy.MM.dd HH:mm"));
                //room
                if (data.getRoom() != null) {
                    tv_room_name.setText(data.getRoom().getRoomName());
                }
                //equipment
                if (data.getRoom() != null) {
                    tv_equip_name.setText(data.getEquipment().getEquipmentName());
                }
                //value
                tv_type.setText(data.getDataItem().getInspectionName());
                tv_value.setText(data.getValue());
                if (data.getDataItem().getInspectionType() == ConstantInt.DATA_VALUE_TYPE_2) {
                    String lowerStr = "";
                    if (!TextUtils.isEmpty(String.valueOf(data.getDataItem().getQuantityLowlimit()))) {
                        lowerStr = String.valueOf(data.getDataItem().getQuantityLowlimit());
                    }
                    String upperStr = "";
                    if (!TextUtils.isEmpty(String.valueOf(data.getDataItem().getQuantityUplimit()))) {
                        upperStr = String.valueOf(data.getDataItem().getQuantityUplimit());
                    }
                    if (!TextUtils.isEmpty(lowerStr) || !TextUtils.isEmpty(upperStr)) {
                        tv_des.setVisibility(View.VISIBLE);
                        tv_des.setVisibility(View.VISIBLE);
                        tv_des.setText(String.format("正常范围 [%s~%s ]", lowerStr, upperStr));
                    } else {
                        tv_des.setVisibility(View.GONE);
                        tv_des.setVisibility(View.GONE);
                    }
                } else {
                    tv_des.setVisibility(View.GONE);
                }
            }
        };
        mRecyclerView.setAdapter(adapter);
        getDate(mCurrentDay.get(Calendar.YEAR), mCurrentDay.get(Calendar.MONTH) + 1, mCurrentDay.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_choose_time:
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AlarmActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setVersion(DatePickerDialog.Version.VERSION_1);
                dpd.show(getFragmentManager(), "choose_date");
                break;
            case R.id.iv_search:
                searchStr = editText.getText().toString();
                if (!TextUtils.isEmpty(searchStr)) {
                    requestAlarm();
                } else {
                    App.getInstance().showToast("请输入搜索内容");
                }
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        mCurrentDay.set(year, monthOfYear, dayOfMonth);
        getDate(mCurrentDay.get(Calendar.YEAR), mCurrentDay.get(Calendar.MONTH) + 1, mCurrentDay.get(Calendar.DAY_OF_MONTH));
    }


    private void getDate(int year, int monthOfYear, int dayOfMonth) {
        String currentDate = mDate;
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(year)).append("-");
        StringBuilder time = new StringBuilder();
        time.append(String.valueOf(year)).append(".");
        if (monthOfYear < 10) {
            sb.append("0").append(String.valueOf(monthOfYear)).append("-");
            time.append("0").append(String.valueOf(monthOfYear)).append(".");
        } else {
            sb.append(String.valueOf(monthOfYear)).append("-");
            time.append(String.valueOf(monthOfYear)).append(".");
        }
        if (dayOfMonth < 10) {
            sb.append("0").append(String.valueOf(dayOfMonth));
            time.append("0").append(String.valueOf(dayOfMonth));
        } else {
            sb.append(MessageFormat.format("{0}", String.valueOf(dayOfMonth)));
            time.append(String.valueOf(dayOfMonth));
        }
        if (TextUtils.isEmpty(currentDate) || !currentDate.equals(sb.toString())) {
            mDate = sb.toString();
            tv_time.setText(time.toString());
            onRefresh();
        }
    }

    View.OnClickListener taskListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlarmList data = (AlarmList) view.getTag(R.id.tag_object);
            if (data.getTask() == null) {
                return;
            }
            Intent intent = new Intent(AlarmActivity.this, TaskDataActivity.class);
            intent.putExtra(ConstantStr.KEY_BUNDLE_STR, data.getTask().getTaskName());
            intent.putExtra(ConstantStr.KEY_BUNDLE_LONG, data.getTask().getTaskId());
            startActivity(intent);
        }
    };

    View.OnClickListener equipmentListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlarmList data = (AlarmList) view.getTag(R.id.tag_object);
            if (data.getEquipment() == null) {
                return;
            }
            Intent intent = new Intent(AlarmActivity.this, EquipmentArchivesActivity.class);
            intent.putExtra(ConstantStr.KEY_BUNDLE_OBJECT, data.getEquipment());
            startActivity(intent);

        }
    };

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
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
    public void noMoreData() {
        mRecycleRefreshLoadLayout.setNoMoreData(true);
    }

    @Override
    public void hideLoadingMore() {
        mRecycleRefreshLoadLayout.loadFinish();
    }


    @Override
    public void showAlarmList(@NonNull List<AlarmList> data) {
        isRefresh = false;
        mList.clear();
        mList.addAll(data);
        mRecyclerView.getAdapter().notifyDataSetChanged();
        noDataLayout.setVisibility(View.GONE);
    }

    @Override
    public void showMoreAlarmList(@NonNull List<AlarmList> data) {
        mList.addAll(data);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void setPresenter(AlarmContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        mList.clear();
        mRecyclerView.getAdapter().notifyDataSetChanged();
        requestAlarm();
    }

    @Override
    public void onLoadMore() {
        if (isRefresh) {
            return;
        }
        if (mList == null || mList.size() == 0) {
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(searchStr)) {
                jsonObject.put("equipmentName", searchStr);
            }
            jsonObject.put("count", ConstantInt.PAGE_SIZE);
            jsonObject.put("lastId", mList.get(mList.size() - 1).getAlarmId());
            jsonObject.put("time", mDate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPresenter.getAlarmListMore(jsonObject.toString());
    }

    private void requestAlarm() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(searchStr)) {
                jsonObject.put("equipmentName", searchStr);
            }
            jsonObject.put("count", ConstantInt.PAGE_SIZE);
            jsonObject.put("time", mDate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPresenter.getAlarmList(jsonObject.toString());
    }
}
