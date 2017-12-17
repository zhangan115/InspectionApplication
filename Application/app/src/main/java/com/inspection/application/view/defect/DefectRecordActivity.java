package com.inspection.application.view.defect;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.common.ConstantInt;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.equipment.EquipmentBean;
import com.inspection.application.mode.bean.fault.FaultList;
import com.inspection.application.view.BaseActivity;
import com.inspection.application.view.defect.detail.DefectRecordDetailActivity;
import com.library.adapter.RVAdapter;
import com.library.utils.DataUtil;
import com.library.widget.ExpendRecycleView;
import com.library.widget.RecycleRefreshLoadLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 缺陷记录
 * Created by pingan on 2017/12/13.
 */

public class DefectRecordActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, RecycleRefreshLoadLayout.OnLoadListener, DefectRecordContract.View {

    private RecycleRefreshLoadLayout mRecycleRefreshLoadLayout;
    private ExpendRecycleView mRecyclerView;
    private RelativeLayout noDataLayout;

    private DefectRecordContract.Presenter mPresenter;
    private List<FaultList> mList;
    private boolean isRefresh;
    @Nullable
    private EquipmentBean equipmentBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        equipmentBean = getIntent().getParcelableExtra(ConstantStr.KEY_BUNDLE_OBJECT);
        if (equipmentBean == null) {
            setLayoutAndToolbar(R.layout.activity_defect_record, R.string.main_fault_record);
        } else {
            setLayoutAndToolbar(R.layout.activity_defect_record, equipmentBean.getEquipmentName());
        }
        new DefectRecordPresenter(Injection.getIntent().provideFaultRepository(App.getInstance().getModule()), this);
        mList = new ArrayList<>();
        mRecycleRefreshLoadLayout = findViewById(R.id.refresh_layout);
        mRecycleRefreshLoadLayout.setColorSchemeColors(findColorById(R.color.colorPrimary));
        mRecycleRefreshLoadLayout.setOnLoadListener(this);
        mRecycleRefreshLoadLayout.setOnRefreshListener(this);
        mRecyclerView = findViewById(R.id.recycleViewId);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        noDataLayout = findViewById(R.id.layout_no_data);
        RVAdapter<FaultList> adapter = new RVAdapter<FaultList>(mRecyclerView, mList, R.layout.item_falut_list) {
            @Override
            public void showData(ViewHolder vHolder, FaultList data, int position) {
                TextView tv_room_name = (TextView) vHolder.getView(R.id.tv_room_name);
                TextView tv_equip_name = (TextView) vHolder.getView(R.id.tv_equip_name);
                TextView tv_fault_type = (TextView) vHolder.getView(R.id.tv_fault_type);
                TextView tv_fault_des = (TextView) vHolder.getView(R.id.tv_fault_des);
                TextView tv_report_user_name = (TextView) vHolder.getView(R.id.tv_report_user_name);
                TextView tv_report_time = (TextView) vHolder.getView(R.id.tv_report_time);
                if (data.getEquipment() != null && data.getEquipment().getRoom() != null) {
                    tv_room_name.setText(data.getEquipment().getRoom().getRoomName());
                } else {
                    tv_room_name.setText("");
                }
                tv_fault_des.setText(data.getFaultDescript());
                tv_report_user_name.setText(String.format("上报人:%s", data.getUser().getRealName()));
                tv_report_time.setText(DataUtil.timeFormat(data.getCreateTime(), null));
                if (data.getFaultType() == ConstantInt.DEFECT_TYPE_A) {
                    tv_fault_type.setTextColor(findColorById(R.color.color_defect_a));
                    tv_fault_type.setBackground(findDrawById(R.drawable.bg_defect_a));
                } else if (data.getFaultType() == ConstantInt.DEFECT_TYPE_B) {
                    tv_fault_type.setTextColor(findColorById(R.color.color_defect_b));
                    tv_fault_type.setBackground(findDrawById(R.drawable.bg_defect_b));
                } else {
                    tv_fault_type.setTextColor(findColorById(R.color.color_defect_c));
                    tv_fault_type.setBackground(findDrawById(R.drawable.bg_defect_c));
                }
                tv_fault_type.setText(App.getInstance().getMapOption().get("2").get(String.valueOf(data.getFaultType())));
                if (equipmentBean != null) {
                    tv_equip_name.setVisibility(View.GONE);
                } else {
                    tv_equip_name.setVisibility(View.VISIBLE);
                    tv_equip_name.setText(data.getEquipment().getEquipmentName());
                }
            }
        };
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(DefectRecordActivity.this, DefectRecordDetailActivity.class);
                if (mList.get(position).getEquipment() != null) {
                    intent.putExtra(ConstantStr.KEY_BUNDLE_STR, mList.get(position).getEquipment().getEquipmentName());
                    intent.putExtra(ConstantStr.KEY_BUNDLE_LONG, mList.get(position).getFaultId());
                }
                startActivity(intent);
            }
        });
        requestFaultList();
    }

    private void requestFaultList() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("count", ConstantInt.PAGE_SIZE);
            if (equipmentBean != null) {
                jsonObject.put("equipmentId", equipmentBean.getEquipmentId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPresenter.getFaultList(jsonObject.toString());
    }

    @Override
    public void onRefresh() {
        mList.clear();
        mRecyclerView.getAdapter().notifyDataSetChanged();
        isRefresh = true;
        requestFaultList();
    }

    @Override
    public void onLoadMore() {
        if (isRefresh) {
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("lastId", mList.get(mList.size() - 1).getFaultId());
            jsonObject.put("count", ConstantInt.PAGE_SIZE);
            if (equipmentBean != null) {
                jsonObject.put("equipmentId", equipmentBean.getEquipmentId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPresenter.getFaultListMore(jsonObject.toString());
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
    public void showFaultList(@NonNull List<FaultList> data) {
        mList.clear();
        mList.addAll(data);
        mRecyclerView.getAdapter().notifyDataSetChanged();
        noDataLayout.setVisibility(View.GONE);
    }

    @Override
    public void showMoreFaultList(@NonNull List<FaultList> data) {
        mList.addAll(data);
        mRecyclerView.getAdapter().notifyDataSetChanged();
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
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void setPresenter(DefectRecordContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
