package com.inspection.application.view.equipment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.equipment.EquipmentBean;
import com.inspection.application.mode.bean.equipment.RoomListBean;
import com.inspection.application.view.BaseActivity;
import com.inspection.application.view.equipment.archives.EquipmentArchivesActivity;
import com.library.widget.PinnedHeaderExpandableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备界面
 */
public class EquipListActivity extends BaseActivity implements EquipListContract.View {

    //view
    private PinnedHeaderExpandableListView mListView;
    private RelativeLayout mNoDataLayout;
    //data
    private EquipListContract.Presenter mPresenter;
    private boolean isChooseEquipment;
    private List<RoomListBean> mRoomList;
    private EquipListAdapter equipAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_equip_list, "设备档案");
        new EquipListPresenter(Injection.getIntent().provideEquipmentRepository(), this);
        isChooseEquipment = getIntent().getBooleanExtra(ConstantStr.KEY_BUNDLE_BOOLEAN, false);
        mListView = findViewById(R.id.expandableListView);
        mNoDataLayout = findViewById(R.id.layout_no_data);
        initData();
    }

    private void initData() {
        mRoomList = new ArrayList<>();
        equipAdapter = new EquipListAdapter(this, R.layout.item_equip_group, R.layout.item_equip_child);
        equipAdapter.setData(mRoomList);
        mListView.setAdapter(equipAdapter);
        equipAdapter.setItemListener(new EquipListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(EquipmentBean equipmentBean) {
                if (isChooseEquipment) {
                    Intent intent = new Intent();
                    intent.putExtra(ConstantStr.KEY_BUNDLE_OBJECT, equipmentBean);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    Intent intent = new Intent(EquipListActivity.this, EquipmentArchivesActivity.class);
                    intent.putExtra(ConstantStr.KEY_BUNDLE_OBJECT, equipmentBean);
                    startActivity(intent);
                }
            }
        });
        mPresenter.getEquipList();
    }

    @Override
    public void showRoomList(List<RoomListBean> list) {
        mNoDataLayout.setVisibility(View.GONE);
        mRoomList.clear();
        mRoomList.addAll(list);
        equipAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@Nullable String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void noData() {
        mNoDataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(EquipListContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
